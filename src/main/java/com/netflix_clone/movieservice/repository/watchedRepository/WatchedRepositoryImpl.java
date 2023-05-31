package com.netflix_clone.movieservice.repository.watchedRepository;

import com.netflix_clone.movieservice.repository.domain.QWatched;
import com.netflix_clone.movieservice.repository.domain.Watched;
import com.netflix_clone.movieservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.movieservice.repository.dto.reference.QContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.reference.QWatchedDto;
import com.netflix_clone.movieservice.repository.dto.reference.WatchedDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.netflix_clone.movieservice.repository.domain.QWatched.watched;
import static com.netflix_clone.movieservice.repository.domain.QContentsInfo.contentsInfo;

public class WatchedRepositoryImpl extends QuerydslRepositorySupport implements WatchedRepositoryCustom{
    private JPQLQueryFactory query;

    @Autowired
    public WatchedRepositoryImpl(JPQLQueryFactory query) {
        super(Watched.class);
        this.query = query;
    }

    @Override
    public PageImpl<WatchedDto> watchedContents(PageableRequest request, Pageable pageable) {
        List<WatchedDto> list =  query.select(
                new QWatchedDto(
                        watched.watchedNo,
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
                        ),
                        watched.lastWatchedDate,
                        watched.watchedAt
                )
        )
        .from(watched)
        .join(watched.contentsInfo, contentsInfo)
        .where(watched.profile.profileNo.eq(request.getTableNo()))
        .limit(pageable.getPageSize()).offset(pageable.getOffset())
        .orderBy(watched.lastWatchedDate.desc())
        .fetch();

        Long count = from(watched).where(watched.profile.profileNo.eq(request.getTableNo())).fetchCount();

        return new PageImpl<WatchedDto>(list, pageable, count);
    }
}
