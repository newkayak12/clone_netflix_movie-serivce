package com.netflix_clone.movieservice.repository.dto.reference;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.netflix_clone.movieservice.repository.domain.Favorite} entity
 */
@Data
@NoArgsConstructor
public class FavoriteDto implements Serializable {
    private Long favoriteNo;
    private MovieProfileDto profile;
    private ContentsInfoDto contentsInfo;
    private LocalDateTime favoriteDate;
    private Boolean isFavorite;


    @QueryProjection
    public FavoriteDto(Long favoriteNo, MovieProfileDto profile, ContentsInfoDto contentsInfo, LocalDateTime favoriteDate, Boolean isFavorite) {
        this.favoriteNo = favoriteNo;
        this.profile = profile;
        this.contentsInfo = contentsInfo;
        this.favoriteDate = favoriteDate;
        this.isFavorite = isFavorite;
    }
}