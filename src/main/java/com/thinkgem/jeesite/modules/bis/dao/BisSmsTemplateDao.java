/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bis.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bis.entity.BisSmsTemplate;

/**
 * 短信模板DAO接口
 * @author jun
 * @version 2017-09-18
 */
@MyBatisDao
public interface BisSmsTemplateDao extends CrudDao<BisSmsTemplate> {

}