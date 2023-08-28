package com.netflix_clone.movieservice.controller;

import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.movieservice.repository.dto.reference.WatchedDto;
import com.netflix_clone.movieservice.service.WatchedService;
import com.sun.mail.imap.protocol.BODY;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/watched")
@RequiredArgsConstructor
public class WatchedController {
    private final WatchedService service;

    @PostMapping(value = "/")
    public ResponseEntity<Boolean> makeWatched(@RequestBody WatchedDto watchedDto){
        return ResponseEntity.ok(service.makeWatched(watchedDto));
    }

    @GetMapping(value = "/")
    public ResponseEntity<PageImpl<ContentsInfoDto>> watchedContents(PageableRequest request) {
        return new ResponseEntity(service.watchedContents(request), HttpStatus.OK);
    }
}
