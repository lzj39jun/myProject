package com.thinkgem.jeesite.common.utils;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 赛邮短信
 */
public class SmsSubmallUtils {
    private static Logger log = LoggerFactory.getLogger(SmsSubmallUtils.class);

    public static String signature = "6f9344ea32fd98d0a706e8fac117039a";
    public static String appid = "18691";
    public static String project = "k1sHT2";
    public static String code = "ygrDkt14p9";

    public static JsonVO xsend(String mobile) {
        String url = "https://api.mysubmail.com/message/xsend";
        JSONObject vars = new JSONObject();
        vars.put("code", code);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("appid", appid);
        paramMap.put("to", mobile);
        paramMap.put("project", project);
        paramMap.put("vars", vars);
        paramMap.put("signature", signature);
        JSONObject jsonObject = HttpUtil.postMethodUrl(url, null, paramMap, null);
        return null;
    }

    public static void main(String[] args) {
        String phones[] = {"1326058", "1388618", "1335828", "1588008", "1368208", "1582288", "1307858", "1398558", "1318898", "1328080", "1333788", "1381366"};
        for (int j = 0; j < phones.length; j++) {
            for (int i = 0; i < 20; i++) {
                String mobile = phones[j] + (int) ((Math.random() * 9 + 1) * 1000);
//            String mobile="15158036769";
                xsend(mobile);
            }
        }

    }

}

