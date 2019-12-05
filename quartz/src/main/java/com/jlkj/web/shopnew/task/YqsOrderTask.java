//package com.jlkj.web.shopnew.task;
//
//
//import cc.s2m.web.utils.webUtils.vo.Expressions;
//import com.jlkj.web.shopnew.pojo.ScoreOrder;
//import com.jlkj.web.shopnew.service.IScoreOrder;
//import com.jlkj.web.shopnew.util.DateUtil;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.List;
//
//@Component
//@EnableScheduling
//public class YqsOrderTask extends BaseTask {
//
//    private static final Logger logger = LogManager.getLogger(YqsOrderTask.class);
//
//    @Autowired
//    private IScoreOrder scoreOrderService;
//
//
//    //                 0 0 10,14,16 * * ?
//    @Scheduled(cron = "10 * * * * ? ") // 秒 分 时 日 月 星期几
//    public void run() {
//        super.run();
//    }
//
//
//    @Override
//    protected void doTask() {
//        ScoreOrder condition = new ScoreOrder();
//        String format = "yyyy-MM-dd";
//        String yestoday = DateUtil.getYesterdayTime(new Date(), format);
//        Date thirtyMin = DateUtil.rollMinute(new Date(), -30);
//        condition.and(Expressions.ge("create_time", yestoday));
//        condition.and(Expressions.lt("create_time", thirtyMin));
//
//        List<ScoreOrder> list = scoreOrderService.getList(condition);
//
//        if(list != null &&  list.size() != 0){
//
//        }
//
//    }
//
//    @Override
//    protected void setThreadName() {
//        Thread.currentThread().setName(this.getModule());
//    }
//}
