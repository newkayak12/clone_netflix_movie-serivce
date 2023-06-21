package com.netflix_clone.movieservice.component.configure;

import java.time.LocalDateTime;

public abstract class ConfigMsg {


    public static void msg(String config) {
        String RESET = "\u001B[0m";
        String FONT_GREEN = "\u001B[32m";
        System.out.println(String.format(" %s ::::::::::::: %s ::::: %sON%s", LocalDateTime.now(), config, FONT_GREEN, RESET));
    }
}
