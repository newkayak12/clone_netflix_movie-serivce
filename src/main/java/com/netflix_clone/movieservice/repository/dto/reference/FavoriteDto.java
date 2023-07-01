package com.netflix_clone.movieservice.repository.dto.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.netflix_clone.movieservice.repository.domain.Favorite} entity
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(value = {"profile"}, allowSetters = true, allowGetters = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class FavoriteDto implements Serializable {
    private Long favoriteNo;
    private MovieProfileDto profile;
    private ContentsInfoDto contentsInfo;
    private LocalDateTime favoriteDate;




    @QueryProjection
    public FavoriteDto(Long favoriteNo, LocalDateTime favoriteDate) {
        this.favoriteNo = favoriteNo;
        this.favoriteDate = favoriteDate;
    }
}