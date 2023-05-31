package com.netflix_clone.movieservice.repository.dto.reference;

import com.netflix_clone.movieservice.enums.ContentType;
import com.netflix_clone.movieservice.repository.domain.ContentPerson;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * A DTO for the {@link com.netflix_clone.movieservice.repository.domain.ContentsInfo} entity
 */
@Data
@NoArgsConstructor
public class ContentsInfoDto implements Serializable {
    private Long contentsNo;
    private CategoryDto category;
    private String title;
    private String description;
    private LocalDateTime releaseDate;

    private ContentType contentType;
    private LocalTime duration;
    private LocalDateTime regDate;
    private LocalDateTime serviceDueDate;
    private String storedLocation;
    private Long watchCount;
    private List<ContentPersonDto> contentPeople;
    private List<ContentsDetailDto> details;

    @Transient
    private List<FileDto> images;


    @QueryProjection
    public ContentsInfoDto(Long contentsNo, CategoryDto category, String title, String description,
                           LocalDateTime releaseDate, ContentType contentType, LocalTime duration,
                           LocalDateTime regDate, LocalDateTime serviceDueDate, String storedLocation,
                           Long watchCount, List<ContentPersonDto> contentPeople, List<ContentsDetailDto> details) {
        this.contentsNo = contentsNo;
        this.category = category;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.contentType = contentType;
        this.duration = duration;
        this.regDate = regDate;
        this.serviceDueDate = serviceDueDate;
        this.storedLocation = storedLocation;
        this.watchCount = watchCount;
        this.contentPeople = contentPeople;
        this.details = details;
    }

    @QueryProjection
    public ContentsInfoDto(Long contentsNo, CategoryDto category, String title, String description,
                           LocalDateTime releaseDate, ContentType contentType, LocalTime duration,
                           LocalDateTime regDate, LocalDateTime serviceDueDate, String storedLocation, Long watchCount) {
        this.contentsNo = contentsNo;
        this.category = category;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.contentType = contentType;
        this.duration = duration;
        this.regDate = regDate;
        this.serviceDueDate = serviceDueDate;
        this.storedLocation = storedLocation;
        this.watchCount = watchCount;
    }

    @QueryProjection
    public ContentsInfoDto(Long contentsNo, CategoryDto category, String title, String description,
                           LocalDateTime releaseDate, ContentType contentType, LocalTime duration,
                           LocalDateTime regDate, LocalDateTime serviceDueDate, String storedLocation,
                           Long watchCount, List<ContentsDetailDto> details) {
        this.contentsNo = contentsNo;
        this.category = category;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.contentType = contentType;
        this.duration = duration;
        this.regDate = regDate;
        this.serviceDueDate = serviceDueDate;
        this.storedLocation = storedLocation;
        this.watchCount = watchCount;
        this.details = details;
    }

    @QueryProjection
    public ContentsInfoDto(Long contentsNo, String title, String description, LocalDateTime releaseDate,
                           ContentType contentType, LocalTime duration, LocalDateTime regDate,
                           LocalDateTime serviceDueDate, String storedLocation, Long watchCount) {
        this.contentsNo = contentsNo;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.contentType = contentType;
        this.duration = duration;
        this.regDate = regDate;
        this.serviceDueDate = serviceDueDate;
        this.storedLocation = storedLocation;
        this.watchCount = watchCount;
    }

    @QueryProjection
    public ContentsInfoDto(Long contentsNo, String title, String description, LocalDateTime releaseDate,
                           ContentType contentType, LocalTime duration, LocalDateTime regDate,
                           LocalDateTime serviceDueDate, String storedLocation, Long watchCount,
                           List<ContentsDetailDto> details) {
        this.contentsNo = contentsNo;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.contentType = contentType;
        this.duration = duration;
        this.regDate = regDate;
        this.serviceDueDate = serviceDueDate;
        this.storedLocation = storedLocation;
        this.watchCount = watchCount;
        this.details = details;
    }
}