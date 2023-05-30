package com.netflix_clone.movieservice.repository.dto.reference;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A DTO for the {@link com.netflix_clone.movieservice.repository.domain.Watched} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchedDto implements Serializable {
    private Long watchedNo;
    private MovieProfileDto profile;
    private ContentsInfoDto contentsInfo;
    private LocalDateTime lastWatchedDate;
    private LocalTime watchedAt;


    @QueryProjection
    public WatchedDto(Long watchedNo, LocalDateTime lastWatchedDate, LocalTime watchedAt) {
        this.watchedNo = watchedNo;
        this.lastWatchedDate = lastWatchedDate;
        this.watchedAt = watchedAt;
    }

    @QueryProjection
    public WatchedDto(Long watchedNo, ContentsInfoDto contentsInfo, LocalDateTime lastWatchedDate, LocalTime watchedAt) {
        this.watchedNo = watchedNo;
        this.contentsInfo = contentsInfo;
        this.lastWatchedDate = lastWatchedDate;
        this.watchedAt = watchedAt;
    }
}