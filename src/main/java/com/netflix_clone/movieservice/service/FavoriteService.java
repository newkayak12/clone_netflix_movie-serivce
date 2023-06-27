package com.netflix_clone.movieservice.service;

import com.netflix_clone.movieservice.component.configure.feign.ImageFeign;
import com.netflix_clone.movieservice.component.enums.FileType;
import com.netflix_clone.movieservice.component.exceptions.BecauseOf;
import com.netflix_clone.movieservice.component.exceptions.CommonException;
import com.netflix_clone.movieservice.repository.domain.Favorite;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.reference.FavoriteDto;
import com.netflix_clone.movieservice.repository.dto.reference.FavoriteWatchedDto;
import com.netflix_clone.movieservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.movieservice.repository.dto.request.SetFavoriteStatusRequest;
import com.netflix_clone.movieservice.repository.favoriteRepository.FavoriteRepository;
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
public class FavoriteService {
    private final FavoriteRepository repository;
    private final ImageFeign imageFeign;
    private final ModelMapper mapper;

    public PageImpl<FavoriteWatchedDto> favorites(PageableRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());
        return (PageImpl<FavoriteWatchedDto>) repository.favorites(request, pageable).map( result -> {
            ContentsInfoDto contentsInfoDto = result.getContentsInfo();
            contentsInfoDto.setImages(imageFeign.files(contentsInfoDto.getContentsNo(), FileType.CONTENTS).getBody());

            if(Objects.isNull(result.getFavorite())) {
                FavoriteDto favoriteDto = new FavoriteDto();
                favoriteDto.emptyFavorite(contentsInfoDto.getContentsNo(), request.getTableNo());
                result.setFavorite(favoriteDto);
            }

            result.setContentsInfo(contentsInfoDto);
            return result;
        });
    }

    public FavoriteDto setFavoriteStatus(SetFavoriteStatusRequest request) throws CommonException {
        Favorite favorite = mapper.map(request, Favorite.class);
        return Optional.ofNullable(repository.save(favorite))
                       .map(result -> mapper.map(result, FavoriteDto.class))
                       .orElseThrow(() -> new CommonException(BecauseOf.SAVE_FAILURE));
    }
}
