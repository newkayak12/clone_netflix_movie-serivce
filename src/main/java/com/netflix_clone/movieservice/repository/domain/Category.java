package com.netflix_clone.movieservice.repository.domain;

import javax.persistence.*;

@Table(name = "category")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryNo")
    private Long categoryNo;

    @ManyToOne
    @JoinColumn(name = "parentNo", referencedColumnName = "categoryNo", nullable = true)
    private Category parentCategory;

    @Column(name = "name", columnDefinition = "VARCHAR(200)")
    private String name;

    @Column(name = "isLeaf", columnDefinition = "BIT(1) default FALSE")
    private Boolean isLeaf;
}
