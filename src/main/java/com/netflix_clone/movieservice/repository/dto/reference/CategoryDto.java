package com.netflix_clone.movieservice.repository.dto.reference;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;

/**
 * A DTO for the {@link com.netflix_clone.movieservice.repository.domain.Category} entity
 */
@Data
@NoArgsConstructor
public class CategoryDto implements Serializable {
    private Long categoryNo;
    private CategoryDto parentCategory;
    private String name;
    private Boolean isLeaf;


    @QueryProjection
    public CategoryDto(Long categoryNo) {
        this.categoryNo = categoryNo;
    }

    @QueryProjection
    public CategoryDto(Long categoryNo, String name) {
        this.categoryNo = categoryNo;
        this.name = name;
    }

    @QueryProjection
    public CategoryDto(Long categoryNo, CategoryDto parentCategory, String name, Boolean isLeaf) {
        this.categoryNo = categoryNo;
        this.parentCategory = parentCategory;
        this.name = name;
        this.isLeaf = isLeaf;
    }
}