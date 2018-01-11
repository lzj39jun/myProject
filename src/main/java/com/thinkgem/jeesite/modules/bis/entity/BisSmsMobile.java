
package com.thinkgem.jeesite.modules.bis.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 手机号区段Entity
 * @author jun
 * @version 2018-01-01
 */
public class BisSmsMobile extends DataEntity<BisSmsMobile> {

    private static final long serialVersionUID = 1L;
    private String mobile;        // 手机号
    private String province;        // 省
    private String city;        // 市
    private String type;        // 运营商
    private String phone;        // 电话
    private String num;        // 数量

    public BisSmsMobile() {
        super();
    }

    public BisSmsMobile(String id) {
        super(id);
    }

    @Length(min = 0, max = 50, message = "手机号长度必须介于 0 和 50 之间")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Length(min = 0, max = 50, message = "省长度必须介于 0 和 50 之间")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Length(min = 0, max = 50, message = "市长度必须介于 0 和 50 之间")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Length(min = 0, max = 10, message = "运营商长度必须介于 0 和 10 之间")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(min = 0, max = 10, message = "电话长度必须介于 0 和 10 之间")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min = 0, max = 20, message = "数量长度必须介于 0 和 20 之间")
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}