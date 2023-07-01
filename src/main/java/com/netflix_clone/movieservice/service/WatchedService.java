package com.netflix_clone.movieservice.service;


import com.netflix_clone.movieservice.component.configure.feign.ImageFeign;
import com.netflix_clone.movieservice.component.enums.FileType;
import com.netflix_clone.movieservice.repository.domain.Watched;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.movieservice.repository.dto.reference.WatchedDto;
import com.netflix_clone.movieservice.repository.watchedRepository.WatchedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class WatchedService {
    private final WatchedRepository repository;
    private final ImageFeign imageFeign;
    private final ModelMapper mapper;

    public Boolean makeWatched(WatchedDto watchedDto) {
        return Optional.ofNullable(repository.save(mapper.map(watchedDto, Watched.class))).isPresent();
    }

    public PageImpl<ContentsInfoDto> watchedContents(PageableRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());
        return (PageImpl<ContentsInfoDto>) repository.watchedContents(request, pageable)
                .map( result -> {
                    result.setImages(imageFeign.files(result.getContentsNo(), FileType.CONTENTS).getBody());
                    return result;
                });

    }


}
