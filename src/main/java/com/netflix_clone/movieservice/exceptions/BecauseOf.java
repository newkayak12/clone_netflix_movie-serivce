package com.netflix_clone.movieservice.exceptions;

import lombok.Getter;

@Getter
public enum BecauseOf {
    NO_DATA("데이터가 존재하지 않습니다.");


    private String msg;
    BecauseOf(String msg){
        this.msg = msg;
    }

}
