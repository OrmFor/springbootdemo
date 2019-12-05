package com.jlkj.web.shopnew.pojo.mongo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/*@Document(collection = "BrowseRecord")*/
public class BrowseRecord {

    @Id
    private ObjectId id;

    //访问者姓名
    private String visitor;

    private int type;

    //访问者内码
    @Indexed
    private String visitorCode;

    //第几次浏览
    private String num;

    private String visited;

    @Indexed
    private String visitedCode;

    //访问时间
    @Field("time")
    private Date time;


}
