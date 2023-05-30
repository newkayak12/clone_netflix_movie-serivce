package com.netflix_clone.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/contents/")
@RequiredArgsConstructor
public class ContentsController {

    @GetMapping(value = "/")
    public ResponseEntity contents(){
        return new ResponseEntity(null, HttpStatus.OK);
    }
    @GetMapping(value = "/{contentsNo:[\\d]+}")
    public ResponseEntity content(){
        return new ResponseEntity(null, HttpStatus.OK);
    }

    //TODO : 영상 스트리밍 공부하기
    @PatchMapping(value = "/{contentsNo:[\\d]+}")
    public ResponseEntity<FileSystemResource> stream(){
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
