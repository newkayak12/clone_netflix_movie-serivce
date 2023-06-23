package com.netflix_clone.movieservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.movieservice.component.exceptions.BecauseOf;
import com.netflix_clone.movieservice.repository.dto.reference.CategoryDto;
import com.netflix_clone.movieservice.repository.dto.request.SaveCategory;
import com.netflix_clone.movieservice.util.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties
@ActiveProfiles(value = {"local"})
class CategoryControllerTest extends AbstractControllerTest {
    private final String prefix = "/api/v1/category";
    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName(value = "카테고리 가져오기")
    class Categories {
        @Test
        @DisplayName(value = "루트 노드 가져오기")
        public void rootNode() throws Exception {
            mockMvc.perform(
                get(String.format("%s/", prefix))
                .param("page", "1")
                .param("limit", "10")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.size()").value(10));
        }

        @ParameterizedTest
        @ValueSource(ints = {1})
        public void childNode(int rootNo) throws Exception {
            mockMvc.perform(
                get(String.format("%s/", prefix))
                        .param("page", "1")
                        .param("limit", "10")
                        .param("categoryNo", rootNo+"")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(10));

        }
    }

    @ParameterizedTest
    @DisplayName(value = "카테고리 등록")
    @Transactional
    @Rollback
    @CsvFileSource(resources = {"/csv/parentNodeCategory.csv", "/csv/childNodeCategory.csv"}, numLinesToSkip = 1)
    void save(Boolean isLeaf, String name, Long parentNo) throws Exception {
        SaveCategory request = new SaveCategory();
        request.setIsLeaf(isLeaf);
        request.setName(name);
        if(!parentNo.equals(0)) request.setParentCategory(new CategoryDto(parentNo));


        mockMvc.perform(
            post(String.format("%s/", prefix))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isBoolean())
        .andExpect(jsonPath("$").value(true));
    }

    @DisplayName(value = "삭제")
    @Nested
    class RemoveCategories {
        @Test
        @DisplayName(value = "삭제 실패")
        void failure() throws Exception {
            mockMvc.perform(
                        delete(String.format("%s/%d", prefix, 1))
                )
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$").value(BecauseOf.CANNOT_DELETE.getMsg()));
        }

        @Test
        @DisplayName(value = "삭제 성공")
        void success() throws Exception {
            mockMvc.perform(
                        delete(String.format("%s/%d", prefix, 12))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isBoolean())
                .andExpect(jsonPath("$").value(true));
        }
    }
}