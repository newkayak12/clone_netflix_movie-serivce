package com.netflix_clone.movieservice.repository.domain;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Table(name = "contentsInfo")
@Entity
public class ContentsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contentsNo", columnDefinition = "BIGINT(20)")
    private Long contentsNo;

    @ManyToOne
    @JoinColumn(name = "categoryNo")
    private Category category;

    @Column(name = "title", columnDefinition = "VARCHAR(200)")
    private String title;
    @Column(name = "description", columnDefinition = "VARCHAR(1500)")
    private String description;
    @Column(name = "releaseDate", columnDefinition = "DATETIME default CURRENT_TIMESTAMP()")
    private LocalDateTime releaseDate;
    @Column(name = "duration", columnDefinition = "TIME")
    private LocalTime duration;
    @Column(name = "regDate", columnDefinition = "DATETIME default CURRENT_TIMESTAMP()")
    private LocalDateTime regDate;
    @Column(name = "serviceDueDate", columnDefinition = "DATETIME")
    private LocalDateTime serviceDueDate;
    @Column(name = "storedLocation", columnDefinition = "VARCHAR(500)")
    private String storedLocation;
    @Column(name = "watchCount", columnDefinition = "BIGINT(20) default 0")
    private Long watchCount;

    @OneToMany(mappedBy = "contentsInfo")
    private List<ContentPerson> contentPeople;
}
