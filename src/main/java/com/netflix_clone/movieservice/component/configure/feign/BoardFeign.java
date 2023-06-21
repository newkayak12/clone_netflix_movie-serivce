package com.netflix_clone.movieservice.component.configure.feign;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@Qualifier(value = "board")
@FeignClient(name = "netflix-clone-board-service")
public interface BoardFeign {}
