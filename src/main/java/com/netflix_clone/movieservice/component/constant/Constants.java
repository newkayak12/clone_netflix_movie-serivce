package com.netflix_clone.movieservice.component.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "constants")
public class Constants {

    public static String FILE_PATH;

    @Value(value = "${constant.file-path}")
    public static void setFilePath(String _FILE_PATH){ FILE_PATH = _FILE_PATH; }


}