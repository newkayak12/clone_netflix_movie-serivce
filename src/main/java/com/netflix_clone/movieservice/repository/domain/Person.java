package com.netflix_clone.movieservice.repository.domain;

import com.netflix_clone.movieservice.component.enums.Role;
import com.netflix_clone.movieservice.repository.dto.reference.FileDto;
import lombok.Getter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.List;

@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "person")
@Entity
@Getter
@DynamicUpdate
@DynamicInsert
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personNo")
    private Long personNo;
    @Column(name = "name", columnDefinition = "VARCHAR(100)")
    private String name;
    @Column(name = "role", columnDefinition = "VARCHAR(100)")
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "person")
    private List<ContentPerson> contentPeople;

    @Transient
    private FileDto image;

}
