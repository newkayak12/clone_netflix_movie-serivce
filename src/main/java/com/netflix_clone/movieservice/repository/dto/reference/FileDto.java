package com.netflix_clone.movieservice.repository.dto.reference;

import com.netflix_clone.movieservice.component.enums.FileType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Created on 2023-05-19
 * Project file-service
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class FileDto implements Serializable {
    private Long fileNo;
    private Long tableNo;
    private FileType fileType;
    private String storedFileName;
    private String originalFileName;
    private Integer orders;
    private String contentType;
    private Long fileSize;

    @QueryProjection
    public FileDto(Long fileNo, Long tableNo, FileType fileType, String storedFileName, String originalFileName, Integer orders, String contentType, Long fileSize) {
        this.fileNo = fileNo;
        this.tableNo = tableNo;
        this.fileType = fileType;
        this.storedFileName = storedFileName;
        this.originalFileName = originalFileName;
        this.orders = orders;
        this.contentType = contentType;
        this.fileSize = fileSize;
    }
}
