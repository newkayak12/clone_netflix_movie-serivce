package com.netflix_clone.movieservice.controller;

import com.netflix_clone.movieservice.component.exceptions.CommonException;
import com.netflix_clone.movieservice.repository.dto.reference.FavoriteDto;
import com.netflix_clone.movieservice.repository.dto.reference.FavoriteWatchedDto;
import com.netflix_clone.movieservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.movieservice.repository.dto.request.SetFavoriteStatusRequest;
import com.netflix_clone.movieservice.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/favorite")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService service;

    @GetMapping(value = "/")
    public ResponseEntity<PageImpl<FavoriteWatchedDto>> favorites(@ModelAttribute PageableRequest request){
        return new ResponseEntity<PageImpl<FavoriteWatchedDto>>(service.favorites(request), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Boolean> setFavoriteStatus(@RequestBody SetFavoriteStatusRequest request) throws CommonException {
        return new ResponseEntity(service.setFavoriteStatus(request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{favoriteNo:[\\d]+}")
    public ResponseEntity<Boolean> removeFavoriteStatus(@PathVariable Long favoriteNo){
        return ResponseEntity.ok(service.removeFavoriteStatus(favoriteNo));
    }
}
