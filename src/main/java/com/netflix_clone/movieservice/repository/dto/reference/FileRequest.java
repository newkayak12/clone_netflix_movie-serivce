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
public class FileRequest {
    private Long fileNo;
    private Long tableNo;
    private String fileType;
    private String storedFileName;
    private String originalFileName;
    private Integer orders;
    private String contentType;
    private Long fileSize;
    private MultipartFile rawFile;
}
