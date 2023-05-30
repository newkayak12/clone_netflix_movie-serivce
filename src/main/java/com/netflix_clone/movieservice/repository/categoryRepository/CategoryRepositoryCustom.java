package com.netflix_clone.movieservice.repository.categoryRepository;

import com.netflix_clone.movieservice.repository.dto.reference.CategoryDto;
import com.netflix_clone.movieservice.repository.dto.request.CategoryRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface CategoryRepositoryCustom {
    PageImpl<CategoryDto> categories(Pageable pageable, CategoryRequest request);
    Boolean isCategoryUsed(Long categoryNo);
    Boolean remove(Long categoryNo);
}
