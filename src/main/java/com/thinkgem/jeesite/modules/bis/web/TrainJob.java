package com.thinkgem.jeesite.modules.bis.web;

import com.thinkgem.jeesite.modules.bis.service.BisSmsMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by jun on 2017/10/10.
 */
@Service
@Lazy(false)
public class TrainJob {


    @Autowired
    private BisSmsMobileService bisSmsMobileService;

    /**
     * 方法说明：给还剩一天结束并且未完成的教师培训发送系统消息和推送(每30分钟)
     * 变更原因（若有变更）：
     */
//    @Scheduled(cron = "0 0/30 * * * ?")
//    @Scheduled(cron = "0/3 * * * * ?")
//    @Scheduled(fixedRate = 1000*60*60*24)
//    public void userTrainSendMsg() {
//        this.bisSmsMobileService.sendSms();
//    }

}
