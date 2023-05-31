package com.netflix_clone.movieservice.service;

import com.netflix_clone.movieservice.configure.feign.ImageFeign;
import com.netflix_clone.movieservice.enums.FileType;
import com.netflix_clone.movieservice.exceptions.BecauseOf;
import com.netflix_clone.movieservice.exceptions.CommonException;
import com.netflix_clone.movieservice.repository.contentsRepository.ContentsRepository;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.request.ContentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class ContentsService {
    private final ContentsRepository repository;
    private final ImageFeign imageFeign;

    @Transactional(readOnly = true)
    public PageImpl<ContentsInfoDto> contents(ContentRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());
        return (PageImpl<ContentsInfoDto>) repository.contents(request, pageable)
                .map(contentsInfoDto -> {
                    contentsInfoDto.setImages(imageFeign.files(contentsInfoDto.getContentsNo(), FileType.CONTENTS).getBody());
                    return contentsInfoDto;
                });
    }

    public ContentsInfoDto content(Long contentsNo) throws CommonException {
        return Optional.ofNullable(repository.content(contentsNo))
                .map(contentsInfoDto -> {
                    contentsInfoDto.setImages(imageFeign.files(contentsInfoDto.getContentsNo(), FileType.CONTENTS).getBody());
                    return contentsInfoDto;
                })
                .orElseThrow(() -> new CommonException(BecauseOf.NO_DATA));
    }
}
