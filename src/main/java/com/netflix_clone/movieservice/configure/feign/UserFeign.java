package com.netflix_clone.movieservice.configure.feign;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@Qualifier(value = "user")
@FeignClient(name = "netflix-clone-user-service")
public interface UserFeign {}
