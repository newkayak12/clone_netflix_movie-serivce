package com.netflix_clone.movieservice.repository.categoryRepository;

import com.netflix_clone.movieservice.repository.domain.Category;
import com.netflix_clone.movieservice.repository.dto.reference.CategoryDto;
import com.netflix_clone.movieservice.repository.dto.reference.QCategoryDto;
import com.netflix_clone.movieservice.repository.dto.request.CategoryRequest;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


import java.util.List;

import static com.netflix_clone.movieservice.repository.domain.QCategory.category;
public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {
    private JPQLQueryFactory query;

    public CategoryRepositoryImpl(JPQLQueryFactory query) {
        super(Category.class);
        this.query = query;
    }

    @Override
    public PageImpl<CategoryDto> categories(Pageable pageable, CategoryRequest request) {
        List<CategoryDto> list =  query.select(
                            new QCategoryDto(
                                    category.categoryNo,
                                    new QCategoryDto(category.parentCategory.categoryNo),
                                    category.name,
                                    category.isLeaf
                            )
                    )
                    .from(category)
                    .where(category.parentCategory.categoryNo.eq(request.getCategoryNo()))
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetch();

        Long count = from(category).where(category.parentCategory.categoryNo.eq(request.getCategoryNo())).fetchCount();

        return new PageImpl<CategoryDto>(list, pageable, count);
    }
}
