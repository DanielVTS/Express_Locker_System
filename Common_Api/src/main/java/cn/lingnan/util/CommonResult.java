package cn.lingnan.util;

import cn.lingnan.exception.APIException;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class CommonResult<T> {
    private long code;
    private String message;
    private T data;


    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult<>(cn.lingnan.util.ResultCode.SUCCESS.getCode(), cn.lingnan.util.ResultCode.SUCCESS.getMessage(), null);
    }


    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(cn.lingnan.util.ResultCode.SUCCESS.getCode(), cn.lingnan.util.ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<>(cn.lingnan.util.ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode,String message) {
        return new CommonResult<>(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<>(cn.lingnan.util.ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(cn.lingnan.util.ResultCode.FAILED);
    }

    /**
     *手机号重复
     */
    public static <T> CommonResult<T> phone_failed(){
        return failed(cn.lingnan.util.ResultCode.PHONE_EXIST);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(cn.lingnan.util.ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<>(cn.lingnan.util.ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<>(cn.lingnan.util.ResultCode.UNAUTHORIZED.getCode(), cn.lingnan.util.ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<>(cn.lingnan.util.ResultCode.FORBIDDEN.getCode(), cn.lingnan.util.ResultCode.FORBIDDEN.getMessage(), data);
    }

    /**
     * 失败返回结果
     * @param exception 异常
     */
    @ExceptionHandler(APIException.class)
    public static <T> CommonResult<T> failed(APIException exception) {
        return new CommonResult<>(exception.getCode(), exception.getMessage(), null);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

