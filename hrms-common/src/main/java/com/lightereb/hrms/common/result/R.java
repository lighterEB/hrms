package com.lightereb.hrms.common.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true) // 启用链式调用
public class R<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // 状态码
    private Integer code;
    // 消息
    private String message;
    // 数据
    private T data;
    // 附加数据
    private Map<String, Object> extra = new HashMap<>();
    // 是否成功
    private Boolean success;
    // 时间戳
    private long timestamp = System.currentTimeMillis();

    // 私有构造函数，强制使用静态方法创建实例
    private R() {
    }

    // ====== 成功响应方法 ======

    /**
     * 成功响应
     */
    public static <T> R<T> ok() {
        return ok(null);
    }

    /**
     * 成功响应，带数据
     */
    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setCode(200);
        r.setMessage("操作成功");
        r.setData(data);
        r.setSuccess(true);
        return r;
    }

    /**
     * 成功响应，自定义消息
     */
    public static <T> R<T> ok(String message) {
        R<T> r = new R<>();
        r.setCode(200);
        r.setMessage(message);
        r.setSuccess(true);
        return r;
    }

    /**
     * 成功响应，带数据和自定义消息
     */
    public static <T> R<T> ok(T data, String message) {
        R<T> r = new R<>();
        r.setCode(200);
        r.setMessage(message);
        r.setData(data);
        r.setSuccess(true);
        return r;
    }

    /**
     * 创建资源成功 (201)
     */
    public static <T> R<T> created(T data) {
        R<T> r = new R<>();
        r.setCode(201);
        r.setMessage("创建成功");
        r.setData(data);
        r.setSuccess(true);
        return r;
    }

    /**
     * 接受请求但无需返回内容 (204)
     */
    public static <T> R<T> noContent() {
        R<T> r = new R<>();
        r.setCode(204);
        r.setMessage("操作成功");
        r.setSuccess(true);
        return r;
    }

    // ====== 客户端错误响应方法 ======

    /**
     * 错误响应 - 通用错误 (400)
     */
    public static <T> R<T> error() {
        return error("操作失败");
    }

    /**
     * 错误响应 - 带消息的通用错误 (400)
     */
    public static <T> R<T> error(String message) {
        R<T> r = new R<>();
        r.setCode(400);
        r.setMessage(message);
        r.setSuccess(false);
        return r;
    }

    /**
     * 错误响应 - 自定义状态码和消息
     */
    public static <T> R<T> error(int code, String message) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMessage(message);
        r.setSuccess(false);
        return r;
    }

    /**
     * 未授权 (401)
     */
    public static <T> R<T> unauthorized(String message) {
        R<T> r = new R<>();
        r.setCode(401);
        r.setMessage(message == null ? "未授权" : message);
        r.setSuccess(false);
        return r;
    }

    /**
     * 无权限 (403)
     */
    public static <T> R<T> forbidden(String message) {
        R<T> r = new R<>();
        r.setCode(403);
        r.setMessage(message == null ? "无权限" : message);
        r.setSuccess(false);
        return r;
    }

    /**
     * 资源不存在 (404)
     */
    public static <T> R<T> notFound(String message) {
        R<T> r = new R<>();
        r.setCode(404);
        r.setMessage(message == null ? "资源不存在" : message);
        r.setSuccess(false);
        return r;
    }

    /**
     * 资源冲突 (409)
     */
    public static <T> R<T> conflict(String message) {
        R<T> r = new R<>();
        r.setCode(409);
        r.setMessage(message == null ? "资源冲突" : message);
        r.setSuccess(false);
        return r;
    }

    /**
     * 参数校验错误 (422)
     */
    public static <T> R<T> validationError(String message) {
        R<T> r = new R<>();
        r.setCode(422);
        r.setMessage(message == null ? "参数校验错误" : message);
        r.setSuccess(false);
        return r;
    }

    /**
     * 服务器错误 (500)
     */
    public static <T> R<T> serverError(String message) {
        R<T> r = new R<>();
        r.setCode(500);
        r.setMessage(message == null ? "服务器内部错误" : message);
        r.setSuccess(false);
        return r;
    }

    // ====== 链式调用方法 ======

    /**
     * 添加额外数据
     */
    public R<T> addExtra(String key, Object value) {
        this.extra.put(key, value);
        return this;
    }

    /**
     * 添加多个额外数据
     */
    public R<T> addExtras(Map<String, Object> extras) {
        this.extra.putAll(extras);
        return this;
    }

    /**
     * 根据条件返回结果
     */
    public static <T> R<T> condition(boolean condition, T data, String successMsg, String errorMsg) {
        return condition ? ok(data, successMsg) : error(errorMsg);
    }

    /**
     * 判断当前响应是否成功
     */
    public boolean isSuccess() {
        return this.success != null && this.success;
    }
}