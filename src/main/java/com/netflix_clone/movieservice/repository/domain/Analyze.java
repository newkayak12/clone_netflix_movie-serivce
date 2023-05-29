package com.netflix_clone.movieservice.repository.domain;

import javax.persistence.*;

@Table(name = "analyzer")
@Entity
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
