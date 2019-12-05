package com.jlkj.web.shopnew.controller;


import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dto.LogDto;
import com.jlkj.web.shopnew.pojo.Log;
import com.jlkj.web.shopnew.pojo.mongo.LogMongo;
import com.jlkj.web.shopnew.service.ILog;
import com.jlkj.web.shopnew.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/1.0")
public class LogController extends BaseController {

    @Autowired
    private ILog logService;
    @Autowired
    private MongoTemplate mongoTemplate;



    @RequestMapping(value = "/list" , method = RequestMethod.POST)
    public ResultCode list() {
       /* Log bean = new Log();
        bean.setOperationId(895);
        List<Log> logs = logService.getListByLimit(bean,"id desc",1,5);*/

        List<LogDto> logs = logService.getInsertMongoDbAll();

        for(LogDto dto : logs) {
            LogMongo bean = LogMongo.builder().createTime(DateUtil.getDate(String.valueOf(dto.getCreateTime())))
                    .goodName(dto.getGoodsName() == null ? "" : dto.getGoodsName())
                    .logType(dto.getLogType())
                    .num(dto.getNum()).operationId(dto.getOperationId())
                    .operationName(dto.getOperationName())
                    .uid(dto.getUid())
            .userName(dto.getUsername())
            .updateTime(DateUtil.getDate(String.valueOf(dto.getCreateTime())))
            .userType(dto.getUserType()).build()
                    ;
            this.mongoTemplate.save(bean);
        }
        
        return new ResultCode(StatusCode.SUCCESS,logs);
    }

    @RequestMapping(value = "/testInsertLog")
    public ResultCode testInsertLog() {
       Log bean = new Log();
       bean.setOperationId(895);
       bean.setUid(1);
       bean.setType(1);
       bean.setCreateTime(System.nanoTime());
       bean.setGoodsName("测试测试再测试");
       bean.setNum(100);
       logService.insert(bean);

        return new ResultCode(StatusCode.SUCCESS);
    }



}