package com.netflix_clone.movieservice.component.configure.queryDsl;

import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * Created on 2023-05-18
 * Project user-service
 */
@Configuration(value = "querydsl_configuration")
@RequiredArgsConstructor
public class Config {
    private final EntityManager entityManager;

    @Bean
    public JPQLQueryFactory jPQLQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }
}
