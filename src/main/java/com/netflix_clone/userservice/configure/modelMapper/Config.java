package com.netflix_clone.userservice.configure.modelMapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import javax.management.modelmbean.ModelMBean;

@Configurable(value = "modelMapper")
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
