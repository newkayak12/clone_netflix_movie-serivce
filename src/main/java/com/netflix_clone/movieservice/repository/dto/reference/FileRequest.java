package com.netflix_clone.movieservice.repository.dto.reference;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created on 2023-05-19
 * Project file-service
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class FileRequest extends FileDto {
    private MultipartFile rawFile;
}
