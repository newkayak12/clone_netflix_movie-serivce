package com.netflix_clone.movieservice.service;

import com.netflix_clone.movieservice.component.configure.feign.ImageFeign;
import com.netflix_clone.movieservice.component.constant.Constants;
import com.netflix_clone.movieservice.component.enums.FileType;
import com.netflix_clone.movieservice.component.enums.Role;
import com.netflix_clone.movieservice.component.exceptions.BecauseOf;
import com.netflix_clone.movieservice.component.exceptions.CommonException;
import com.netflix_clone.movieservice.repository.contentsDetailRepository.ContentsDetailRepository;
import com.netflix_clone.movieservice.repository.contentsRepository.ContentsRepository;
import com.netflix_clone.movieservice.repository.dto.reference.ContentPersonDto;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsDetailDto;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.reference.PersonDto;
import com.netflix_clone.movieservice.repository.dto.request.ContentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ContentsService {
    private final ContentsRepository repository;
    private final ContentsDetailRepository detailRepository;
    private final ImageFeign imageFeign;
    private final ModelMapper mapper;

    @Transactional(readOnly = true)
    public PageImpl<ContentsInfoDto> contents(ContentRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());
        return (PageImpl<ContentsInfoDto>) repository.contents(request, pageable)
                .map(contentsInfoDto -> {
                    contentsInfoDto.setImages(imageFeign.files(contentsInfoDto.getContentsNo(), FileType.CONTENTS).getBody());
                    return contentsInfoDto;
                });
    }

    @Transactional(readOnly = true)
    public ContentsInfoDto content(Long contentsNo) throws CommonException {
        return Optional.ofNullable(repository.content(contentsNo))
                .map(contentsInfo -> {
                    ContentsInfoDto contentsInfoDto = mapper.map(contentsInfo, ContentsInfoDto.class);
                    contentsInfoDto.setImages(imageFeign.files(contentsInfoDto.getContentsNo(), FileType.CONTENTS).getBody());

                    List<ContentsDetailDto> details = contentsInfo.getDetails().parallelStream().map(detailInfo -> {
                        ContentsDetailDto detail = mapper.map(detailInfo, ContentsDetailDto.class);
                        detail.setThumbnail(imageFeign.file(detail.getDetailNo(), FileType.THUMBNAIL).getBody());
                        return detail;
                    }).collect(Collectors.toList());
                    contentsInfoDto.setDetails(details);

                    List<ContentPersonDto> contentPerson = contentsInfo.getContentPeople().parallelStream().map(conPer -> {
                        ContentPersonDto contentPersonDto = mapper.map(conPer, ContentPersonDto.class);
                        PersonDto personDto = contentPersonDto.getPerson();
                        personDto.setFile(imageFeign.file(personDto.getPersonNo(),
                                         personDto.getRole().equals(Role.DIRECTOR) ? FileType.DIRECTOR : FileType.ACTOR).getBody());

                        contentPersonDto.setPerson(personDto);
                        return contentPersonDto;
                    }).collect(Collectors.toList());
                    contentsInfoDto.setContentPeople(contentPerson);

                    return contentsInfoDto;
                })
                .orElseThrow(() -> new CommonException(BecauseOf.NO_DATA));
    }

    @Transactional(readOnly = true)
    public ResourceRegion stream(HttpHeaders headers, Long contentsNo, Long detailNo) throws CommonException {
        log.warn("HTTP RANDGE? {}", headers.getRange());


        ResourceRegion resourceRegion = null;
        try {
            String path = "";

            if (Objects.nonNull(detailNo)) {
                path = mapper.map(repository.findContentsInfoByContentsNo(contentsNo), ContentsInfoDto.class).getStoredLocation();
            } else {
                path = mapper.map(detailRepository.findContentsDetailByDetailNo(detailNo), ContentsDetailDto.class).getStoredLocation();
            }

            UrlResource video = new UrlResource(Constants.FILE_PATH + path);
            final long chunkSize = 1000000L;
            long contentLength = video.contentLength();

            Optional<HttpRange> optional = headers.getRange().stream().findFirst();
            HttpRange httpRange;
            if (optional.isPresent()) {
                httpRange = optional.get();
                long start = httpRange.getRangeStart(contentLength);
                long end = httpRange.getRangeEnd(contentLength);


                long rangeLength = Long.min(chunkSize, end - start + 1);
                log.warn("IF PRESENT => rangeLength {}", rangeLength);
                resourceRegion = new ResourceRegion(video, start, rangeLength);
            } else {
                long rangeLength = Long.min(chunkSize, contentLength);


                log.warn("NOT PRESENT => rangeLength {}", rangeLength);
                resourceRegion = new ResourceRegion(video, 0, rangeLength);
            }
        } catch ( Exception e ){
            throw new CommonException(BecauseOf.UNKNOWN_ERROR);
        }
        return resourceRegion;
    }
}
