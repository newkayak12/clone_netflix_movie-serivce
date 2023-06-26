package com.netflix_clone.movieservice.component.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "constants")
public class Constants {

    public static String MOVIE_PATH;
    @Value(value = "${constants.movie_path}")
    public void setFilePath(String _MOVIE_PATH){ MOVIE_PATH = _MOVIE_PATH; }


}