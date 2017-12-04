package com.thinkgem.jeesite.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 常用工具类
 */
public class CommonUtils {

    /**
     * 获取唯一ID
     * @return
     */
    public static String getUniqueId(){
        Integer uuid=Math.abs(UUID.randomUUID().hashCode());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        String time=simpleDateFormat.format(new Date());
        String uuids=StringUtils.rightPad(uuid.toString(),10,"0");
        String str=time+uuids;
        return str;
    }

    public static void main(String[] args) {
        for (int i=0;i<100;i++) {
            getUniqueId();
        }
    }
}
