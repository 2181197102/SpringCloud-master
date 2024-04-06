package com.springboot.cloud.nsclcservice.nsclc.exception;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.common.core.exception.SystemErrorType;
import com.springboot.cloud.common.web.exception.DefaultGlobalExceptionHandlerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerAdvice extends DefaultGlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = {FileSaveErrorException.class})
    public Result fileSaveErrorException(FileSaveErrorException ex) {
        log.error("save file exception:{}", ex.getMessage());
        return Result.fail(SystemErrorType.SAVE_FILE_ERROR);
    }
}