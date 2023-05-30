package com.netflix_clone.movieservice.repository.categoryRepository;

import com.netflix_clone.movieservice.repository.domain.Category;
import com.netflix_clone.movieservice.repository.dto.reference.CategoryDto;
import com.netflix_clone.movieservice.repository.dto.reference.QCategoryDto;
import com.netflix_clone.movieservice.repository.dto.request.CategoryRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPQLQueryFactory;
import io.micrometer.core.instrument.binder.BaseUnits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


import java.util.List;
import java.util.Objects;

import static com.netflix_clone.movieservice.repository.domain.QCategory.category;
import static com.netflix_clone.movieservice.repository.domain.QContentsInfo.contentsInfo;
public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {
    private JPQLQueryFactory query;

    @Autowired
    public CategoryRepositoryImpl(JPQLQueryFactory query) {
        super(Category.class);
        this.query = query;
    }

    @Override
    public PageImpl<CategoryDto> categories(Pageable pageable, CategoryRequest request) {
        BooleanBuilder builder = new BooleanBuilder();
        if(Objects.nonNull(request.getCategoryNo())) builder.and(category.categoryNo.eq(category.parentCategory.categoryNo));
        else builder.and(category.parentCategory.categoryNo.eq(request.getCategoryNo()));

        List<CategoryDto> list =  query.select(
                            new QCategoryDto(
                                    category.categoryNo,
                                    new QCategoryDto(category.parentCategory.categoryNo),
                                    category.name,
                                    category.isLeaf
                            )
                    )
                    .from(category)
                    .where(builder)
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetch();

        Long count = from(category).where(builder).fetchCount();

        return new PageImpl<CategoryDto>(list, pageable, count);
    }

    @Override
    public Boolean isCategoryUsed(Long categoryNo) {
           Long categoryCount = query.select()
                                     .from(category)
                                     .where(category.parentCategory.categoryNo.eq(categoryNo))
                                     .fetchCount();
           Long movieCount = query.select()
                                  .from(contentsInfo)
                                  .where(contentsInfo.category.categoryNo.eq(categoryNo))
                                  .fetchCount();
        return  (categoryCount + movieCount) > 0L;
    }

    @Override
    public Boolean remove(Long categoryNo) {
        return query.delete(category).where(category.categoryNo.eq(categoryNo)).execute() > 0;
    }


}
