package com.netflix_clone.movieservice.repository.dto.request;

import com.netflix_clone.movieservice.repository.dto.reference.ContentsDetailDto;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.reference.FileDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;

@Data
@Getter
@Setter
@ToString(callSuper = true)
public class SaveDetailRequest extends ContentsDetailDto {
    private Long contentsNo;
    private MultipartFile rawFile;
    private MultipartFile rawMovieFile;

    public SaveDetailRequest(Long detailNo, Integer season, Integer episode, String subTitle, LocalTime duration, String storedLocation, ContentsInfoDto contentsInfo, FileDto thumbnail, MultipartFile rawFile, MultipartFile rawMovieFile) {
        super(detailNo, season, episode, subTitle, duration, storedLocation, contentsInfo, thumbnail, rawFile);
        this.rawFile = rawFile;
        this.rawMovieFile = rawMovieFile;
    }
}
