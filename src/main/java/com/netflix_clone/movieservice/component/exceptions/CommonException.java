package com.netflix_clone.movieservice.component.exceptions;

public class CommonException extends Exception{
    public CommonException(BecauseOf reason){
        super(reason.getMsg());
    }
}
