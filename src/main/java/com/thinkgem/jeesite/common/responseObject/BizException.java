package com.thinkgem.jeesite.common.responseObject;

public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Object data;
    private Integer code = Integer.valueOf(0);

    public BizException(String message, Exception ex) {
        super(message, ex);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, String data) {
        super(message);
        this.data = data;
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(Integer code, String message, String data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
