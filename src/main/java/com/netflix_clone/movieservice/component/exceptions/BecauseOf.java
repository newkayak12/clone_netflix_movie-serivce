package com.netflix_clone.movieservice.component.exceptions;

import lombok.Getter;

@Getter
public enum BecauseOf {
    NO_DATA("데이터가 존재하지 않습니다."),
    CANNOT_DELETE("삭제할 수 없습니다."),
    SAVE_FAILURE("저장에 실패했습니다."),
    UNKNOWN_ERROR("알 수 없는 에러가 발생했습니다.");

    private String msg;
    BecauseOf(String msg){
        this.msg = msg;
    }

}
