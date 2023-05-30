package com.netflix_clone.movieservice.repository.favoriteRepository;

import com.netflix_clone.movieservice.repository.domain.Favorite;
import com.netflix_clone.movieservice.repository.dto.reference.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import static com.netflix_clone.movieservice.repository.domain.QWatched.watched;
import static com.netflix_clone.movieservice.repository.domain.QContentsInfo.contentsInfo;
import static com.netflix_clone.movieservice.repository.domain.QFavorite.favorite;
import static com.netflix_clone.movieservice.repository.domain.QCategory.category;
public class FavoriteRepositoryImpl extends QuerydslRepositorySupport implements FavoriteRepositoryCustom {
    private JPQLQueryFactory query;

    @Autowired
    public FavoriteRepositoryImpl(JPQLQueryFactory query) {
        super(Favorite.class);
        this.query = query;
    }

    @Override
    public PageImpl<FavoriteWatchedDto> favorites(PageableRequest request, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(favorite.profile.profileNo.eq(request.getTableNo()));
        builder.and(favorite.isFavorite.isTrue());

        List<FavoriteWatchedDto> list = query.select(
                new QFavoriteWatchedDto (
                        new QContentsInfoDto(
                                contentsInfo.contentsNo,
                                contentsInfo.title,
                                contentsInfo.description,
                                contentsInfo.releaseDate,
                                contentsInfo.duration,
                                contentsInfo.regDate,
                                contentsInfo.serviceDueDate,
                                contentsInfo.storedLocation,
                                contentsInfo.watchCount
                        ),
                        new QWatchedDto(
                                watched.watchedNo,
                                watched.lastWatchedDate,
                                watched.watchedAt
                        ),
                        new QFavoriteDto(
                                favorite.favoriteNo,
                                favorite.favoriteDate,
                                favorite.isFavorite
                        )
                )
        )
        .from(favorite)
        .join(favorite.contentsInfo, contentsInfo)
        .leftJoin(watched).on(contentsInfo.contentsNo.eq(watched.contentsInfo.contentsNo))
        .where(builder)
        .limit(pageable.getPageSize())
        .offset(pageable.getOffset()).fetch();

        Long count = from(favorite).join(favorite.contentsInfo, contentsInfo).where(builder).fetchCount();
        return new PageImpl<>(list, pageable, count);
    }
}
