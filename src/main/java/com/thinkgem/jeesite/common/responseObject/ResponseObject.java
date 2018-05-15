package com.thinkgem.jeesite.common.responseObject;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResponseObject implements Serializable {
    //"返回状态,(false 失败;true 成功)
    private boolean success = true;
    //"消息级别,(info 正常;war 告警;error 错误)
    private String level = "info";
    //"消息类型,(normal 正常;)
    private String type = "normal";
    //"业务消息类型,(normal 正常;)
    private String bizType = "normal";
    //"系统编码,(详见ResponseCode枚举)
    private String code = "00";
    //"消息内容"
    private String message;
    //业务数据"
    private Object data;

    public ResponseObject() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public Map<String, Object> put(String key, Object value) {
        if (this.data == null || !(this.data instanceof Map)) {
            this.data = new HashMap();
        }

        Map<String, Object> map = (Map) this.data;
        map.put(key, value);
        return map;
    }
}