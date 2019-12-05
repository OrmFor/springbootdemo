package com.jlkj.web.shopnew.request;


import lombok.Data;

import java.util.Date;

@Data
public class FollowMongoRequest {

    private int uid;

    private String userName;

    private String context;

    private int belongUid;

    private int isAfterChat;

    private int userType;

    private Date createTime;

    private Date updateTime;

    private int transfer;//转移标识

}
