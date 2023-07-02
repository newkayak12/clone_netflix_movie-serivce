package com.netflix_clone.movieservice.repository.contentsRepository;

import com.netflix_clone.movieservice.component.wrap.RestPage;
import com.netflix_clone.movieservice.repository.domain.ContentsInfo;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.reference.MovieProfileDto;
import com.netflix_clone.movieservice.repository.dto.request.ContentRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

public interface ContentsRepositoryCustom {
    PageImpl<ContentsInfoDto> contents(ContentRequest request, Pageable pageable);
    Optional<ContentsInfoDto> content(Long contentNo, MovieProfileDto profileDto);
}
