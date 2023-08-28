package com.netflix_clone.movieservice.repository.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalTime;

@Table(name = "contentsDetail")
@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@ToString
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

    public void attachFileLocation(String location){
        this.storedLocation = location;
    }
}
