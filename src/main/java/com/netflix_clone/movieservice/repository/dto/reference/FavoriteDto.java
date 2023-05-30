package com.netflix_clone.movieservice.repository.dto.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonIgnoreProperties(allowGetters = false, value = {"profile"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FavoriteDto implements Serializable {
    private Long favoriteNo;
    private MovieProfileDto profile;
    private ContentsInfoDto contentsInfo;
    private LocalDateTime favoriteDate;
    private Boolean isFavorite;


    public void emptyFavorite(Long contentsNo, Long profileNo){
        MovieProfileDto profile = new MovieProfileDto();
        profile.setProfileNo(profileNo);
        this.profile = profile;

        ContentsInfoDto contentsInfoDto = new ContentsInfoDto();
        contentsInfoDto.setContentsNo(contentsNo);
        this.contentsInfo = contentsInfoDto;
        this.isFavorite = false;
    }


    @QueryProjection
    public FavoriteDto(Long favoriteNo, LocalDateTime favoriteDate, Boolean isFavorite) {
        this.favoriteNo = favoriteNo;
        this.favoriteDate = favoriteDate;
        this.isFavorite = isFavorite;
    }
}