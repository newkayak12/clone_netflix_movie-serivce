package com.netflix_clone.movieservice.controller;

import com.netflix_clone.movieservice.exceptions.CommonException;
import com.netflix_clone.movieservice.repository.dto.reference.CategoryDto;
import com.netflix_clone.movieservice.repository.dto.request.CategoryRequest;
import com.netflix_clone.movieservice.repository.dto.request.SaveCategory;
import com.netflix_clone.movieservice.service.CategoryService;
import com.sun.mail.imap.protocol.BODY;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping(value = "/")
    public ResponseEntity<PageImpl<CategoryDto>> categories(@ModelAttribute CategoryRequest request){
        return new ResponseEntity<PageImpl<CategoryDto>>(service.categories(request), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Boolean> save(@RequestBody SaveCategory request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{categoryNo:[\\d]+}")
    public ResponseEntity<Boolean> remove(@PathVariable Long categoryNo) throws CommonException {
        return new ResponseEntity<Boolean>(service.remove(categoryNo), HttpStatus.OK);
    }
}
