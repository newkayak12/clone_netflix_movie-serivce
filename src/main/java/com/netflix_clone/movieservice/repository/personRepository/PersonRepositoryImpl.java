package com.netflix_clone.movieservice.repository.personRepository;

import com.netflix_clone.movieservice.repository.domain.Person;
import com.netflix_clone.movieservice.repository.dto.reference.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.netflix_clone.movieservice.repository.domain.QPerson.person;
import static com.netflix_clone.movieservice.repository.domain.QContentPerson.contentPerson;
import static com.netflix_clone.movieservice.repository.domain.QContentsInfo.contentsInfo;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

public class PersonRepositoryImpl extends QuerydslRepositorySupport implements PersonRepositoryCustom{
    private JPQLQueryFactory query;

    public PersonRepositoryImpl(JPQLQueryFactory query) {
        super(Person.class);
        this.query = query;
    }


    @Override
    public PageImpl<PersonDto> people(Pageable pageable, PageableRequest request) {
        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(request.getSearchText())) builder.and(person.name.contains(request.getSearchText()));


        List<PersonDto> list = query.select(
                    new QPersonDto(
                        person.personNo,
                        person.name,
                        person.role
                    )
                )
                .from(person)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.from(person).where(builder).fetchCount();
        return new PageImpl<PersonDto>(list, pageable, count);
    }

    @Override
    public Optional<PersonDto> person(Long personNo) {
        return Optional.ofNullable(
                query.select(
                                new QPersonDto(
                                        person.personNo,
                                        person.name,
                                        person.role,
                                        list(
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
                                )
                        )
                        .from(person)
                        .leftJoin(person.contentPeople, contentPerson)
                        .leftJoin(contentPerson.contentsInfo, contentsInfo)
                        .where(person.personNo.eq(personNo))
                        .fetchOne()
        );
    }
}
