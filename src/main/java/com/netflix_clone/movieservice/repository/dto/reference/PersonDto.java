package com.netflix_clone.movieservice.repository.dto.reference;

import com.netflix_clone.movieservice.component.enums.Role;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.netflix_clone.movieservice.repository.domain.Person} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class PersonDto implements Serializable {
    private Long personNo;
    private String name;
    private Role role;

    private List<ContentsInfoDto> contentsInfoList;

    @Transient
    private FileDto file;

    @QueryProjection
    public PersonDto(Long personNo, String name, Role role) {
        this.personNo = personNo;
        this.name = name;
        this.role = role;
    }

    @QueryProjection
    public PersonDto(Long personNo, String name, Role role, List<ContentsInfoDto> contentsInfoList) {
        this.personNo = personNo;
        this.name = name;
        this.role = role;
        this.contentsInfoList = contentsInfoList;
    }
}
