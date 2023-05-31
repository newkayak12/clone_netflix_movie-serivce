package com.netflix_clone.movieservice.repository.domain;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalTime;

@Table(name = "contentsDetail")
@Entity
public class ContentsDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detailNo", columnDefinition = "BIGINT(20)")
    private Long detailNo;
    @Column(name = "season", columnDefinition = "INT(11) default 0")
    @ColumnDefault(value = "0")
    private Integer season;
    @Column(name = "episode", columnDefinition = "INT(11)")
    private Integer episode;
    @Column(name = "subTitle", columnDefinition = "VARCHAR(256)")
    private String subTitle;
    @Column(name = "duration", columnDefinition = "TIME")
    private LocalTime duration;
    @Column(name = "storedLocation", columnDefinition = "VARCHAR(500)")
    private String storedLocation;
    @ManyToOne
    @JoinColumn(name = "contentsNo")
    private ContentsInfo contentsInfo;
}
