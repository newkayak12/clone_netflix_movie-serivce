package com.netflix_clone.movieservice.repository.domain;

import javax.persistence.*;
import java.util.List;

@Table(name = "contentPerson")
@Entity
public class ContentPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contentPersonNo", columnDefinition = "BIGINT(20)")
    private Long contentPersonNo;

    @ManyToOne
    @JoinColumn(name = "personNo")
    private Person person;
    @ManyToOne
    @JoinColumn(name = "contentsNo")
    private ContentsInfo contentsInfo;

}
