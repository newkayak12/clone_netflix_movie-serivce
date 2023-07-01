package com.netflix_clone.movieservice.repository.domain;

import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Entity
@Table(name = "favorite")
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favoriteNo", columnDefinition = "BIGINT(20)")
    private Long favoriteNo;
    @ManyToOne
    @JoinColumn(name = "profileNo")
    private MovieProfile profile;
    @OneToOne
    @JoinColumn(name = "contentsNo")
    private ContentsInfo contentsInfo;
    @Column(name = "favoriteDate", columnDefinition = "DATETIME default CURRENT_TIMESTAMP()", updatable = false)
    private LocalDateTime favoriteDate;


    @PrePersist
    public void favorite(){
        this.favoriteDate = LocalDateTime.now();
    }

}
