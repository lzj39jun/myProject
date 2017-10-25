/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bis.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 短信模板Entity
 * @author jun
 * @version 2017-09-18
 */
public class BisSmsTemplate extends DataEntity<BisSmsTemplate> {

    private static final long serialVersionUID = 1L;
    private String name;		// 名称
    private String method;		// 请求方法
    private String url;		// 请求地址
    private String contentType;		// 请求类型
    private String params;		// 请求参数
    private String type;		// 类别

    public BisSmsTemplate() {
        super();
    }

    public BisSmsTemplate(String id){
        super(id);
    }

    @Length(min=1, max=50, message="名称长度必须介于 1 和 50 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min=1, max=20, message="请求方法长度必须介于 1 和 20 之间")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Length(min=1, max=225, message="请求地址长度必须介于 1 和 225 之间")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Length(min=1, max=225, message="请求类型长度必须介于 1 和 225 之间")
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Length(min=1, max=1000, message="请求参数长度必须介于 1 和 1000 之间")
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Length(min=1, max=20, message="类别长度必须介于 1 和 20 之间")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}