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
@Document(collection = Constant.CUSTOMERMONGO)
public class CustomerMongo {
    //customer:客户表
    //{
    //    "_id" : ObjectId("5d770da20933dc2625207622"),
    //    "tourist_no" : "用户游客ID",//客户内码
    //    "userName" : "用户名",//客户姓名
    //    "position" : "招商经理",//客户角色s
    //    "phone" : "13017714888",//客户手机号
    //    "createTime":"创建时间",
    //    "updateTime":"更新时间"
    //}
    //

    @Id
    private ObjectId id;

    @Indexed
    private int uid;

    private String userName;

    private String position;

    private String phone;

    private Date createTime;

    private Date updateTime;


}
