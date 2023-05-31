package com.netflix_clone.movieservice.controller;

import com.netflix_clone.movieservice.exceptions.CommonException;
import com.netflix_clone.movieservice.repository.dto.request.ContentRequest;
import com.netflix_clone.movieservice.service.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @PatchMapping(value = "/{contentsNo:[\\d]+}")
    public ResponseEntity<FileSystemResource> stream(@PathVariable Long contentsNo){
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
