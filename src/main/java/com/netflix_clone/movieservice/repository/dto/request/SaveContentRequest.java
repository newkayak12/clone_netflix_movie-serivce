package com.netflix_clone.movieservice.repository.dto.request;

import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
public class SaveContentRequest extends ContentsInfoDto {
    private List<MultipartFile> rawFiles;
}
