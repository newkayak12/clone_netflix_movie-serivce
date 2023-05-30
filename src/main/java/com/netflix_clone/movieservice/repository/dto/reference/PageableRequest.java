package com.netflix_clone.movieservice.repository.dto.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageableRequest {
    private Integer page;
    private Integer limit;
    private Long tableNo;
    private String searchText;
}
