package com.netflix_clone.movieservice.repository.dto.reference;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString(callSuper = false)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FavoriteWatchedDto {
    private ContentsInfoDto contentsInfo;
    private WatchedDto watched;
    private FavoriteDto favorite;

    private LocalDateTime lastWatchedDate;

    @QueryProjection
    public FavoriteWatchedDto(ContentsInfoDto contentsInfo, WatchedDto watched, FavoriteDto favorite) {
        this.contentsInfo = contentsInfo;
        this.watched = watched;
        this.favorite = favorite;
    }

    @QueryProjection
    public FavoriteWatchedDto(ContentsInfoDto contentsInfo, FavoriteDto favorite, LocalDateTime lastWatchedDate) {
        this.contentsInfo = contentsInfo;
        this.favorite = favorite;
        this.lastWatchedDate = lastWatchedDate;
    }
}
