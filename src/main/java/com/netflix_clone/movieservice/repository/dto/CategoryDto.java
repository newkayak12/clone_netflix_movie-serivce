package com.netflix_clone.movieservice.repository.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.netflix_clone.movieservice.repository.domain.Category} entity
 */
@Data
public class CategoryDto implements Serializable {
    private Long categoryNo;
    private CategoryDto parentCategory;
    private String name;
    private Boolean isLeaf;
}