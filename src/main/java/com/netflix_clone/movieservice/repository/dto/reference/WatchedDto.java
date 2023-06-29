package com.netflix_clone.movieservice.repository.dto.reference;

import com.netflix_clone.movieservice.repository.domain.ContentsDetail;
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
    private ContentsDetailDto contentsDetail;
    private LocalDateTime lastWatchedDate;
    private LocalTime watchedAt;


    @QueryProjection
    public WatchedDto(Long watchedNo, LocalDateTime lastWatchedDate, LocalTime watchedAt) {
        this.watchedNo = watchedNo;
        this.lastWatchedDate = lastWatchedDate;
        this.watchedAt = watchedAt;
    }

    @QueryProjection
    public WatchedDto(Long watchedNo, ContentsDetailDto contentsDetail, LocalDateTime lastWatchedDate, LocalTime watchedAt) {
        this.watchedNo = watchedNo;
        this.contentsDetail = contentsDetail;
        this.lastWatchedDate = lastWatchedDate;
        this.watchedAt = watchedAt;
    }
}