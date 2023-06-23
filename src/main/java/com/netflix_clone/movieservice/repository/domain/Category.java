package com.netflix_clone.movieservice.repository.domain;

import lombok.ToString;

import javax.persistence.*;

@Table(name = "category")
@Entity
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryNo")
    private Long categoryNo;

    @ManyToOne()
    @JoinColumn(name = "parentNo", referencedColumnName = "categoryNo",
                nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category parentCategory;

    @Column(name = "name", columnDefinition = "VARCHAR(200)")
    private String name;

    @Column(name = "isLeaf", columnDefinition = "BIT(1) default FALSE")
    private Boolean isLeaf;
}
