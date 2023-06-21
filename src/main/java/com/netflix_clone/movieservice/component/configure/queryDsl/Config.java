package com.netflix_clone.movieservice.component.configure.queryDsl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.movieservice.component.configure.ConfigMsg;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created on 2023-05-18
 * Project user-service
 */
@Configuration(value = "querydsl_configuration")
public class Config {

    @PersistenceContext
    private  EntityManager entityManager;

    public Config() {
        ConfigMsg.msg("QueryDsl");
    }
    @Bean
    public JPQLQueryFactory jPQLQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }
}
