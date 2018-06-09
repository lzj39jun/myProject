/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bis.entity.BisSmsMobile;
import com.thinkgem.jeesite.modules.sys.entity.Log;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 日志DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

    @Select("select a.days as days,count(a.remote_addr) as num,sum(a.number) as sumNum from (select remote_addr,DATE_FORMAT(create_date,'%Y-%m-%d') days,COUNT(id) as number from sys_log WHERE create_date BETWEEN #{beginDate} AND #{endDate} GROUP  BY remote_addr,days )a GROUP BY a.days ORDER BY a.days desc")
    List<Map> findByNumberList(@Param("beginDate")Date beginDate, @Param("endDate")Date endDate);

}
