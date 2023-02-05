package com.example.demo.exception;

import com.example.demo.common.BaseResponse;
import com.example.demo.common.ErrorCode;
import com.example.demo.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptopmHandler(BusinessException e){
        log.error("bussinessExceptipn"+e.getMessage()+"---"+e.getDescription(),e);
        return ResultUtils.error(e.getCode(),e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptopmHandler(BusinessException e){
        log.error("runtimeExceptipn",e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage());
    }

}
