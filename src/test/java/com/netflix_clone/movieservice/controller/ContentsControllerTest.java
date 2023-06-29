package com.netflix_clone.movieservice.controller;

import com.netflix_clone.movieservice.component.constant.Constants;
import com.netflix_clone.movieservice.component.enums.ContentType;
import com.netflix_clone.movieservice.component.enums.Role;
import com.netflix_clone.movieservice.repository.dto.reference.CategoryDto;
import com.netflix_clone.movieservice.repository.dto.reference.PersonDto;
import com.netflix_clone.movieservice.repository.dto.request.SaveContentRequest;
import com.netflix_clone.movieservice.repository.dto.request.SaveDetailRequest;
import com.netflix_clone.movieservice.util.AbstractControllerTest;
import com.netflix_clone.movieservice.util.TestUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.newkayak.FileUpload.FileResult;
import org.newkayak.FileUpload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

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
            request.setTitle("[제로초] WebGame으로 배우는 React");
            request.setDescription("React 기초 다지기");
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
                .param("title","[제로초] WebGame으로 배우는 React")
                .param("description","React 기초 다지기")
                .param("releaseDate", release)
                .param("contentType", ContentType.TV.name())
                .param("serviceDueDate", due)
            ).andExpect(status().isOk());
        }
        /**
         * ISSUE ::
         * test를 gradle 환경에서 실행하면 timeout
         * Intellij로 실행하면 정삭 작동
         *
         */
        @ParameterizedTest
        @DisplayName(value = "detail 저장")
        @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
        void saveContentDetail(int number) throws Exception {
            mockMvc.perform(
                            this.generateRequest(number)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.subTitle").value(String.format("[ %s 강 ]", (number < 10 ? "0" + (number) : number))));

        }

        MockHttpServletRequestBuilder generateRequest(int value){
            try {
                MockMultipartFile image = TestUtil.getMockMultiPartFile("/Users/sanghyeonkim/Downloads/R1280x0.png", "image", "rawFile");
                MockMultipartFile movie = TestUtil.getMockMultiPartFile(
                        String.format("/Users/sanghyeonkim/Downloads/port/netflixClone/source/zerocho_react/%s.mp4", (value < 10 ? "0" + (value) : value)),
                        "video",
                        "rawMovieFile"
                );
                MockMultipartHttpServletRequestBuilder builder = multipart(String.format("%s/save/detail", prefix));
                builder
                        .file(image)
                        .file(movie)
                        .param("season", "1")
                        .param("episode", value + "")
                        .param("subTitle", String.format("[ %s 강 ]", (value < 10 ? "0" + (value) : value)))
                        //                .param(String.format("duration", )
                        .param("contentsNo", "3");
                builder.contentType(MediaType.MULTIPART_FORM_DATA);


                return builder;
            } catch (Exception e){

            }

            return null;
        }
    }


    @Nested
    @DisplayName(value = "컨텐츠 삭제")
    class RemoveContent{
        @Test
        @DisplayName(value = "컨텐츠 삭제")
        void removeContentInfo() {
        }

        @Test
        @DisplayName(value = "컨텐츠 상세 삭제")
        void removeDetail() {
        }
    }


    @Nested
    @DisplayName(value = "컨텐츠 불러오기")
    class FetchContents {
        @ParameterizedTest
        @DisplayName(value = "컨텐츠 리스트_카테고리")
        @ValueSource(ints = 101)
        void contents_category(int categoryNo) throws Exception {
            mockMvc.perform(
                    get(String.format("%s/", prefix))
                            .param("limit","10")
                            .param("page", "1")
                            .param("categoryNo", categoryNo+"")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.size()").value(2));

        }

        @ParameterizedTest
        @DisplayName(value = "컨텐츠 리스트_컨텐츠 타입")
        @ValueSource(strings = "TV")
        void contents_contentType(String contentType) throws Exception {
            mockMvc.perform(
                    get(String.format("%s/", prefix))
                            .param("limit","10")
                            .param("page", "1")
                            .param("contentType", contentType)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.size()").value(2));
        }

        @ParameterizedTest
        @DisplayName(value = "컨텐츠 리스트_제목")
        @ValueSource(strings = "react")
        void contents_title(String searchText) throws Exception {
            mockMvc.perform(
                    get(String.format("%s/", prefix))
                            .param("limit","10")
                            .param("page", "1")
                            .param("searchText",searchText)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.size()").value(1));
        }

        @ParameterizedTest
        @DisplayName(value = "컨텐츠 단일")
        @ValueSource(ints = 3)
        void content(int contentsNo) throws Exception {
            mockMvc.perform(
                get(String.format("%s/%d", prefix, contentsNo))
                .param("profileNo", "18")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.contentsNo").value(3))
            .andExpect(jsonPath("$.title").value("[제로초] WebGame으로 배우는 React"))
            .andExpect(jsonPath("$.description").value("React 기초 다지기"))
            .andExpect(jsonPath("$.details").isNotEmpty());
        }

    }


    @Test
    @DisplayName(value = "파일 저장 테스트")
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