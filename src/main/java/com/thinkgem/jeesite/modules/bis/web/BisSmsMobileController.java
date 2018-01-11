package com.thinkgem.jeesite.modules.bis.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.JsonVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.bis.entity.BisSmsMobile;
import com.thinkgem.jeesite.modules.bis.service.BisSmsMobileService;

/**
 * 手机号区段Controller
 * @author jun
 * @version 2018-01-01
 */
@Controller
@RequestMapping(value = "${adminPath}/bis/bisSmsMobile")
public class BisSmsMobileController extends BaseController {

    @Autowired
    private BisSmsMobileService bisSmsMobileService;

    @ModelAttribute
    public BisSmsMobile get(@RequestParam(required=false) String id) {
        BisSmsMobile entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = bisSmsMobileService.get(id);
        }
        if (entity == null){
            entity = new BisSmsMobile();
        }
        return entity;
    }

    @RequiresPermissions("bis:bisSmsMobile:view")
    @RequestMapping(value = {"list", ""})
    public String list(BisSmsMobile bisSmsMobile, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<BisSmsMobile> page = bisSmsMobileService.findPage(new Page<BisSmsMobile>(request, response), bisSmsMobile);
        model.addAttribute("page", page);
        return "modules/bis/bisSmsMobileList";
    }

    @RequiresPermissions("bis:bisSmsMobile:view")
    @RequestMapping(value = "form")
    public String form(BisSmsMobile bisSmsMobile, Model model) {
        model.addAttribute("bisSmsMobile", bisSmsMobile);
        return "modules/bis/bisSmsMobileForm";
    }

    @RequiresPermissions("bis:bisSmsMobile:edit")
    @RequestMapping(value = "save")
    public String save(BisSmsMobile bisSmsMobile, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, bisSmsMobile)){
            return form(bisSmsMobile, model);
        }
        bisSmsMobileService.save(bisSmsMobile);
        addMessage(redirectAttributes, "保存手机号区段成功");
        return "redirect:"+Global.getAdminPath()+"/bis/bisSmsMobile/?repage";
    }

    @RequiresPermissions("bis:bisSmsMobile:edit")
    @RequestMapping(value = "delete")
    public String delete(BisSmsMobile bisSmsMobile, RedirectAttributes redirectAttributes) {
        bisSmsMobileService.delete(bisSmsMobile);
        addMessage(redirectAttributes, "删除手机号区段成功");
        return "redirect:"+Global.getAdminPath()+"/bis/bisSmsMobile/?repage";
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
    @ResponseBody
    public JsonVO sendSms(){
        return bisSmsMobileService.sendSms();
    }

}