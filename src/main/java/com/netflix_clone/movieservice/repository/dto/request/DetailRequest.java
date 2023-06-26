package com.netflix_clone.movieservice.repository.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class DetailRequest {
    List<SaveDetailRequest> request = new ArrayList<>();
}
