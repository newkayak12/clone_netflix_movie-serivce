package com.netflix_clone.movieservice.repository.dto.request;

import com.netflix_clone.movieservice.repository.dto.reference.ContentsDetailDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@ToString
public class SaveDetailRequest extends ContentsDetailDto {
    private Long contentsNo;
    private MultipartFile rawFile;
    private MultipartFile rawMovieFile;
}
