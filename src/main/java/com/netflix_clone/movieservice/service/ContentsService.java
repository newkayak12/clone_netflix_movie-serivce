package com.netflix_clone.movieservice.service;

import com.netflix_clone.movieservice.component.configure.feign.ImageFeign;
import com.netflix_clone.movieservice.component.constant.Constants;
import com.netflix_clone.movieservice.component.enums.ContentType;
import com.netflix_clone.movieservice.component.enums.FileType;
import com.netflix_clone.movieservice.component.enums.Role;
import com.netflix_clone.movieservice.component.exceptions.BecauseOf;
import com.netflix_clone.movieservice.component.exceptions.CommonException;
import com.netflix_clone.movieservice.repository.contentPersonRepository.ContentPersonRepository;
import com.netflix_clone.movieservice.repository.contentsDetailRepository.ContentsDetailRepository;
import com.netflix_clone.movieservice.repository.contentsRepository.ContentsRepository;
import com.netflix_clone.movieservice.repository.domain.ContentPerson;
import com.netflix_clone.movieservice.repository.domain.ContentsDetail;
import com.netflix_clone.movieservice.repository.domain.ContentsInfo;
import com.netflix_clone.movieservice.repository.dto.reference.*;
import com.netflix_clone.movieservice.repository.dto.request.ContentRequest;
import com.netflix_clone.movieservice.repository.dto.request.SaveContentRequest;
import com.netflix_clone.movieservice.repository.dto.request.SaveDetailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.newkayak.FileUpload.FileResult;
import org.newkayak.FileUpload.FileUpload;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ContentsService {
    private final ContentsRepository repository;
    private final ContentsDetailRepository detailRepository;
    private final ContentPersonRepository contentPersonRepository;
    private final ImageFeign imageFeign;
    private final ModelMapper mapper;
    private final FileUpload upload;

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

            UrlResource video = new UrlResource(Constants.MOVIE_PATH + path);
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

    public ContentsInfoDto saveContentInfo(SaveContentRequest request) {
        ContentsInfo info = repository.save(mapper.map(request, ContentsInfo.class));
        List<ContentPerson> persons = request.getPeopleNo()
                                             .stream()
                                             .map( peopleNo -> {
                                                 ContentPersonDto dto = new ContentPersonDto();
                                                 PersonDto personDto = new PersonDto();
                                                 personDto.setPersonNo(peopleNo);
                                                 dto.setPerson(personDto);
                                                 dto.setContentsInfo(mapper.map(info, ContentsInfoDto.class));
                                                 return mapper.map(dto, ContentPerson.class);
                                             })
                                             .collect(Collectors.toList());
        info.connectWithPerson(persons);

        if (Objects.nonNull(request.getRawFiles())) {
            FileRequests fileRequest = new FileRequests();
            fileRequest.setRawFiles(request.getRawFiles());
            fileRequest.setTableNo(info.getContentsNo());
            fileRequest.setFileType(FileType.CONTENTS.name());
            imageFeign.saves(fileRequest);
        }

        return mapper.map(info, ContentsInfoDto.class);
    }

    public List<ContentsDetailDto> saveContentDetail(List<SaveDetailRequest> request) {
        Stream<SaveDetailRequest> requestStream = request.stream().sequential();
        if(request.size() >= 10) requestStream.parallel();
        ContentsInfoDto infoDto = mapper.map(
                repository.findContentsInfoByContentsNo(request.stream().findFirst().get().getContentsNo()),
                ContentsInfoDto.class
        );

        return requestStream.map( element -> {
         ContentsDetailDto detailDto = mapper.map(repository, ContentsDetailDto.class);
         detailDto.setContentsInfo(infoDto);

        ContentsDetail detail = detailRepository.save(mapper.map(detailDto, ContentsDetail.class));

        FileRequest fileRequest = new FileRequest();
        fileRequest.setRawFile(element.getRawFile());
        fileRequest.setTableNo(detail.getDetailNo());
        fileRequest.setFileType(FileType.THUMBNAIL.name());
        imageFeign.save(fileRequest);


        if(Objects.nonNull(element.getRawMovieFile())){
            FileResult result = upload.upload(false, element.getRawMovieFile()).stream().findFirst().get();
            detail.attachFileLocation(result.getStoredFileName());
        }
        //TODO :: MOVIE FILE

        return mapper.map(detail, ContentsDetailDto.class);
        }).collect(Collectors.toList());
    }

    public Boolean removeContentInfo(Long contentsNo) {
        detailRepository.findContentsDetailByContentsInfo_ContentsNo(contentsNo)
                        .forEach(detail -> {
                            this.removeDetail(detail.getDetailNo());
                        });
        repository.deleteById(contentsNo);
        imageFeign.remove(contentsNo, FileType.CONTENTS);
        return true;
    }

    public Boolean removeDetail(Long detailNo) {
        ContentsDetail detail = detailRepository.findContentsDetailByDetailNo(detailNo);
        detailRepository.delete(detail);

        imageFeign.remove(detailNo, FileType.THUMBNAIL);
        FileResult result = new FileResult(null, detail.getStoredLocation(), null, null);
        upload.remove(result);
        return true;
    }
}
