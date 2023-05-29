package com.netflix_clone.movieservice.repository.dto.reference;

import com.netflix_clone.movieservice.repository.domain.ContentPerson;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * A DTO for the {@link com.netflix_clone.movieservice.repository.domain.ContentsInfo} entity
 */
@Data
public class ContentsInfoDto implements Serializable {
    private Long contentsNo;
    private CategoryDto category;
    private String title;
    private String description;
    private LocalDateTime releaseDate;
    private LocalTime duration;
    private LocalDateTime regDate;
    private LocalDateTime serviceDueDate;
    private String storedLocation;
    private Long watchCount;

    private List<ContentPersonDto> contentPeople;
    
}