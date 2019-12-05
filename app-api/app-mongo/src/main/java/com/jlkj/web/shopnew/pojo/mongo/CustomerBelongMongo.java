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
@Document(collection = Constant.CUSTOMERBELONGMONGO)
public class CustomerBelongMongo {

    //belong:客户归属内码
    //{
    //    "belongToUserName" : "王波",//所属用户
    //    "belongToUserNo" : "wb123",//所属用户内码
    //    "tourist_no":"客户内码",
    //	"type" : "1潜在客户,2意向客户,3客户",//客户类型
    //    "label" : "标签1,标签2,标签3",//客户标签
    //}


    @Id
    private ObjectId id;

    @Indexed
    private int belongUid;

    @Indexed
    private int uid;

    private String userName;

    private String belongUserName;

    //被标记客户类型
    private int userType;

    private int potentialType;

    private int intentionalType;

    //被标记潜在客户时间
    private Date potentialTime;

    //被标记意向客户时间
    private Date intentionalTime;

    //被标记客户时间
    private Date customerTime;

    private String label;

    private Date updateTime;

    private Date createTime;

    private String phone;

    private int transfer;//转移标识
}
