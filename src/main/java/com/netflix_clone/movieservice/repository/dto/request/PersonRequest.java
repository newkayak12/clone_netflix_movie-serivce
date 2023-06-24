package com.netflix_clone.movieservice.repository.dto.request;

import com.netflix_clone.movieservice.repository.dto.reference.PersonDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class PersonRequest extends PersonDto {
    private MultipartFile rawFile;
}
