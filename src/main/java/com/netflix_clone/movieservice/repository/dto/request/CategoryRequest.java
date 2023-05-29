package com.netflix_clone.movieservice.repository.dto.request;

import com.netflix_clone.movieservice.repository.dto.reference.CategoryDto;
import lombok.*;

@Getter
@Setter
@ToString
public class CategoryRequest extends CategoryDto {

    public CategoryRequest(Long categoryNo, CategoryDto parentCategory, String name, Boolean isLeaf, Integer page, Integer limit) {
        super(categoryNo, parentCategory, name, isLeaf);
        this.page = page;
        this.limit = limit;
    }
    public CategoryRequest(Long categoryNo, CategoryDto parentCategory, String name, Boolean isLeaf) {
        super(categoryNo, parentCategory, name, isLeaf);
    }

    public CategoryRequest() {
        super();
    }

    private Integer page;
    private Integer limit;
}
