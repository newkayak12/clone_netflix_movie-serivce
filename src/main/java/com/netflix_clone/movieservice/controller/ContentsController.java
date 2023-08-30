package com.netflix_clone.movieservice.controller;

import com.netflix_clone.movieservice.component.exceptions.CommonException;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsDetailDto;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.reference.MovieProfileDto;
import com.netflix_clone.movieservice.repository.dto.request.ContentRequest;
import com.netflix_clone.movieservice.repository.dto.request.SaveContentRequest;
import com.netflix_clone.movieservice.repository.dto.request.SaveDetailRequest;
import com.netflix_clone.movieservice.service.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/contents")
@RequiredArgsConstructor
public class ContentsController {
    private final ContentsService service;

    @PostMapping(value = "/save/info", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ContentsInfoDto> saveContentInfo(@ModelAttribute SaveContentRequest request){
        return ResponseEntity.ok(service.saveContentInfo(request));
    }
    @PostMapping(value = "/save/detail", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ContentsDetailDto> saveContentDetail(@ModelAttribute SaveDetailRequest request){
        return ResponseEntity.ok(service.saveContentDetail(request));
    }
    @DeleteMapping(value = "/info/{contentsNo:[\\d]+}")
    public ResponseEntity<Boolean> removeContentInfo(@PathVariable Long contentsNo){
        return ResponseEntity.ok(service.removeContentInfo(contentsNo));
    }
    @DeleteMapping(value = "/detail/{detailNo:[\\d]+}")
    public ResponseEntity<Boolean> removeDetail(@PathVariable Long detailNo){
        return ResponseEntity.ok(service.removeDetail(detailNo));
    }



    @GetMapping(value = "/")
    public ResponseEntity contents(@ModelAttribute ContentRequest request){
        return new ResponseEntity(service.contents(request), HttpStatus.OK);
    }
    @GetMapping(value = "/{contentsNo:[\\d]+}")
    public ResponseEntity content(@PathVariable Long contentsNo,
                                  @ModelAttribute MovieProfileDto profileDto) throws CommonException {
        return new ResponseEntity(service.content(contentsNo, profileDto), HttpStatus.OK);
    }

    //TODO : 영상 스트리밍 공부하기
    @GetMapping(value = "/{contentsNo:[\\d]+}/{detailNo:[\\d]+}")
    public ResponseEntity<ResourceRegion> stream(@RequestHeader HttpHeaders headers,
                                                 @PathVariable(required = true) Long contentsNo,
                                                 @PathVariable(required = false) Long detailNo) throws CommonException {


        ResourceRegion resource = service.stream(headers,contentsNo, detailNo);
        return  ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(resource.getResource())
                                             .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resource);
    }

    @GetMapping(value = "/{contentsNo:[\\d]+}/{detailNo:[\\d]+}/byte")
    public ResponseEntity<ByteArrayResource> streamByte(@RequestHeader HttpHeaders headers,
                                                 @PathVariable(required = true) Long contentsNo,
                                                 @PathVariable(required = false) Long detailNo) throws CommonException {


        ByteArrayResource resource = service.streamByte(headers,contentsNo, detailNo);
        return  ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
