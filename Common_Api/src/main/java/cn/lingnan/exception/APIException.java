package cn.lingnan.exception;

public class APIException extends RuntimeException {
    private final int code;
    private final String msg;

    public APIException() {
        this(500, "接口错误");
    }

    public APIException(String msg) {
        this(500, msg);
    }

    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
}
