package com.netflix_clone.movieservice.component.configure.eureka;

import com.netflix_clone.movieservice.component.configure.ConfigMsg;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2023-05-12
 * Project board-service
 */
@Configuration (value = "eureka_configuration")
@EnableEurekaClient
public class Config {
    public Config() {
        ConfigMsg.msg("Eureka");
    }
}
