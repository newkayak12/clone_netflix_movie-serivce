package com.netflix_clone.movieservice.repository.watchedRepository;

import com.netflix_clone.movieservice.repository.domain.Watched;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.movieservice.repository.dto.reference.WatchedDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

public interface WatchedRepositoryCustom {
    PageImpl<ContentsInfoDto>  watchedContents(PageableRequest request, Pageable pageable);
}
