/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api.movie;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.responseObject.ResponseObject;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.bis.entity.BisMovie;
import com.thinkgem.jeesite.modules.bis.service.BisMovieService;
import com.thinkgem.jeesite.modules.bis.utils.FilmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 电视电影Controller
 *
 * @author jun
 * @version 2017-09-18
 */
@RestController
@RequestMapping(value = "${apiPath}/movie")
public class MovieController extends BaseController {

    @Autowired
    private BisMovieService bisMovieService;

    /**
     * 七七铺列表
     *
     * @param bisMovie
     * @return
     */
    @RequestMapping(value = {"qqpList"})
    @ResponseBody
    public ResponseObject qqpList(BisMovie bisMovie) {
        Page<Map<String, String>> page = new Page<Map<String, String>>();
        if (StringUtils.isNotEmpty(bisMovie.getName())) {
            page = FilmUtil.findQqpByName(bisMovie.getName(), 1);
        }
        ResponseObject responseObject = buildResObject();
        responseObject.setData(page);
        return responseObject;
    }

    /**
     * 七七铺详情
     *
     * @param bisMovie
     * @return
     */
    @RequestMapping(value = "qqpForm")
    public ResponseObject qqpForm(BisMovie bisMovie) {
        ResponseObject responseObject = buildResObject();
        responseObject.setData(FilmUtil.findQqpViewByUrl(bisMovie.getId()));
        return responseObject;
    }


}