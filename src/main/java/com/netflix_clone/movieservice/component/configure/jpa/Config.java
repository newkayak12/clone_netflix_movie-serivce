package com.netflix_clone.movieservice.component.configure.jpa;

import com.netflix_clone.movieservice.component.configure.ConfigMsg;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

/**
 * Created on 2023-05-19
 * Project user-service
 */
@Configuration(value = "jpa_configuration")
@EnableJpaAuditing
public class Config {
    @PostConstruct
    public void enabled(){
        ConfigMsg.msg("Jpa");
    }
}
