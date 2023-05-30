package com.netflix_clone.movieservice.controller;

import com.netflix_clone.movieservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.movieservice.repository.dto.reference.WatchedDto;
import com.netflix_clone.movieservice.service.WatchedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/watched/")
@RequiredArgsConstructor
public class WatchedController {
    private final WatchedService service;

    @GetMapping(value = "/")
    public ResponseEntity<PageImpl<WatchedDto>> watchedContents(PageableRequest request) {
        return new ResponseEntity<PageImpl<WatchedDto>>(service.watchedContents(request), HttpStatus.OK);
    }
}
