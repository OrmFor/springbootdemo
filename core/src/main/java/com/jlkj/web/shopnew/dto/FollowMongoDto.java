package com.jlkj.web.shopnew.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FollowMongoDto {

    private String logo;//客户头像

    private String userName;//客户姓名

    private Date createTime;//跟进时间

    private String context;//跟进内容

}
