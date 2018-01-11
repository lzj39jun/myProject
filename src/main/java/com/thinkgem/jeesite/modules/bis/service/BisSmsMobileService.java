package com.thinkgem.jeesite.modules.bis.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.JsonVO;
import com.thinkgem.jeesite.common.utils.SmsSubmallUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.bis.entity.BisSmsMobile;
import com.thinkgem.jeesite.modules.bis.dao.BisSmsMobileDao;

/**
 * 手机号区段Service
 * @author jun
 * @version 2018-01-01
 */
@Service
@Transactional(readOnly = true)
public class BisSmsMobileService extends CrudService<BisSmsMobileDao, BisSmsMobile> {

    public BisSmsMobile get(String id) {
        return super.get(id);
    }

    public List<BisSmsMobile> findList(BisSmsMobile bisSmsMobile) {
        return super.findList(bisSmsMobile);
    }

    public Page<BisSmsMobile> findPage(Page<BisSmsMobile> page, BisSmsMobile bisSmsMobile) {
        return super.findPage(page, bisSmsMobile);
    }

    @Transactional(readOnly = false)
    public void save(BisSmsMobile bisSmsMobile) {
        super.save(bisSmsMobile);
    }

    @Transactional(readOnly = false)
    public void delete(BisSmsMobile bisSmsMobile) {
        super.delete(bisSmsMobile);
    }

    public JsonVO sendSms(){
        String city="'武汉','福州','天津','贵阳','青岛','常州'";
        int now=0;
        List<BisSmsMobile> list=dao.findByList(city);
        for(BisSmsMobile bisSmsMobile:list){
            for (int i = 0; i < 100; i++) {
                String mobile = bisSmsMobile.getMobile() + (int) ((Math.random() * 9 + 1) * 1000);
//            String mobile="15158036769";
                SmsSubmallUtils.xsend(mobile);
//                System.out.println(mobile);
                now++;
            }

        }
        return new JsonVO(200,"成功",now);
    }

}
