package com.netflix_clone.movieservice.repository.domain;



import com.netflix_clone.movieservice.repository.dto.reference.FileDto;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movie_profile")
@ToString
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
