package com.netflix_clone.movieservice.component.configure.feign;

import com.netflix_clone.movieservice.component.enums.FileType;
import com.netflix_clone.movieservice.repository.dto.reference.FileDto;
import com.netflix_clone.movieservice.repository.dto.reference.FileRequest;
import com.netflix_clone.movieservice.repository.dto.reference.FileRequests;
import feign.Headers;
import feign.Param;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2023-05-12
 * Project user-service
 */
@Qualifier(value = "image")
@FeignClient("netflix-clone-file-service")
public interface ImageFeign {

    @PostMapping(value = "/api/v1/file/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Headers("Content-Type: multipart/form-data")
    ResponseEntity<FileDto> save(@Param(value = "request") FileRequest request);

    @PostMapping(value = "/api/v1/file/saves", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Headers("Content-Type: multipart/form-data")
    ResponseEntity<List<FileDto>> saves(@Param(value = "requests") FileRequests requests);

    @GetMapping(value = "/api/v1/file/{tableNo}/{fileType}/mono")
    public ResponseEntity<FileDto> file(@PathVariable(name = "tableNo") Long tableNo, @PathVariable(name = "fileType") FileType fileType);
    @GetMapping(value = "/api/v1/file/{tableNo}/{fileType}")
    public ResponseEntity<List<FileDto>> files(@PathVariable(name = "tableNo") Long tableNo, @PathVariable(name = "fileType") FileType fileType);
    @GetMapping(value = "/api/v1/file/{fileType}")
    public ResponseEntity<List<FileDto>> files(@RequestParam(value = "tableNos") List<Long> tableNos, @PathVariable(name = "fileType") FileType fileType);
    @DeleteMapping(value = "/api/v1/file/{tableNo}/{fileType}")
    public ResponseEntity<Boolean> remove(@PathVariable(name = "tableNo") Long tableNo, @PathVariable(name = "fileType")  FileType fileType);
    @DeleteMapping(value = "/api/v1/file/{fileType}")
    public ResponseEntity<Boolean> remove(@RequestParam(value = "tableNos") List<Long> tableNos, @PathVariable(name = "fileType")  FileType fileType);
    @DeleteMapping(value = "/api/v1/file/include")
    public ResponseEntity<Boolean> removeIn(@RequestParam(value = "fileNos") List<Long> fileNos);
    @DeleteMapping(value = "/api/v1/file/exclude")
    public ResponseEntity<Boolean> removeNotIn(@RequestParam(value = "fileNos") List<Long> fileNos);

}
