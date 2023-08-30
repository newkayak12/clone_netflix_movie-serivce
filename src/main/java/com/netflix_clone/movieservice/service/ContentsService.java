package com.netflix_clone.movieservice.service;

import com.netflix_clone.movieservice.component.configure.feign.ImageFeign;
import com.netflix_clone.movieservice.component.constant.Constants;
import com.netflix_clone.movieservice.component.enums.FileType;
import com.netflix_clone.movieservice.component.enums.Role;
import com.netflix_clone.movieservice.component.exceptions.BecauseOf;
import com.netflix_clone.movieservice.component.exceptions.CommonException;
import com.netflix_clone.movieservice.component.wrap.RestPage;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.io.FileInputStream;
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
    private final ContentPersonRepository contentPersonRepository;
    private final ImageFeign imageFeign;
    private final ModelMapper mapper;
    private final FileUpload upload;

    @Transactional(readOnly = true)
    @Cacheable(key = "#request", cacheNames = "content", unless = "#result == null", cacheManager = "gsonCacheManager")
    public RestPage<ContentsInfoDto> contents(ContentRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());
        return new RestPage<ContentsInfoDto> (repository.contents(request, pageable)
                .map(contentsInfoDto -> {
                    contentsInfoDto.setImages(imageFeign.files(contentsInfoDto.getContentsNo(), FileType.CONTENTS).getBody());
                    return contentsInfoDto;
                }));
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#contentsNo", cacheNames = "content", unless = "#result == null", cacheManager = "gsonCacheManager")
    public ContentsInfoDto content(Long contentsNo, MovieProfileDto profileDto) throws CommonException {
        return  repository.content(contentsNo, profileDto)
                .map(contentsInfoDto -> {
                    contentsInfoDto.setImages(imageFeign.files(contentsInfoDto.getContentsNo(), FileType.CONTENTS).getBody());

                    contentsInfoDto.setPeople(contentsInfoDto.getPeople().stream().distinct().collect(Collectors.toList()));
                    contentsInfoDto.setDetails(contentsInfoDto.getDetails().stream().distinct().collect(Collectors.toList()));



                    contentsInfoDto.getDetails().parallelStream()
                    .forEach(detail -> {
                        detail.setThumbnail(imageFeign.file(detail.getDetailNo(), FileType.THUMBNAIL).getBody());
                    });
                    contentsInfoDto.getPeople().stream().parallel().forEach(person -> {
                        person.setFile(
                                Optional.ofNullable(imageFeign.file(
                                        person.getPersonNo(),
                                        person.getRole().equals(Role.DIRECTOR) ? FileType.DIRECTOR : FileType.ACTOR
                                )).map(HttpEntity::getBody).orElseGet(() -> null)
                        );
                    });

//                    contentsInfoDto.preventInfiniteRecursive(); //TODO :: 순환 참조 방지 -> 더 좋은 방법이 있지 않을까?
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

            if (Objects.isNull(detailNo)) {
                path = mapper.map(repository.findContentsInfoByContentsNo(contentsNo), ContentsInfoDto.class).getStoredLocation();
            } else {
                path = mapper.map(detailRepository.findContentsDetailByDetailNo(detailNo), ContentsDetailDto.class).getStoredLocation();
                log.warn("REPO {}", detailRepository.findContentsDetailByDetailNo(detailNo).toString());
            }

            UrlResource video = new UrlResource("file://"+Constants.MOVIE_PATH+"/origin/" + path);
            final long chunkSize = 2000000L;
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
            e.printStackTrace();
            throw new CommonException(BecauseOf.UNKNOWN_ERROR);
        }
        return resourceRegion;
    }

    @Transactional(readOnly = true)
    public ByteArrayResource streamByte(HttpHeaders headers, Long contentsNo, Long detailNo) throws CommonException {
        log.warn("HTTP BYTE?? {}", headers.getRange());


        try {
            String path = "";

            if (Objects.isNull(detailNo)) {
                path = mapper.map(repository.findContentsInfoByContentsNo(contentsNo), ContentsInfoDto.class).getStoredLocation();
            } else {
                path = mapper.map(detailRepository.findContentsDetailByDetailNo(detailNo), ContentsDetailDto.class).getStoredLocation();
                log.warn("REPO {}", detailRepository.findContentsDetailByDetailNo(detailNo).toString());
            }

            UrlResource video = new UrlResource("file://"+Constants.MOVIE_PATH+"/origin/" + path);
            return   new ByteArrayResource(FileCopyUtils.copyToByteArray(new FileInputStream(Constants.MOVIE_PATH+"/origin/" + path)));
        } catch (Exception e) {

        }

        return null;
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

    public ContentsDetailDto saveContentDetail(SaveDetailRequest request) {
        ContentsInfoDto infoDto = mapper.map(
                repository.findContentsInfoByContentsNo(request.getContentsNo()),
                ContentsInfoDto.class
        );

         ContentsDetailDto detailDto = mapper.map(request, ContentsDetailDto.class);
         detailDto.setContentsInfo(infoDto);

        ContentsDetail detail = detailRepository.save(mapper.map(detailDto, ContentsDetail.class));

        FileRequest fileRequest = new FileRequest();
        fileRequest.setRawFile(request.getRawFile());
        fileRequest.setTableNo(detail.getDetailNo());
        fileRequest.setFileType(FileType.THUMBNAIL.name());
        imageFeign.save(fileRequest);


        if(Objects.nonNull(request.getRawMovieFile())){
            FileResult result = upload.upload(false, request.getRawMovieFile()).stream().findFirst().get();
            detail.attachFileLocation(result.getStoredFileName());
        }
        //TODO :: MOVIE FILE

        return mapper.map(detail, ContentsDetailDto.class);
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
