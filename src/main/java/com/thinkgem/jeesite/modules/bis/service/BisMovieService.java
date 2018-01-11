/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.HttpUtil;
import com.thinkgem.jeesite.modules.bis.dao.BisSmsTemplateDao;
import com.thinkgem.jeesite.modules.bis.entity.BisMovie;
import com.thinkgem.jeesite.modules.bis.entity.BisSmsTemplate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 电视电影资源Service
 *
 * @author jun
 * @version 2017-09-18
 */
@Service
@Transactional(readOnly = true)
public class BisMovieService extends CrudService<BisSmsTemplateDao, BisSmsTemplate> {

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
     * 方法说明：80s获取视频详情
     * 创建时间：2017/9/19 下午2:02
     * 作者：jun
     * 变更原因（若有变更）：
     *
     * @param id 名称
     * @return
     */
    public BisMovie getBisMovieView(String id) {
        BisMovie movie = new BisMovie();

        String url = "http://www.80s.tw/" + id;
        Map<String, Object> map = new HashMap<String, Object>();
        String str = HttpUtil.getRetrunStr(url, map, null);
        Document doc = Jsoup.parse(str);
        Element minfo = doc.getElementById("minfo");

        movie.setId(id);
        movie.setImg("http:" + minfo.getElementsByTag("img").get(0).attr("src"));
        movie.setName(minfo.getElementsByClass("font14w").get(0).text());
        movie.setAlias(minfo.getElementsByTag("span").get(2).html().replaceAll("<span class=\"font_888\">又名：</span> ", ""));

        Elements elements = minfo.getElementsByTag("span").get(4).getElementsByTag("a");
        String performer = "";
        for (Element element : elements) {
            performer += element.html() + " ";
        }
        movie.setPerformer(performer);
        Elements span_block = minfo.getElementsByClass("clearfix").get(0).getElementsByClass("span_block");
        movie.setType(span_block.get(0).text());
        movie.setRegion(span_block.get(1).text());
        movie.setLanguage(span_block.get(2).text());
        movie.setDirector(span_block.get(3).text());
        movie.setReleaseDate(span_block.get(4).text());
        movie.setBeanScore(minfo.getElementsByClass("info").get(0).getElementsByClass("clearfix").get(1).text());
        movie.setPlotIntroduction(minfo.getElementById("movie_content").text());

        Element cpdl2list = doc.getElementById("cpdl2list");
        Elements backcolor1s = cpdl2list.getElementsByClass("xunlei dlbutton1");
        List<Map> mapList = new ArrayList<Map>();
        for (Element backcolor1 : backcolor1s) {
            Map mapT = new HashMap();
            mapT.put("url", backcolor1.getElementsByTag("a").get(0).attr("href"));
            mapT.put("name", "高清");
            String name = backcolor1.getElementsByTag("a").get(0).attr("thunderrestitle");
            if (name != null) {
                mapT.put("name", name);
            }
            mapList.add(mapT);
        }
        movie.setThunderList(mapList);
        return movie;
    }

    /**
     * 方法说明：80s 搜索视频
     * 创建时间：2017/9/19 下午2:02
     * 作者：jun
     * 变更原因（若有变更）：
     *
     * @param name 名称
     * @return
     */
    public List<BisMovie> getBisMovieList(String name) {
        String url = "https://www.80s.tw/search";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", name);
        Map<String, String> headMap = new HashMap<String, String>();
        headMap.put("Content-Type", "application/x-www-form-urlencoded");
        String str = HttpUtil.postReturnStr(url, null, map, headMap, null);
        Document doc = Jsoup.parse(str);
        Element block3 = doc.getElementById("block3");
        Element ul = block3.getElementsByClass("clearfix search_list").get(0);
        Elements lis = ul.getElementsByTag("li");
        List<BisMovie> movieList = new ArrayList<BisMovie>();
        for (Element element : lis) {
            BisMovie movie = new BisMovie();
            movie.setId(element.getElementsByTag("a").get(0).attr("href"));
            movie.setName(element.getElementsByTag("a").get(0).text());
            movieList.add(movie);
        }
        return movieList;
    }

    public static String object2Json(Object object) {
        if (object == null) {
            return "";
        } else {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                return objectMapper.writeValueAsString(object);
            } catch (Exception var3) {
//                log.error(var3.getMessage(), var3);
                return "";
            }
        }
    }

    public static void main(String[] args) {

    }
}