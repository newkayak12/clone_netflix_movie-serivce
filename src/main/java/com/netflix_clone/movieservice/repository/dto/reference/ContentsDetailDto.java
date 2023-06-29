package com.netflix_clone.movieservice.repository.dto.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * A DTO for the {@link com.netflix_clone.movieservice.repository.domain.ContentsDetail} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"contentsInfo"})
@EqualsAndHashCode(of = {"detailNo"})
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
    @Transient
    private MultipartFile rawFile;
    @Transient
    private WatchedDto watched;

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

    @QueryProjection
    public ContentsDetailDto(Long detailNo, Integer season, Integer episode,
                             String subTitle, LocalTime duration,
                             String storedLocation, WatchedDto watched) {

        this.detailNo = detailNo;
        this.season = season;
        this.episode = episode;
        this.subTitle = subTitle;
        this.duration = duration;
        this.storedLocation = storedLocation;
        this.watched = watched;
    }

    public ContentsDetailDto(Long detailNo, Integer season,
                             Integer episode, String subTitle,
                             LocalTime duration, String storedLocation,
                             ContentsInfoDto contentsInfo,
                             FileDto thumbnail, MultipartFile rawFile) {
        this.detailNo = detailNo;
        this.season = season;
        this.episode = episode;
        this.subTitle = subTitle;
        this.duration = duration;
        this.storedLocation = storedLocation;
        this.contentsInfo = contentsInfo;
        this.thumbnail = thumbnail;
        this.rawFile = rawFile;
    }

    public ContentsDetailDto(Long detailNo, Integer season, Integer episode,
                             String subTitle, LocalTime duration, String storedLocation,
                             FileDto thumbnail, MultipartFile rawFile, WatchedDto watched) {
        this.detailNo = detailNo;
        this.season = season;
        this.episode = episode;
        this.subTitle = subTitle;
        this.duration = duration;
        this.storedLocation = storedLocation;
        this.thumbnail = thumbnail;
        this.rawFile = rawFile;
        this.watched = watched;
    }
}