package com.yusufrh.saving_book.response;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseResponse<T> {
    public BaseResponse(int errorCode, String errorMessage, Page<T> data){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    private int errorCode; 
    private String errorMessage; 
    private Page<T> data;
}
