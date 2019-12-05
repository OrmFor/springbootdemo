package com.jlkj.web.shopnew.request;


import lombok.Data;

import java.util.Date;

@Data
public class SaveLogRequest {
    //"uid":"访问者",
    //	"userName"："访问者名字",
    //	"operationId":"被操作人",
    //	"operationName":"被操作人名字",
    //	"type":"类型
    //	"staffCode":"员工内码",
    //	"createTime":"创建时间",
    //                "updateTime":"更新时间",
    //	"goodNames":"商品名称",
    //	"type":"0.潜在客户 1.意向客户 2.客户",
    //                "followNum":"跟进次数"
    //                "lastFollowTime": "最后跟进时间"

    private int uid;//访问者

    private String userName;//访问者名字

    private int operationId ;//被操作人

    private String operationName;//被操作人名字

    private String userImg;

    private int logType;

    private String goodName;

    private Date createTime;

    private Date updateTime;

    private int userType;

    private long num;

    private int companyId;

    private int isShare;//0否 1是

    private int shareId;//分享者id

    private int operationRole;

    private int followNum;

    private Date lastFollowTime;

    private int transfer;


}
