package com.jlkj.web.shopnew.pojo.mongo;


import com.jlkj.web.shopnew.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = Constant.FOLLOWMONGO)
public class FollowMongo {
    //follow
    //{
    //  "customerId":"客户id",
    //  "customerName":"客户名称",
    //  "context":"跟进内容",
    //  "belongUid":"所属用户",
    //  "belongUserName":"所属用户名",
    //  "createTime":"创建时间",
    //  "updateTime":"更新时间"
    //}

    @Id
    private ObjectId id;

    private int uid;

    private String userName;

    private String context;

    @Indexed
    private int belongUid;

    private String belongUserName;

    private int userType;//用户类型

    private int isAfterChat;

    private Date createTime;

    @Indexed
    private Date updateTime;

    private int transfer;//标识转移

}
