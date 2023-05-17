package com.netflix_clone.userservice.configure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created on 2023-05-12
 * Project user-service
 */
@FeignClient(value = "netflix-clone-board-service")
public interface BoardFeign {

    @GetMapping("/api/v1/board/faqs/")
    public String getFaq();
}
