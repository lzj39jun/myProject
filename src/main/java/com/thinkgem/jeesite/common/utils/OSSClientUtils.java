package com.thinkgem.jeesite.common.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

import java.util.ArrayList;
import java.util.List;

/**
 * 阿里云oss管理类
 */
public class OSSClientUtils {
    public static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    public static String accessKeyId = "LTAIXylr3JLoGcaL";
    public static String accessKeySecret = "iZHdZSLKUclU8ofkV8fbejlmMFd3pI";
    public static String url = "http://ijunlin.oss-cn-hangzhou.aliyuncs.com/";
    //空间
    private static String bucketName = "ijunlin";
    //文件存储目录
    private static String filedir = "home/";

    public static List<String> listObjects() {
        List<String> stringList = new ArrayList<String>();
        // 初始化OSSClient
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 获取指定bucket下的所有Object信息
        ObjectListing listing = ossClient.listObjects(bucketName);
        // 遍历所有Object
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            if (!objectSummary.getKey().equals(filedir)) {
                stringList.add(url + objectSummary.getKey());
            }
        }
        return stringList;
    }
    public static void main(String[] args) {
        OSSClientUtils ossClientUtils = new OSSClientUtils();
        ossClientUtils.listObjects();
    }

}
