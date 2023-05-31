package com.netflix_clone.movieservice.repository.dto.request;

import com.netflix_clone.movieservice.enums.ContentType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ContentRequest {
    private Long categoryNo;
    private String searchText;
    private ContentType contentType;

    private Integer page;
    private Integer limit;
}
