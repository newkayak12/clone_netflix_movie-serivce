package com.netflix_clone.movieservice.repository.dto.reference;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * A DTO for the {@link com.netflix_clone.movieservice.repository.domain.ContentsDetail} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentsDetailDto implements Serializable {
    private Long detailNo;
    private Integer season;
    private Integer episode;
    private String subTitle;
    private LocalTime duration;
    private String storedLocation;
    private ContentsInfoDto contentsInfo;

    @Transient
    private FileDto thumbnail;

    @QueryProjection
    public ContentsDetailDto(Long detailNo, Integer season, Integer episode,
                             String subTitle, LocalTime duration, String storedLocation) {

        this.detailNo = detailNo;
        this.season = season;
        this.episode = episode;
        this.subTitle = subTitle;
        this.duration = duration;
        this.storedLocation = storedLocation;
//        this.contentsInfo = contentsInfo;
    }
}