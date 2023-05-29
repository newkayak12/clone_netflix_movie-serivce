package com.netflix_clone.movieservice.repository.dto.reference;

import com.netflix_clone.movieservice.enums.Role;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * A DTO for the {@link com.netflix_clone.movieservice.repository.domain.Person} entity
 */
@Data
public class PersonDto implements Serializable {
    private Long personNo;
    private String name;
    private Role role;

    @Transient
    private FileDto file;

    @QueryProjection
    public PersonDto(Long personNo, String name, Role role) {
        this.personNo = personNo;
        this.name = name;
        this.role = role;
    }
}