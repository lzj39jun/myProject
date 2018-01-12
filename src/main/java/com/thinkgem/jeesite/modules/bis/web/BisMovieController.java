/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bis.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.bis.entity.BisMovie;
import com.thinkgem.jeesite.modules.bis.service.BisMovieService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 电视电影Controller
 *
 * @author jun
 * @version 2017-09-18
 */
@Controller
@RequestMapping(value = "${adminPath}/bis/bisMovie")
public class BisMovieController extends BaseController {

    @Autowired
    private BisMovieService bisMovieService;


    @RequestMapping(value = {"list", ""})
    public String list(BisMovie bisMovie, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<BisMovie> page = new Page<BisMovie>();
        if(StringUtils.isNotEmpty(bisMovie.getName())) {
            List<BisMovie> movies= bisMovieService.getBisMovieList(bisMovie.getName());
            page.setList(movies);
            page.setCount(Long.valueOf(movies.size()));
        }
        model.addAttribute("page", page);
        return "modules/bis/bisMovieList";
    }


    @RequestMapping(value = "form")
    public String form(BisMovie bisMovie, Model model) {
        bisMovie=bisMovieService.getBisMovieView(bisMovie.getId());
        model.addAttribute("bisMovie", bisMovie);
        return "modules/bis/bisMovieForm";
    }

}