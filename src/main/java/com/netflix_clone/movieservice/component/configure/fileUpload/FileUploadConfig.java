package com.netflix_clone.movieservice.component.configure.fileUpload;

import com.netflix_clone.movieservice.component.configure.ConfigMsg;
import com.netflix_clone.movieservice.component.constant.Constants;
import org.newkayak.FileUpload.FileUpload;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration(value = "fileUploadConfig")
@DependsOn(value = "constants")
public class FileUploadConfig {
    public FileUploadConfig() {
        ConfigMsg.msg("FileUpload");
    }

    @Bean
    public FileUpload fileUpload() {
        return new FileUpload(Constants.MOVIE_PATH, false, 1024L * 1024L * 500);
    }

}
