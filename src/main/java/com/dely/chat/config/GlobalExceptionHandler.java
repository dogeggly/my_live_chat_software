package com.dely.chat.config;

import com.dely.chat.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result<String> handleException(Exception e) {
        log.error("服务器异常:", e);
        return Result.fail(e.getMessage());
    }

}
