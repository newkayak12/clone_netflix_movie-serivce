package com.netflix_clone.movieservice.repository.domain;

import com.netflix_clone.movieservice.enums.Role;
import com.netflix_clone.movieservice.repository.dto.reference.FileDto;

import javax.persistence.*;
import java.util.List;

@Table(name = "person")
@Entity
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
