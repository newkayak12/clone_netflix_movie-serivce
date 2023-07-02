package com.netflix_clone.movieservice.component.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "constants")
public class Constants {

    public static String MOVIE_PATH;
//    public static String REDIS_HOST;
//    public static Integer REDIS_PORT;

    @Value(value = "${constants.movie_path}")
    public void setFilePath(String _MOVIE_PATH){ MOVIE_PATH = _MOVIE_PATH; }

//    @Value(value = "${spring.data.redis.host}")
//    public void setRedisHost( String _REDIS_HOST ) { REDIS_HOST = _REDIS_HOST; }
//    @Value(value = "${spring.data.redis.port}")
//    public void setRedisPort( Integer _REDIS_PORT ) { REDIS_PORT = _REDIS_PORT; }

}