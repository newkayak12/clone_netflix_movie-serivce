package com.netflix_clone.movieservice.component.configure.objectMapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.netflix_clone.movieservice.component.configure.ConfigMsg;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable(value = "objectMapper_configuration")
public class Config {
    private ObjectMapper objectMapper = new ObjectMapper();

    public Config() {
        ConfigMsg.msg("ModelMapper");
        objectMapper = new ObjectMapper();
    }
    private void deserializeWhenEmptyCase() {
        this.objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        this.objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }
    private void deserializeWhenUnknownCase() {
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    private void deserializeWhenEnumCase() {
        this.objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        this.objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
    }
    private void deserializeSettings() {
        this.deserializeWhenEnumCase();
        this.deserializeWhenEmptyCase();
        this.deserializeWhenUnknownCase();
//        this.deserializeRegisterJavaTimeModule();
    }

    private void serializeSettings() {
        this.objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING,true);
        this.objectMapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL,true);
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
//        this.objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    }


    private void setJavaModule() {
        this.objectMapper.registerModules(new JavaTimeModule(), new Jdk8Module());
    }

    @Bean(name = "object_mapper")
    public ObjectMapper objectMapper () {
        this.deserializeSettings();
        this.serializeSettings();
        this.setJavaModule();
        return this.objectMapper;
    }
}
