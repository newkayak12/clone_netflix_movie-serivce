package com.netflix_clone.movieservice.repository.watchedRepository;

import com.netflix_clone.movieservice.repository.domain.QContentsDetail;
import com.netflix_clone.movieservice.repository.domain.Watched;
import com.netflix_clone.movieservice.repository.dto.reference.*;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.netflix_clone.movieservice.repository.domain.QContentsDetail.contentsDetail;
import static com.netflix_clone.movieservice.repository.domain.QContentsInfo.contentsInfo;
import static com.netflix_clone.movieservice.repository.domain.QWatched.watched;

public class WatchedRepositoryImpl extends QuerydslRepositorySupport implements WatchedRepositoryCustom{
    private JPQLQueryFactory query;

    @Autowired
    public WatchedRepositoryImpl(JPQLQueryFactory query) {
        super(Watched.class);
        this.query = query;
    }

    @Override
    public PageImpl<ContentsInfoDto> watchedContents(PageableRequest request, Pageable pageable) {
        List<ContentsInfoDto> list =  query.select(
                new QContentsInfoDto (
                    contentsInfo.contentsNo,
                    contentsInfo.title,
                    contentsInfo.description,
                    contentsInfo.releaseDate,
                    contentsInfo.contentType,
                    contentsInfo.duration,
                    contentsInfo.regDate,
                    contentsInfo.serviceDueDate,
                    contentsInfo.storedLocation,
                    contentsInfo.watchCount,
                    watched.lastWatchedDate.max()
                )
        )
        .from(contentsInfo)
        .innerJoin(contentsDetail)
        .on(contentsInfo.contentsNo.eq(contentsDetail.contentsInfo.contentsNo))
        .innerJoin(watched)
        .on(contentsDetail.detailNo.eq(watched.contentsDetail.detailNo))
        .where(watched.profile.profileNo.eq(request.getTableNo()))
        .groupBy(contentsInfo.contentsNo)
        .orderBy(watched.lastWatchedDate.desc())
        .limit(pageable.getPageSize()).offset(pageable.getOffset())
        .fetch();

        Long count = from(contentsInfo)
                .innerJoin(contentsDetail)
                .on(contentsInfo.contentsNo.eq(contentsDetail.contentsInfo.contentsNo))
                .innerJoin(watched)
                .on(contentsDetail.detailNo.eq(watched.contentsDetail.detailNo))
                .where(watched.profile.profileNo.eq(request.getTableNo())).fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
