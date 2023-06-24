package com.netflix_clone.movieservice.repository.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name = "analyzer")
@Entity
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Analyze {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analyzeNo", columnDefinition = "BIGINT(20)")
    private Long analyzeNo;

    @OneToOne
    @JoinColumn(name = "contentsNo")
    private ContentsInfo contentsInfo;
    @OneToOne
    @JoinColumn(name = "categoryNo")
    private Category category;

    @Column(name = "watchScore", columnDefinition = "Double")
    private Double watchScore;
    @Column(name = "favoriteScore", columnDefinition = "BIGINT(20)")
    private Long favoriteScore;
}
