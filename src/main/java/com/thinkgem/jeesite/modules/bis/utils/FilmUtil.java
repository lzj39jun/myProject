package com.thinkgem.jeesite.modules.bis.utils;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.HttpUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 电影
 *
 * @Description:TODO
 * @author:jun
 * @time:2017年8月2日 下午3:06:24
 */
public class FilmUtil {
    private static final Log LOGGER = LogFactory.getLog(FilmUtil.class);

    /**
     * 高清电影网
     * 根据名称搜索电影
     *
     * @param name
     * @return List<String>
     * @Description
     * @author jun
     * @time:2017年8月2日 下午3:08:00
     */
    public static List<Map<String, String>> findByName(String name) {
        name = URLEncoder.encode(name);
        String str = HttpUtil.getRetrunStr("http://www.999hdhd.com/htm/search_topic_" + name + "_1.htm", null, null);
        return analysisFindByName(str);
    }

    /**
     * 高清电影网
     * 电影列表解析
     *
     * @param str
     * @return List<Map<String,String>>
     * @Description
     * @author jun
     * @time:2017年8月3日 上午10:46:33
     */
    public static List<Map<String, String>> analysisFindByName(String str) {
        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
        try {
            Document doc = Jsoup.parse(str);
            Elements uls = doc.getElementsByTag("ul");
            Element ul = uls.get(1);
            Elements lis = ul.getElementsByTag("li");
            for (Element li : lis) {
                Elements a = li.getElementsByTag("a");
                Elements img = li.getElementsByTag("img");
                Elements span = li.getElementsByTag("span");
                Map<String, String> map = Maps.newHashMap();
                map.put("a", a.get(0).attr("href"));
                map.put("img", img.get(0).attr("src"));
                map.put("name", span.get(0).text());
                map.put("year", a.get(1).text());
                map.put("region", a.get(2).text());
                map.put("performer", span.get(3).text());
                maps.add(map);
            }
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return maps;
    }

    /**
     * 高清电影网
     * 获取百度网盘链接
     *
     * @param a
     * @return String
     * @Description
     * @author jun
     * @time:2017年8月3日 下午3:28:47
     */
    public static String findByid(String a) {
        String thunder = "";
        try {
            String str = HttpUtil.getRetrunStr("http://www.999hdhd.com" + a, null, null);
            Document doc = Jsoup.parse(str);
            System.out.println(doc);
            Elements anchor = doc.getElementsByTag("li");
            int i = anchor.size();
            Element li = anchor.get(i - 1);
            thunder = li.html();
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return thunder;
    }

    /**
     * 七七铺
     * 根据名称搜索电影
     *
     * @param name
     * @return List<String>
     * @Description
     * @author jun
     * @time:2017年8月2日 下午3:08:00
     */
    public static Page<Map<String, String>> findQqpByName(String name, Integer page) {
        Page<Map<String, String>> pageList = new Page<Map<String, String>>();
        pageList.setPageNo(page);
        try {
            LOGGER.info("---------------搜索电影：" + name);
            name = URLEncoder.encode(name, "gbk");

            Document doc = Jsoup.connect("http://www.qiqipu.com/search.asp?page=" + page + "&searchword=" + name + "&searchtype=-1")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                    .timeout(10000)
                    .get();
            //获取分页
//			 LOGGER.info("---------------doc："+doc);
            Elements strong = doc.getElementsByTag("strong");
            int totalRow = Integer.valueOf((strong.get(1).getElementsByTag("span").get(1).text()));
//            int totalPage = totalRow / 16;
//            if (totalRow % 16 > 0) {
//                totalPage = totalPage + 1;
//            }
            pageList.setCount(totalRow);
            pageList.setPageSize(16);
            Elements lis = doc.getElementsByClass("box channel").get(0).getElementsByTag("ul").get(0).getElementsByTag("li");
            List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
            for (Element li : lis) {
                Elements a = li.getElementsByTag("a");
                Elements img = li.getElementsByTag("img");
                Elements span = li.getElementsByTag("span");
                Elements em = li.getElementsByTag("em");
                Elements p = li.getElementsByTag("p");
                Map<String, String> map = Maps.newHashMap();
                map.put("a", a.get(0).attr("href"));
                map.put("img", img.get(0).attr("src"));
                map.put("name", a.get(1).text());
                map.put("year", em.get(0).text());
                map.put("region", span.get(1).text());
                map.put("performer", p.get(0).text());
                maps.add(map);
            }
            pageList.setList(maps);
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return pageList;
    }

    /**
     * 七七铺
     * 资源详情
     *
     * @param url
     * @return List<String>
     * @Description
     * @author jun
     * @time:2017年8月2日 下午3:08:00
     */
    public static Page<Map<String, String>> findQqpViewByUrl(String url) {
        Page<Map<String, String>> pageList = new Page<Map<String, String>>();
        pageList.setPageNo(1);
        try {
            Document doc = Jsoup.connect("http://www.qiqipu.com" + url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                    .timeout(10000)
                    .get();
            Elements lis = doc.getElementsByClass("down_list").get(0).getElementsByTag("ul").get(0).getElementsByTag("li");
//            Elements vlink_3 = doc.getElementsByClass("vlist");
//            Elements urlLis = null;
//            if (vlink_3 != null) {
//                urlLis = vlink_3.get(0).getElementsByTag("ul").get(0).getElementsByTag("li");
//            }
            String ids[]=url.split("/");
            String id=ids[ids.length-1];
            List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
            for (int i = 0; i < lis.size(); i++) {
                Elements a = lis.get(i).getElementsByTag("a");
                Elements thunder = lis.get(i).getElementsByTag("input");
                Map<String, String> map = Maps.newHashMap();
                map.put("title", a.get(0).attr("title"));
                map.put("a", a.get(0).attr("href"));
                map.put("thunder", thunder.get(0).attr("value"));
                map.put("m3u8Url", "");
//                if (urlLis != null) {
//                    map.put("m3u8Url", urlLis.get(lis.size() - 1 - i).getElementsByTag("a").get(0).attr("href"));
                    map.put("m3u8Url", url+"/player.html?"+id+"-0-"+i);
//                }
                maps.add(map);
            }
            pageList.setCount(maps.size());
            pageList.setList(maps);
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return pageList;
    }

    /**
     * 获取种子
     *
     * @param url
     * @return String
     * @Description
     * @author jun
     * @time:2017年8月4日 下午5:23:50
     */
    public static String findQqpThunder(String url) {
        String thunder = "";
        try {
            //获取种子
            Document docs = Jsoup.connect("http://www.qiqipu.com" + url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                    .timeout(3000)
                    .get();
            Elements thunders = docs.getElementsByClass("down_url");
            thunder = thunders.get(0).val();
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return thunder;
    }


    /**
     * 获取m3u8
     *
     * @param url
     * @return String
     * @Description
     * @author jun
     * @time:2017年8月4日 下午5:23:50
     */
    public static String findM3u8Url(String url) {
        String m3u8url = "";
        try {
            //获取js
            Document docs = Jsoup.connect("http://www.qiqipu.com" + url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                    .timeout(3000)
                    .get();
            Elements playbox = docs.getElementsByClass("playbox");
            String js = playbox.get(0).getElementsByTag("script").get(0).attr("src");
            //获取js内容解析
            String urlJs = HttpUtil.getRetrunStr(js, null, null);
            ScriptEngineManager sem = new ScriptEngineManager();
            ScriptEngine engine = sem.getEngineByName("javascript");
            String m=url.substring(url.lastIndexOf("-")+1,url.length());
            String urlM = "   " +
                    "        var n = parseInt(nn); " +
                    "        var m = parseInt(" + m + "); " +
                    "        var list = VideoListJson; " +
                    "        var data = []; " +
                    "        if(list.length > 0) { " +
                    "            var subs = list[Math.min(n, list.length - 1)][1]; " +
                    "            data = subs[Math.min(m, subs.length - 1)].split('$'); " +
                    "        } " +
                    "            var url= data[1]; ";

            urlJs = urlJs.substring(0, urlJs.lastIndexOf(",urlinfo")) + ";";
            for (int n=0;n<10;n++) {
                engine.put("nn",n);
                engine.eval(urlJs + urlM);
                m3u8url = (String) engine.get("url");
                if(m3u8url.substring(m3u8url.lastIndexOf(".")+1,m3u8url.length()).equals("m3u8")){
                    break;
                }else {
                    m3u8url="";
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return m3u8url;
    }

    public static void main(String[] args) {
//		Page<Map<String,String>> pageList=findViewByUrl("/dsj/gcj/32444/");
        //%E6%88%91%E6%98%AF
        try {
            System.out.println(URLEncoder.encode("我", "gbk"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
