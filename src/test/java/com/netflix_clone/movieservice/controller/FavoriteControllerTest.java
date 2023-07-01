package com.netflix_clone.movieservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.reference.FavoriteDto;
import com.netflix_clone.movieservice.repository.dto.reference.MovieProfileDto;
import com.netflix_clone.movieservice.util.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties
@ActiveProfiles(value = {"local"})
class FavoriteControllerTest extends AbstractControllerTest {
    private final String prefix = "/api/v1/favorite";
    @Autowired
    private ObjectMapper mapper;

    @Nested
    @DisplayName(value = "가져오기")
    public class FetchFavorite {
        @Test
        @DisplayName(value = "좋아요 리스트")
        void favorites() throws Exception {
            String page = "1";
            String limit = "10";
            String tableNo = "18";

            mockMvc.perform(
                get(String.format("%s/", prefix))
                .param("page", page)
                .param("limit", limit)
                .param("tableNo", tableNo)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.size()").value(1));
        }
    }

    @Nested
    @DisplayName(value = "좋아요")
    public class FavoriteStatus {

        @Test
        @DisplayName(value = "좋아요 ")
        @Transactional
        @Rollback
        void setFavoriteStatus() throws Exception {
            String body = mapper.writeValueAsString(
                    Map.of(
                            "contentsInfo", Map.of("contentsNo", 2),
                            "profile", Map.of("profileNo", 18),
                            "favoriteDate", LocalDateTime.of(2023, 01, 01, 00, 00)
                    )
            );


            mockMvc.perform(
                post(String.format("%s/",prefix))
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isBoolean())
            .andExpect(jsonPath("$").value(true));


        }

        @Test
        @DisplayName(value = "좋아요 해제")
        @Transactional
        @Rollback
        void removeFavoriteStatus() throws Exception {
            mockMvc.perform(
                delete(String.format("%s/%d", prefix, 15))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isBoolean())
            .andExpect(jsonPath("$").value(true));
        }
    }

}