package com.netflix_clone.movieservice.repository.favoriteRepository;

import com.netflix_clone.movieservice.repository.dto.reference.FavoriteWatchedDto;
import com.netflix_clone.movieservice.repository.dto.reference.PageableRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface FavoriteRepositoryCustom {
    PageImpl<FavoriteWatchedDto> favorites(PageableRequest request, Pageable pageable);
    Boolean removeFavoriteStatus(Long favoriteNo);
}
