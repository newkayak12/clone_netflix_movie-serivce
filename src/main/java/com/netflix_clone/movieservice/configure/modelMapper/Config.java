package com.netflix_clone.movieservice.configure.modelMapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "modelMapper_configuration")
public class Config {
    private ModelMapper modelMapper = new ModelMapper();

    private void strictStrategy() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Bean
    public ModelMapper modelMapper() {
        this.strictStrategy();
        return modelMapper;
    }
}
