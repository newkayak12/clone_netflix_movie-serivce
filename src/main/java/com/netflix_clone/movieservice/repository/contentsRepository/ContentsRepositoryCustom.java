package com.netflix_clone.movieservice.repository.contentsRepository;

import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.request.ContentRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public interface ContentsRepositoryCustom {
    PageImpl<ContentsInfoDto> contents(ContentRequest request, Pageable pageable);
    ContentsInfoDto content(Long contentNo);
}
