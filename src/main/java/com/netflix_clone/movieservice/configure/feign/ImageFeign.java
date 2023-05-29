package com.netflix_clone.movieservice.configure.feign;

import com.netflix_clone.movieservice.enums.FileType;
import com.netflix_clone.movieservice.repository.dto.reference.FileDto;
import com.netflix_clone.movieservice.repository.dto.reference.FileRequest;
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
@FeignClient(name = "netflix-clone-image-service")
public interface ImageFeign {

    @PostMapping(value = "/save", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<List<FileDto>> save(@RequestPart(value = "requestList") List<FileRequest> requestList);
    @GetMapping(value = "/{tableNo}/{fileType}")
    public ResponseEntity<FileDto> file(@PathVariable(name = "tableNo") Long tableNo, @PathVariable(name = "fileType") FileType fileType);
    @GetMapping(value = "/{tableNo}/{fileType}")
    public ResponseEntity<List<FileDto>> files(@PathVariable(name = "tableNo") Long tableNo, @PathVariable(name = "fileType") FileType fileType);
    @GetMapping(value = "/{fileType}")
    public ResponseEntity<List<FileDto>> files(@RequestParam(value = "tableNos") List<Long> tableNos, @PathVariable(name = "fileType") FileType fileType);
    @DeleteMapping(value = "/{tableNo}/{fileType}")
    public ResponseEntity<Boolean> remove(@PathVariable(name = "tableNo") Long tableNo, @PathVariable(name = "fileType")  FileType fileType);
    @DeleteMapping(value = "/{fileType}")
    public ResponseEntity<Boolean> remove(@RequestParam(value = "tableNos") List<Long> tableNos, @PathVariable(name = "fileType")  FileType fileType);
    @DeleteMapping(value = "/include")
    public ResponseEntity<Boolean> removeIn(@RequestParam(value = "files") List<FileDto> files);
    @DeleteMapping(value = "/exclude")
    public ResponseEntity<Boolean> removeNotIn(@RequestParam(value = "files") List<FileDto> files);
}
