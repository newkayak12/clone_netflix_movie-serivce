package com.netflix_clone.movieservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsDetailDto;
import com.netflix_clone.movieservice.repository.dto.reference.ContentsInfoDto;
import com.netflix_clone.movieservice.repository.dto.reference.MovieProfileDto;
import com.netflix_clone.movieservice.repository.dto.reference.WatchedDto;
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
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties
@ActiveProfiles(value = {"local"})
class WatchedControllerTest extends AbstractControllerTest {
    private final String prefix = "/api/v1/watched/";

    @Autowired
    private ObjectMapper mapper;

    @Nested
    @DisplayName(value = "콘텐츠 시청")
    public class WatchContents {
        @Test
        @DisplayName(value = "콘텐츠 시청 이력 기록")
        @Transactional
        @Rollback
        void makeWatched() throws Exception {
            WatchedDto watchedDto = new WatchedDto();

            MovieProfileDto profileDto = new MovieProfileDto();
            profileDto.setProfileNo(18L);
            watchedDto.setProfile(profileDto);

            ContentsDetailDto contentsDetailDto = new ContentsDetailDto();
            ContentsInfoDto contentsInfoDto = new ContentsInfoDto();
            contentsInfoDto.setContentsNo(2L);

            contentsDetailDto.setContentsInfo(contentsInfoDto);
            contentsDetailDto.setDetailNo(27L);

            watchedDto.setContentsDetail(contentsDetailDto);
            watchedDto.setLastWatchedDate(LocalDateTime.of(2023,07,01, 00,00));
            watchedDto.setWatchedAt(LocalTime.of(0,10,30));

            mockMvc.perform(
                            post(String.format("%s/", prefix))
                                    .content(mapper.writeValueAsString(watchedDto))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isBoolean())
                    .andExpect(jsonPath("$").value(true));
        }

        @Test
        @DisplayName(value = "시청 지점 업데이트")
        void modifyWatched() throws Exception {
            WatchedDto watchedDto = new WatchedDto();

            watchedDto.setWatchedNo(2L);

            MovieProfileDto profileDto = new MovieProfileDto();
            profileDto.setProfileNo(18L);
            watchedDto.setProfile(profileDto);

            ContentsDetailDto contentsDetailDto = new ContentsDetailDto();
            ContentsInfoDto contentsInfoDto = new ContentsInfoDto();
            contentsInfoDto.setContentsNo(2L);

            contentsDetailDto.setContentsInfo(contentsInfoDto);
            contentsDetailDto.setDetailNo(27L);

            watchedDto.setContentsDetail(contentsDetailDto);
            watchedDto.setLastWatchedDate(LocalDateTime.of(2023,07,01, 00,00));
            watchedDto.setWatchedAt(LocalTime.of(0,20,30));

            mockMvc.perform(
                            post(String.format("%s/", prefix))
                                    .content(mapper.writeValueAsString(watchedDto))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isBoolean())
                    .andExpect(jsonPath("$").value(true));
        }
    }


    @Test
    @DisplayName(value = "시청 기록 확인")
    void watchedContents() throws Exception {

        mockMvc.perform(
            get(String.format("%s/", prefix))
            .param("page", "1")
            .param("limit", "10")
            .param("tableNo", "18")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.size()").value(1));

    }
}