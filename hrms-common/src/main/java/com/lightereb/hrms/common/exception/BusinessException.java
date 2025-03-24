package com.lightereb.hrms.common.exception;

import lombok.Getter;

/**
 * 业务异常类
 * 用于处理业务逻辑相关的异常情况
 */
@Getter
public class BusinessException extends RuntimeException {
    /**
     * 错误码
     * -- GETTER --
     *  获取错误码
     */
    private final Integer code;

    /**
     * 默认构造函数，使用默认错误码(400)
     * @param message 错误信息
     */
    public BusinessException(String message) {
        super(message);
        this.code = 400;
    }

    /**
     * 带错误码的构造函数
     * @param message 错误信息
     * @param code 错误码
     */
    public BusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 带异常的构造函数
     * @param message 错误信息
     * @param e 原始异常
     */
    public BusinessException(String message, Throwable e) {
        super(message, e);
        this.code = 400;
    }

    /**
     * 带错误码和原始异常的构造函数
     * @param message 错误信息
     * @param code 错误码
     * @param e 原始异常
     */
    public BusinessException(String message, Integer code, Throwable e) {
        super(message, e);
        this.code = code;
    }

}
