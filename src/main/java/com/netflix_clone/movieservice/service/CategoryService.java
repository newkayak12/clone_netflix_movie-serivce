package com.netflix_clone.movieservice.service;

import com.netflix_clone.movieservice.component.exceptions.BecauseOf;
import com.netflix_clone.movieservice.component.exceptions.CommonException;
import com.netflix_clone.movieservice.repository.categoryRepository.CategoryRepository;
import com.netflix_clone.movieservice.repository.domain.Category;
import com.netflix_clone.movieservice.repository.dto.reference.CategoryDto;
import com.netflix_clone.movieservice.repository.dto.request.CategoryRequest;
import com.netflix_clone.movieservice.repository.dto.request.SaveCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final ModelMapper mapper;

    @Transactional(readOnly = true)
    public PageImpl<CategoryDto> categories(CategoryRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());
        return  repository.categories(pageable, request);
    }

    public Boolean save(SaveCategory request) {
        if(!request.getIsLeaf()) request.setParentCategory(null);
        Category category = mapper.map(request, Category.class);

        return Optional.ofNullable(repository.save(category)).map(Objects::nonNull).orElseGet(() -> false);
    }

    public Boolean remove(Long categoryNo) throws CommonException {
        if(repository.isCategoryUsed(categoryNo)) throw new CommonException(BecauseOf.CANNOT_DELETE);
        return repository.remove(categoryNo);
    }
}
