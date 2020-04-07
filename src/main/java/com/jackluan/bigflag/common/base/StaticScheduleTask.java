package com.jackluan.bigflag.common.base;

import com.jackluan.bigflag.common.constant.CacheObject;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.share.service.INoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @Author: jack.luan
 * @Date: 2020/4/6 11:27
 */
@Slf4j
@Component
@EnableScheduling
public class StaticScheduleTask {

    @Autowired
    private INoticeService noticeService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    private void cleanOaAccessToken() {
        CacheObject.weChatCache.remove(SystemConstant.WE_CHAT_OA_ACCESS_TOKEN_KEY);
        CacheObject.weChatCache.remove(SystemConstant.WE_CHAT_APP_ACCESS_TOKEN_KEY);
        log.info("clean access token cache success");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    private void cleanOSSCache() {
        CacheObject.fileGroupCache.clear();
        log.info("clean OSS cache success");
    }

    @Scheduled(cron = "10 0/1 * * * ?")
    private void sendNoticeMsg() {
        long time = System.currentTimeMillis();
        noticeService.batchNoticeUser();
        log.info("batch notice user success. use time :{}ms", System.currentTimeMillis() - time);
    }

    @Scheduled(cron = "20 0/10 * * * ?")
    private void noticeApprove() {
        long time = System.currentTimeMillis();
        noticeService.batchNoticeApprove();
        log.info("batch notice sign approve success. use time :{}ms", System.currentTimeMillis() - time);
    }

}
