package com.netflix_clone.movieservice.controller;


import com.netflix_clone.movieservice.component.exceptions.CommonException;
import com.netflix_clone.movieservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.movieservice.repository.dto.reference.PersonDto;
import com.netflix_clone.movieservice.repository.dto.request.PersonRequest;
import com.netflix_clone.movieservice.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService service;

    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Boolean> save(@ModelAttribute PersonRequest request) {
        return ResponseEntity.ok(service.save(request));
    }

    @DeleteMapping(value = "/{personNo:[\\d]+}")
    public ResponseEntity<Boolean> remove(@PathVariable Long personNo) {
        return ResponseEntity.ok(service.remove(personNo));
    }

    @GetMapping(value = "/")
    public ResponseEntity<PageImpl<PersonDto>> people(@ModelAttribute PageableRequest request) {
        return ResponseEntity.ok(service.people(request));
    }

    @GetMapping(value = "/{personNo:[\\d]+}")
    public ResponseEntity<PersonDto> person(@PathVariable Long personNo) throws CommonException {
        return ResponseEntity.ok(service.person(personNo));
    }



}
