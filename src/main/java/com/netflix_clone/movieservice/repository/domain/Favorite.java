package com.netflix_clone.movieservice.repository.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favoriteNo", columnDefinition = "BIGINT(20)")
    private Long favoriteNo;
    @ManyToOne
    @JoinColumn(name = "favorites")
    private MovieProfile profile;
    @OneToOne
    @JoinColumn(name = "contentsNo")
    private ContentsInfo contentsInfo;
    @Column(name = "favoriteDate", columnDefinition = "DATETIME default CURRENT_TIMESTAMP()", updatable = false)
    private LocalDateTime favoriteDate;
    @Column(name = "isFavorite", columnDefinition = "BIT(1) default FALSE")
    private Boolean isFavorite;


    @PrePersist
    public void favorite(){
        this.favoriteDate = LocalDateTime.now();
    }

}
