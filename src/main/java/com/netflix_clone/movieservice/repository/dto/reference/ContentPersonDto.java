package com.netflix_clone.movieservice.repository.dto.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.netflix_clone.movieservice.repository.domain.ContentPerson} entity
 */
@Data
@NoArgsConstructor

public class ContentPersonDto implements Serializable {
    private Long contentPersonNo;
    private ContentsInfoDto contentsInfo;
    private PersonDto person;

    @QueryProjection
    public ContentPersonDto(Long contentPersonNo, ContentsInfoDto contentsInfo, PersonDto person) {
        this.contentPersonNo = contentPersonNo;
        this.contentsInfo = contentsInfo;
        this.person = person;
    }
    @QueryProjection
    public ContentPersonDto(Long contentPersonNo, ContentsInfoDto contentsInfo) {
        this.contentPersonNo = contentPersonNo;
        this.contentsInfo = contentsInfo;
    }
    @QueryProjection
    public ContentPersonDto(Long contentPersonNo, PersonDto person) {
        this.contentPersonNo = contentPersonNo;
        this.person = person;
    }
}