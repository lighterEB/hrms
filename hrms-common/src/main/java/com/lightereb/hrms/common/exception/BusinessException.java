package com.lightereb.hrms.common.exception;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private Integer code;
    private final String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
