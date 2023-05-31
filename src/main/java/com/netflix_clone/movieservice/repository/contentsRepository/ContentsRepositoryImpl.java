package com.netflix_clone.movieservice.repository.contentsRepository;

import com.netflix_clone.movieservice.repository.domain.ContentsInfo;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.reference.QCategoryDto;
import com.netflix_clone.movieservice.repository.dto.reference.QContentsDetailDto;
import com.netflix_clone.movieservice.repository.dto.reference.QContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.request.ContentRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.netflix_clone.movieservice.repository.domain.QContentsInfo.contentsInfo;
import static com.netflix_clone.movieservice.repository.domain.QCategory.category;
import static com.netflix_clone.movieservice.repository.domain.QContentsDetail.contentsDetail;
public class ContentsRepositoryImpl extends QuerydslRepositorySupport implements ContentsRepositoryCustom{
    private JPQLQueryFactory query;

    @Autowired
    public ContentsRepositoryImpl(JPQLQueryFactory query) {
        super(ContentsInfo.class);
        this.query = query;
    }

    public BooleanBuilder contentsCondition(ContentRequest request){
        BooleanBuilder builder = new BooleanBuilder();
        if(Objects.nonNull(request.getContentType())) builder.and(contentsInfo.contentType.eq(request.getContentType()));
        if (Objects.nonNull(request.getCategoryNo())) builder.and(contentsInfo.category.categoryNo.eq(request.getCategoryNo()));
        if(StringUtils.hasText(request.getSearchText())) builder.and(contentsInfo.title.contains(request.getSearchText()));
        return builder;
    }
    @Override
    public PageImpl<ContentsInfoDto> contents(ContentRequest request, Pageable pageable) {
        BooleanBuilder builder = this.contentsCondition(request);

        List<ContentsInfoDto> list =  query.select(
                new QContentsInfoDto(
                        contentsInfo.contentsNo,
                        contentsInfo.title,
                        contentsInfo.description,
                        contentsInfo.releaseDate,
                        contentsInfo.contentType,
                        contentsInfo.duration,
                        contentsInfo.regDate,
                        contentsInfo.serviceDueDate,
                        contentsInfo.storedLocation,
                        contentsInfo.watchCount
                )
        )
        .from(contentsInfo)
        .where(builder)
        .limit(pageable.getPageSize())
        .offset(pageable.getOffset())
        .orderBy(contentsInfo.watchCount.desc())
        .fetch();


        Long count = from(contentsInfo).where(builder).fetchCount();
        return new PageImpl<ContentsInfoDto>(list, pageable, count);
    }

    @Override
    public ContentsInfoDto content(Long contentNo) {
        return query.select(
                new QContentsInfoDto(
                        contentsInfo.contentsNo,
                        new QCategoryDto(
                                category.categoryNo,
                                category.name
                        ),
                        contentsInfo.title,
                        contentsInfo.description,
                        contentsInfo.releaseDate,
                        contentsInfo.contentType,
                        contentsInfo.duration,
                        contentsInfo.regDate,
                        contentsInfo.serviceDueDate,
                        contentsInfo.storedLocation,
                        contentsInfo.watchCount
                        //TODO SubQuery -> List (Projection)
                        // Detail, Person
                )
        ).where().fetchOne();
    }

}
