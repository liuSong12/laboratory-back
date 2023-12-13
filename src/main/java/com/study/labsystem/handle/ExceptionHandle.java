package com.study.labsystem.handle;


import com.study.labsystem.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class ExceptionHandle {

    @ExceptionHandler
    public Result<String> addUserExceptionHandle(Exception e){
        return Result.err(e.getMessage());
    }
}
