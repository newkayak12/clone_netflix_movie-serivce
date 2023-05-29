package com.netflix_clone.movieservice.repository.dto.reference;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created on 2023-05-10
 * Project user-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieProfileDto implements Serializable {
    private Long profileNo;
    private String profileName;
    private LocalDateTime regDate;

    @Transient
    private FileDto image;

    @QueryProjection
    public MovieProfileDto(Long profileNo, String profileName) {
        this.profileNo = profileNo;
        this.profileName = profileName;
    }

}
