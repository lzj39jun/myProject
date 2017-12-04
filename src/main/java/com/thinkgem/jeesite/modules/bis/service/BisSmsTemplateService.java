/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bis.service;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CommonUtils;
import com.thinkgem.jeesite.common.utils.HttpUtil;
import com.thinkgem.jeesite.common.utils.JsonVO;
import com.thinkgem.jeesite.modules.bis.dao.BisSmsTemplateDao;
import com.thinkgem.jeesite.modules.bis.entity.BisSmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信模板Service
 *
 * @author jun
 * @version 2017-09-18
 */
@Service
@Transactional(readOnly = true)
public class BisSmsTemplateService extends CrudService<BisSmsTemplateDao, BisSmsTemplate> {

    public BisSmsTemplate get(String id) {
        return super.get(id);
    }

    public List<BisSmsTemplate> findList(BisSmsTemplate bisSmsTemplate) {
        return super.findList(bisSmsTemplate);
    }

    public Page<BisSmsTemplate> findPage(Page<BisSmsTemplate> page, BisSmsTemplate bisSmsTemplate) {
        return super.findPage(page, bisSmsTemplate);
    }

    @Transactional(readOnly = false)
    public void save(BisSmsTemplate bisSmsTemplate) {
        super.save(bisSmsTemplate);
    }

    @Transactional(readOnly = false)
    public void delete(BisSmsTemplate bisSmsTemplate) {
        super.delete(bisSmsTemplate);
    }

    /**
     * 方法说明：发送短信
     * 创建时间：2017/9/19 下午2:02
     * 作者：jun
     * 变更原因（若有变更）：
     *
     * @param mobile 手机号
     * @return
     */
    public JsonVO sendSms(String mobile, String id) {
        BisSmsTemplate query = new BisSmsTemplate();
        query.setId(id);
        List<BisSmsTemplate> bisSmsTemplatesList = super.findList(query);
        logger.info("应发送短信总条数：" + bisSmsTemplatesList.size());
        int errorNum=0;
        for (BisSmsTemplate bisSmsTemplate : bisSmsTemplatesList) {
            try {
                String url = null;
                String body = null;

                Map<String, Object> paramMap = new HashMap<String, Object>();
                String params = bisSmsTemplate.getParams().replace("[mobile]", mobile);//替换手机号
                    params = params.replace("[token_id]",CommonUtils.getUniqueId());//替换token
                String paramsStr[] = params.split("&amp;");
                for (String par : paramsStr) {
                    String pars[] = par.split("=");
                    if (pars.length > 1) {
                        paramMap.put(pars[0], pars[1]);
                    }
                }

                Map<String, String> headerParamMap = new HashMap<String, String>();
                headerParamMap.put("Content-Type", bisSmsTemplate.getContentType());

                url = bisSmsTemplate.getUrl();
                if (bisSmsTemplate.getContentType().equals("application/json")) {
                    body = bisSmsTemplate.getParams();
                }

                JSONObject jsonObject = null;
                if (bisSmsTemplate.getMethod().equals("GET")) {
                    jsonObject = HttpUtil.get(url, paramMap, headerParamMap);
                } else if (bisSmsTemplate.getMethod().equals("POST")) {
                    jsonObject = HttpUtil.postMethodUrl(url, body, paramMap, headerParamMap);
                }
                logger.info("【返回结果】短信模板ID：" + jsonObject.toJSONString());
            } catch (Exception e) {
                logger.error("【错误】短信模板ID：" + bisSmsTemplate.getId(), e);
                errorNum=errorNum+1;
            }
        }
        logger.info("发送短信错误总条数：" + errorNum);
        return new JsonVO(bisSmsTemplatesList.size()-errorNum);
    }

}