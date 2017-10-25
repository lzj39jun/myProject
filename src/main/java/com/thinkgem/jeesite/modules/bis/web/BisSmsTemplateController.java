/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bis.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.JsonVO;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.bis.entity.BisSmsTemplate;
import com.thinkgem.jeesite.modules.bis.service.BisSmsTemplateService;

/**
 * 短信模板Controller
 * @author jun
 * @version 2017-09-18
 */
@Controller
@RequestMapping(value = "${adminPath}/bis/bisSmsTemplate")
public class BisSmsTemplateController extends BaseController {

    @Autowired
    private BisSmsTemplateService bisSmsTemplateService;

    @ModelAttribute
    public BisSmsTemplate get(@RequestParam(required=false) String id) {
        BisSmsTemplate entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = bisSmsTemplateService.get(id);
        }
        if (entity == null){
            entity = new BisSmsTemplate();
        }
        return entity;
    }

    @RequiresPermissions("bis:bisSmsTemplate:view")
    @RequestMapping(value = {"list", ""})
    public String list(BisSmsTemplate bisSmsTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<BisSmsTemplate> page = bisSmsTemplateService.findPage(new Page<BisSmsTemplate>(request, response), bisSmsTemplate);
        model.addAttribute("page", page);
        return "modules/bis/bisSmsTemplateList";
    }

    @RequiresPermissions("bis:bisSmsTemplate:view")
    @RequestMapping(value = "form")
    public String form(BisSmsTemplate bisSmsTemplate, Model model) {
        model.addAttribute("bisSmsTemplate", bisSmsTemplate);
        return "modules/bis/bisSmsTemplateForm";
    }

    @RequiresPermissions("bis:bisSmsTemplate:edit")
    @RequestMapping(value = "save")
    public String save(BisSmsTemplate bisSmsTemplate, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, bisSmsTemplate)){
            return form(bisSmsTemplate, model);
        }
        bisSmsTemplateService.save(bisSmsTemplate);
        addMessage(redirectAttributes, "保存短信模板成功");
        return "redirect:"+Global.getAdminPath()+"/bis/bisSmsTemplate/?repage";
    }

    @RequiresPermissions("bis:bisSmsTemplate:edit")
    @RequestMapping(value = "delete")
    public String delete(BisSmsTemplate bisSmsTemplate, RedirectAttributes redirectAttributes) {
        bisSmsTemplateService.delete(bisSmsTemplate);
        addMessage(redirectAttributes, "删除短信模板成功");
        return "redirect:"+Global.getAdminPath()+"/bis/bisSmsTemplate/?repage";
    }

    /**
      *方法说明：发送短信
      *创建时间：2017/9/25 下午6:30
      *作者：jun
      *变更原因（若有变更）：
      * @param mobile
      * @return
      */
    @RequestMapping(value = "sendSms")
    public JsonVO sendSms(@RequestParam String mobile,String id){
        return bisSmsTemplateService.sendSms(mobile,id);
    }

}