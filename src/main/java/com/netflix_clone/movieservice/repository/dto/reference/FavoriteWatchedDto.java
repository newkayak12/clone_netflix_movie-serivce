package com.netflix_clone.movieservice.repository.dto.reference;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = false)
@NoArgsConstructor
public class FavoriteWatchedDto {
    private ContentsInfoDto contentsInfo;
    private WatchedDto watched;
    private FavoriteDto favorite;

    @QueryProjection
    public FavoriteWatchedDto(ContentsInfoDto contentsInfo, WatchedDto watched, FavoriteDto favorite) {
        this.contentsInfo = contentsInfo;
        this.watched = watched;
        this.favorite = favorite;
    }
}
