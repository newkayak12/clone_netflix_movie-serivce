package com.netflix_clone.movieservice.controller;

import com.netflix_clone.movieservice.component.constant.Constants;
import com.netflix_clone.movieservice.component.enums.ContentType;
import com.netflix_clone.movieservice.component.enums.Role;
import com.netflix_clone.movieservice.repository.dto.reference.CategoryDto;
import com.netflix_clone.movieservice.repository.dto.reference.PersonDto;
import com.netflix_clone.movieservice.repository.dto.request.SaveContentRequest;
import com.netflix_clone.movieservice.util.AbstractControllerTest;
import com.netflix_clone.movieservice.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.newkayak.FileUpload.FileResult;
import org.newkayak.FileUpload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties
@ActiveProfiles(value = {"local"})
class ContentsControllerTest extends AbstractControllerTest {
    private final String prefix = "/api/v1/contents/";
    @Autowired
    private FileUpload upload;
    @DisplayName(value = "컨텐츠 저장/수정")
    @Nested
    class SaveContentInfo {
        private MockMultipartFile rawFile;
        @BeforeEach
        public void prepareFile () {
            this.rawFile = TestUtil.getMockMultiPartFile("/Users/sanghyeonkim/Downloads/R1280x0.png", "image", "rawFile");
        }
        @Test
        @DisplayName(value = "info 저장")
        void saveContentInfo() throws Exception {
            SaveContentRequest request = new SaveContentRequest();
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryNo(101L);

            request.setCategory(categoryDto);
            request.setPeopleNo(Arrays.asList(2059L));
            request.setTitle("[제로초] 인간 JS 되기");
            request.setDescription("JS 에 대해서 알아보는 시간");
            request.setReleaseDate(LocalDateTime.now().plusMonths(2L));
            request.setContentType(ContentType.TV);
            request.setServiceDueDate(LocalDateTime.now().plusYears(99L));

            LocalDateTime now = LocalDateTime.now();
            String release = now
                              .plusMonths(2L)
                              .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                              .substring(0, now
                                      .plusMonths(2L)
                                      .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).lastIndexOf("."));
            String due = now
                    .plusYears(99)
                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    .substring(0, now
                            .plusYears(99)
                            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).lastIndexOf("."));

            mockMvc.perform(
                multipart(String.format("%s/save/info", prefix))
                .file(rawFile)
                .param("category.categoryNo", "101")
                .param("peopleNo", "2059")
                .param("title","[제로초] 인간 JS 되기")
                .param("description","JS 에 대해서 알아보는 시간")
                .param("releaseDate", release)
                .param("contentType", ContentType.TV.name())
                .param("serviceDueDate", due)
            ).andExpect(status().isOk());
        }
    }

    @Test
    @DisplayName(value = "detail 저장")
    void saveContentDetail() {
//        2,JS 에 대해서 알아보는 시간,,2023-06-25 11:31:13,2023-08-25 11:31:15,2122-06-25 11:31:15,,[제로초] 인간 JS 되기,0,101,TV
//
//        detailNo
//        duration
//        episode
//        season
//        storedLocation
//        subTitle
//        contentsNo

    }

    @Test
    void removeContentInfo() {
    }

    @Test
    void removeDetail() {
    }

    @Test
    void contents() {
    }

    @Test
    void content() {
    }

    @Test
    void stream() {
    }

    @Test
    void mediaFile() {
        MockMultipartFile file = TestUtil.getMockMultiPartFile("/Users/sanghyeonkim/Downloads/port/netflixClone/source/zerocho_js/02.mp4", "video", "01");

        List<FileResult> result = upload.upload(false, file);
        result.forEach( v -> {
            System.out.println(v.getOriginalFileName());
            System.out.println(v.getStoredFileName());
            System.out.println(v.getContentType());
            System.out.println(v.getFileSize());
        });
    }
}