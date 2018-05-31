/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.bis.entity.BisMovie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(value = "${apiPath}")
public class indexController extends BaseController {

    /**
     * 首页列表
     *
     * @param bisMovie
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {""})
    public String list(BisMovie bisMovie, HttpServletRequest request, HttpServletResponse response, Model model) {
        return "mobile/modules/movie/movieList";
    }

}