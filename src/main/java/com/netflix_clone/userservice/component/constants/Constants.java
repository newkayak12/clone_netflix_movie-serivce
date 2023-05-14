package com.netflix_clone.userservice.component.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "constants")
public class Constants {
    public static String FILE_PATH;

    @Value("${constant.file-path}")
    public static void setFilePath( String _FILE_PATH) { FILE_PATH = _FILE_PATH;}


}
