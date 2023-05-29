package com.netflix_clone.movieservice.repository.dto.request;

import com.netflix_clone.movieservice.repository.dto.reference.CategoryDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class SaveCategory extends CategoryDto {
}
