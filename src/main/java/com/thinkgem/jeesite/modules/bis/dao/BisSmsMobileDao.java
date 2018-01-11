package com.thinkgem.jeesite.modules.bis.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bis.entity.BisSmsMobile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 手机号区段DAO接口
 * @author jun
 * @version 2018-01-01
 */
@MyBatisDao
public interface BisSmsMobileDao extends CrudDao<BisSmsMobile> {

    @Select("select mobile from bis_sms_mobile where city in (${city}) ORDER BY RAND() LIMIt 100")
    List<BisSmsMobile> findByList(@Param("city")String city);

}