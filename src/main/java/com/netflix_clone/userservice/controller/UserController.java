package com.netflix_clone.userservice.controller;

import com.netflix_clone.userservice.configure.feign.BoardFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2023-05-12
 * Project user-service
 */
@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final BoardFeign boardFeign;

    @GetMapping(value = "/")
    public String test() {
        String result = "";
        try {
            result = boardFeign.getFaq();
        } catch ( Exception e ){
            e.printStackTrace();
        }
        return "HELLO"+ result;
    }
}
