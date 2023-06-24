package com.netflix_clone.movieservice.controller;

import com.netflix_clone.movieservice.util.AbstractControllerTest;
import com.netflix_clone.movieservice.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties
@ActiveProfiles(value = {"local"})
class PersonControllerTest extends AbstractControllerTest {
    private final String prefix = "/api/v1/person";

    @Nested
    @DisplayName(value = "인물 등록/수정")
    public class SavePerson {
        @ParameterizedTest
        @Transactional
        @Rollback
        @DisplayName(value = "인물 등록")
        @CsvFileSource(resources = {"/csv/insertPerson.csv"}, numLinesToSkip = 1)
        void insertPerson(String name, String role) throws Exception {
             mockMvc.perform(
                 multipart(String.format("%s/save", prefix))
                .param("name", name)
                .param("role", role)
             )
             .andExpect(status().isOk())
             .andExpect(jsonPath("$").isBoolean())
             .andExpect(jsonPath("$").value(true));
        }

        @ParameterizedTest
        @DisplayName(value = "인물 수정")
        @Transactional
        @Rollback
        @CsvFileSource(resources = {"/csv/modifyPerson.csv"}, numLinesToSkip = 1)
        void modifyPerson(Integer personNo, String name, String role) throws Exception {
            mockMvc.perform(
                            multipart(String.format("%s/save", prefix))
                                    .file(TestUtil.getMockMultiPartFile("/Users/sanghyeonkim/Downloads/R1280x0.png", "image", "rawFile"))
                                    .param("personNo", personNo.toString())
                                    .param("name", name)
                                    .param("role", role)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isBoolean())
                    .andExpect(jsonPath("$").value(true));

        }
    }

    @Nested
    @DisplayName(value = "인물 삭제")
    public class RemovePerson {
        @Test
        @DisplayName(value = "삭제")
        @Transactional
        @Rollback
        void deletePerson() throws Exception {
            mockMvc.perform(
                delete(String.format("%s/%d", prefix, 2241))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isBoolean())
            .andExpect(jsonPath("$").value(true));
        }
    }

    @Nested
    @DisplayName(value = "인물 가져오기")
    public class FetchPerson {
        private Integer page = 1;
        private Integer limit = 10;
        private String searchText = "베네딕트";

        @Test
        @DisplayName(value = "리스트")
        void peopleVanilla() throws Exception {
            mockMvc.perform(
                get(String.format("%s/", prefix))
                .param("page", page.toString())
                .param("limit", limit.toString())
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.size()").value(10));

        }
        @Test
        @DisplayName(value = "검색")
        void peopleSearchText() throws Exception {
            mockMvc.perform(
                    get(String.format("%s/", prefix))
                            .param("page", page.toString())
                            .param("limit", limit.toString())
                            .param("searchText", searchText)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.size()").value(1));
        }

        @Test
        @DisplayName(value = "단일")
        void person() throws Exception {
            mockMvc.perform(
                get(String.format("%s/%d", prefix, 2060))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.personNo").value(2060))
            .andExpect(jsonPath("$.name").value("베네딕트 컴버배치"))
            .andExpect(jsonPath("$.role").value("ACTOR"));
        }
    }
}