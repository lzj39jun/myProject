package com.thinkgem.jeesite.modules.bis.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.HttpUtil;
import com.thinkgem.jeesite.common.utils.JsonUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.net.URLEncoder;
import java.util.*;


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
            String urls="http://www.qiqipu.com/search.asp?page=" + page + "&searchword=" + name + "&searchtype=-1";
            Document doc = Jsoup.parse(htmlPage(urls));//获取html文档

//            Document doc = Jsoup.connect("http://www.qiqipu.com/search.asp")
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
//                    .timeout(10000)
//                    .header("Cookie","UM_distinctid=1694cc01f3b386-0e396bf3be123-36667105-13c680-1694cc01f3c2f8; bdshare_firstime=1551770871492; bz_finger=7a06ec65a1376ea3cb574fb70ab9248f; __cm_warden_upi=MTE1LjE5My4xNzUuMTAz; security_session_verify=d667099b95f5132fe7a5108c97180efa; ASPSESSIONIDSCSTSBTC=CJDNMMGDHKGCHMFPEOKKLEAC; 2242_2232_115.193.175.103=1; CNZZDATA1273089026=955754203-1551765859-http%253A%252F%252Fwww.qiqipu.com%252F%7C1552379156; CNZZDATA1260755200=392148868-1551769236-http%253A%252F%252Fwww.qiqipu.com%252F%7C1552379261; Hm_lvt_473f6d12b3211620aa788ce202546e01=1551770853,1552380526; 77gg=2; cscpvrich8838_fidx=3; __cm_warden_uid=c7ff6aa044fa5ad7a7c7811431168da7cookie; AD_480=\"idx:0\"; Hm_lpvt_473f6d12b3211620aa788ce202546e01=1552381351")
////                    .data("searchword",name)
//                    .post();
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
                map.put("number", (new Random().nextInt(9001) + 1000) + "");
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
        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
        try {
//            maps = filter(url);
//            if (maps.isEmpty()) {
            String urls="http://www.qiqipu.com" + url;
            Document doc = Jsoup.parse(htmlPage(urls));//获取html文档
//                Document doc = Jsoup.connect("http://www.qiqipu.com" + url)
//                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
//                        .timeout(10000)
//                        .get();
                Elements lis = doc.getElementsByClass("down_list").get(0).getElementsByTag("ul").get(0).getElementsByTag("li");
                String ids[] = url.split("/");
                String id = ids[ids.length - 1];
                for (int i = 0; i < lis.size(); i++) {
                    Elements a = lis.get(i).getElementsByTag("a");
                    Elements thunder = lis.get(i).getElementsByTag("input");
                    Map<String, String> map = Maps.newHashMap();
                    map.put("title", a.get(0).attr("title"));
                    map.put("a", a.get(0).attr("href"));
                    map.put("thunder", thunder.get(0).attr("value"));
                    map.put("m3u8Url", url + "/player.html?" + id + "-0-" + i);
                    maps.add(map);
                }

//            }
        } catch (Exception e) {
            LOGGER.error(e);
            Map<String, String> map = Maps.newHashMap();
            map.put("title", "电影");
            map.put("thunder", "只能在线播放");
            map.put("m3u8Url", url + "/player.html?" + url + "-0-" + 0);
            maps.add(map);
        }
        LOGGER.error("pageList" + JsonUtils.object2Json(pageList));
        pageList.setCount(maps.size());
        pageList.setList(maps);
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
            String urls="http://www.qiqipu.com" + url;
            Document docs = Jsoup.parse(htmlPage(urls));//获取html文档
            //获取js
//            Document docs = Jsoup.connect("http://www.qiqipu.com" + url)
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
//                    .timeout(3000)
//                    .get();
            Elements playbox = docs.getElementsByClass("playbox");
            String js = playbox.get(0).getElementsByTag("script").get(0).attr("src");
            //获取js内容解析
            String urlJs = HttpUtil.getRetrunStr(js, null, null);
            ScriptEngineManager sem = new ScriptEngineManager();
            ScriptEngine engine = sem.getEngineByName("javascript");
            String m = url.substring(url.lastIndexOf("-") + 1, url.length());
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
            LOGGER.info("---1");
            for (int n = 0; n < 10; n++) {
                LOGGER.info("---2-"+n);
                engine.put("nn", n);
                LOGGER.info("---3-"+n);
                engine.eval(urlJs + urlM);
                m3u8url = (String) engine.get("url");
                if (m3u8url.substring(m3u8url.lastIndexOf(".") + 1, m3u8url.length()).equals("m3u8")) {
                    break;
                } else {
                    m3u8url = "";
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return m3u8url;
    }

    /**
     * 七七铺
     * 最新资源
     *
     * @return List<String>
     * @Description
     * @author jun
     * @time:2017年8月2日 下午3:08:00
     */
    public static Map qqpNewList() {
        Map mapNew = new HashMap();
        try {
            String urls="http://www.qiqipu.com" ;
            Document doc = Jsoup.parse(htmlPage(urls));//获取html文档
//            Document doc = Jsoup.connect("http://www.qiqipu.com/")
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
//                    .timeout(10000)
//                    .get();
            Elements elements = doc.getElementsByClass("commend typebox");
            for (int i = 0; i < elements.size(); i++) {
                if (i == 0 || i == 3) {
                    continue;
                }
                List<Map> list = new ArrayList<Map>();
                Elements lis = elements.get(i).getElementsByTag("ul").get(0).getElementsByTag("li");
                for (Element li : lis) {
                    Elements a = li.getElementsByTag("a");
                    Elements img = li.getElementsByTag("img");
                    String region = li.getElementsByTag("div").get(0).text();
                    String regions[] = StringUtils.split(region, "-");
                    Map<String, String> map = Maps.newHashMap();
                    map.put("a", a.get(0).attr("href"));
                    map.put("img", img.get(0).attr("src"));
                    map.put("name", img.get(0).attr("alt"));
                    map.put("year", regions[0]);
                    map.put("number", (new Random().nextInt(901) + 100) + "");
                    if (regions.length > 1) {
                        map.put("region", "地区：" + regions[1]);
                    }
                    list.add(map);
                }
                if (i == 1) {
                    mapNew.put("movie", list);
                } else if (i == 2) {
                    mapNew.put("tv", list);
                } else if (i == 4) {
                    mapNew.put("variety", list);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return mapNew;
    }

    /**
     * 直接指定地址
     *
     * @param id
     * @return
     */
    public static List<Map<String, String>> filter(String id) {
        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
        if (id.equals("/dy/khp/36991/")) {
            ///dy/khp/36991/
            ///dy/xjp/39558/
            Map<String, String> map = Maps.newHashMap();
            map.put("title", "侏罗纪世界2");
            map.put("a", id);
            map.put("thunder", "");
            map.put("m3u8Url", id + "/player.html?" + id + "-0-0");
            maps.add(map);
        } else if (id.equals("/dy/xjp/39558/")) {
            Map<String, String> map = Maps.newHashMap();
            map.put("title", "猛虫过江");
            map.put("a", id);
            map.put("thunder", "");
            map.put("m3u8Url", id + "/player.html?" + id + "-0-0");
            maps.add(map);
        }
        return maps;
    }

    public static String htmlPage(String url){
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(false); //很重要，启用JS
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
        HtmlPage htmlPage = null;
        try {
            htmlPage = webClient.getPage(url);//尝试加载上面图片例子给出的网页
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }
            webClient.waitForBackgroundJavaScript(3000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
        return htmlPage.asXml();//直接将加载完成的页面转换成xml格式的字符串
    }

    public static void main(String[] args) {
        qqpNewList();
    }
}
