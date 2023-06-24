package com.netflix_clone.movieservice.controller;

import com.netflix_clone.movieservice.util.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties
@ActiveProfiles(value = {"local"})

class ContentsControllerTest extends AbstractControllerTest {

    @Test
    void saveContentInfo() {
    }

    @Test
    void saveContentDetail() {
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
}