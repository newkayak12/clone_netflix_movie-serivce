package com.netflix_clone.movieservice.repository.personRepository;

import com.netflix_clone.movieservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.movieservice.repository.dto.reference.PersonDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonRepositoryCustom {
    PageImpl<PersonDto> people(Pageable pageable, PageableRequest request);

    Optional<PersonDto> person(Long personNo);
}
