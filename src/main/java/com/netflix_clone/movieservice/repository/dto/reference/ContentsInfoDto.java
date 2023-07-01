package com.netflix_clone.movieservice.repository.dto.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.netflix_clone.movieservice.component.enums.ContentType;
import com.netflix_clone.movieservice.repository.domain.Person;
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
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
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
    private List<PersonDto> people;
    private List<ContentsDetailDto> details;

    @Transient
    private List<Long> peopleNo;

    @Transient
    private List<FileDto> images;

    @Transient
    private LocalDateTime lastWatchedDate;


    public void preventInfiniteRecursive() {
        this.contentPeople.forEach( v -> {
            v.setContentsInfo(null);
        });
    }


    @QueryProjection
    public ContentsInfoDto(Long contentsNo, CategoryDto category, String title, String description,
                           LocalDateTime releaseDate, ContentType contentType, LocalTime duration,
                           LocalDateTime regDate, LocalDateTime serviceDueDate, String storedLocation,
                           Long watchCount, List<PersonDto> people, List<ContentsDetailDto> details) {
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
        this.people = people;
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
                           LocalDateTime lastWatchedDate) {
       this(
           contentsNo,
           title,
           description,
           releaseDate,
           contentType,
           duration,
           regDate,
           serviceDueDate,
           storedLocation,
           watchCount
       );
       this.lastWatchedDate = lastWatchedDate;
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