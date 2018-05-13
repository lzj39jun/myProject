/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bis.web;

import com.thinkgem.jeesite.common.utils.OSSClientUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 照片Controller
 *
 * @author jun
 * @version 2017-09-18
 */
@Controller
@RequestMapping(value = "${frontPath}/bis/bisPhoto")
public class BisPhotoController extends BaseController {
    /**
     * 照片列表
     *
     * @return
     */
    @RequestMapping(value = {"list"})
    @ResponseBody
    public List list() {
        return OSSClientUtils.listObjects();
    }

    @RequestMapping(value = {"photo"})
    public String index() {
    return "modules/bis/bisPhoto";
    }

}