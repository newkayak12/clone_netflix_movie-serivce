package com.netflix_clone.movieservice.controller;

import com.netflix_clone.movieservice.component.exceptions.CommonException;
import com.netflix_clone.movieservice.repository.dto.request.ContentRequest;
import com.netflix_clone.movieservice.service.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/contents/")
@RequiredArgsConstructor
public class ContentsController {
    private final ContentsService service;

    @GetMapping(value = "/")
    public ResponseEntity contents(@ModelAttribute ContentRequest request){
        return new ResponseEntity(service.contents(request), HttpStatus.OK);
    }
    @GetMapping(value = "/{contentsNo:[\\d]+}")
    public ResponseEntity content(@PathVariable Long contentsNo) throws CommonException {
        return new ResponseEntity(service.content(contentsNo), HttpStatus.OK);
    }

    //TODO : 영상 스트리밍 공부하기
    @GetMapping(value = "/{contentsNo:[\\d]+}/{detailNo:[\\d]+}")
    public ResponseEntity<ResourceRegion> stream(@RequestHeader HttpHeaders headers,
                                                 @PathVariable(required = true) Long contentsNo,
                                                 @PathVariable(required = false) Long detailNo) throws CommonException {


        ResourceRegion resource = service.stream(headers,contentsNo, detailNo);
        return  ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(resource.getResource())
                        .orElse(MediaType.APPLICATION_OCTET_STREAM)
                ).body(resource);
    }
}
