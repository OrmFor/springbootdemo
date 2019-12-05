package com.jlkj.web.shopnew.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class HotTask extends BaseTask{
    private static final Logger logger = LogManager.getLogger(HotTask.class);


    //                 0 0 10,14,16 * * ?
    @Scheduled(cron = "0 1,11,21,31,41,51 * * * ?") // 秒 分 时 日 月 星期几
    public void run() {
        super.run();
    }

    @Override
    protected void doTask() {
        logger.info("=============HotTask================");

    }

    @Override
    protected void setThreadName() {
        Thread.currentThread().setName(this.getModule());
    }
}
