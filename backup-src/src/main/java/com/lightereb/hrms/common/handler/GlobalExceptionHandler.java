package com.lightereb.hrms.common.handler;

import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.common.result.R;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<String> handlerValidationException(MethodArgumentNotValidException e) {
        // 提取所有错误信息
        String errorMsg = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("%s %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining("；"));
        return R.error(HttpStatus.BAD_REQUEST.value(),errorMsg);
    }

    @ExceptionHandler(BindException.class)
    public R<String> handleBindException(BindException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        return R.error(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    @ExceptionHandler(BusinessException.class)
    public R<String> handleBusinessException(BusinessException ex) {
        return R.error(ex.getCode(),ex.getMessage());
    }
}
