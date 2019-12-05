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
@Document(collection = Constant.LOGMONGO)
public class ShareLogMongo {


//  {
//     "uid":"访问者",
//     "userName":"访问者名字",
//     "operationId":"被操作人",
//     "operationName":"被操作人名字",
//     "logType":"类型1.点赞2.收藏 3.转发(分享)",
//     "num":"次数",
//     "createTime":"创建时间",
//     "updateTime":"更新时间",
//     "goodNames":"商品名称",
//                    "followNum":"跟进次数"
//                            "lastFollowTime": "最后跟进时间"
//     "type":"0.潜在客户 1.意向客户 2.客户"
//    }

    @Id
    private ObjectId id;

    @Indexed
    private int uid;

    private int companyId;

    private String userName;

    @Indexed
    private int operationId;

    private String userImg;

    private int followNum;

    private Date lastFollowTime;

    private String operationName;

    private int logType;

    private long num;

    private Date createTime;

    private Date updateTime;

    private String goodName;

    private int userType;

    private int operationRole;

}
