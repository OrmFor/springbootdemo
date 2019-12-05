package com.jlkj.web.shopnew.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LogDto {
/*        <result column="uid" property="uid" />
    <result column="operation_id" property="operationId" />
    <result column="type" property="logType" />
    <result column="num" property="num" />
    <result column="create_time" property="createTime" />
    <result column="goodsName" property="goodsName" />
    <result column="operationName" property="operationName" />
    <result column="username" property="username" />*/

    private int uid;
    private int operationId;
    private int logType;
    private long num;
    private long createTime;
    private String goodsName;
    private String operationName;
    private String username;

    private long updateTime;

    private int userType;

    private String goodName;
}
