package com.netflix_clone.movieservice.repository.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "watched")
public class Watched {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watchedNo", columnDefinition = "BIGINT(20)")
    private Long watchedNo;

    @ManyToOne
    @JoinColumn(name = "profileNo")
    private MovieProfile profile;
    @ManyToOne
    @JoinColumn(name = "detailNo")
    private ContentsDetail contentsDetail;

    @Column(name = "lastWatchedDate", columnDefinition = "DATETIME default CURRENT_TIMESTAMP()",
            insertable = false, updatable = true)
    private LocalDateTime lastWatchedDate;
    @Column(name = "watchedAt", columnDefinition = "Time")
    private LocalTime watchedAt;
}
