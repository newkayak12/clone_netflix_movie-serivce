package com.netflix_clone.movieservice.repository.domain;



import com.netflix_clone.movieservice.repository.dto.reference.FileDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "board_profile")
public class MovieProfile {
    @Id
    @Column(name = "profileNo", columnDefinition = "BIGINT(20)")
    private Long profileNo;
    @Column(name = "profileName", columnDefinition = "VARCHAR(50)")
    private String profileName;


    @OneToMany(mappedBy = "profile")
    private List<Favorite> favorites;
    @Transient
    private FileDto image;
}
