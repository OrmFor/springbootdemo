package java;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

public class TestJson {

    //{"addTime":1568191345000,"areaName":"南京市","common":false,"deleteStatus":0,"id":922,"level":1,"sequence":999}
    public static void main(String[] args){

        JSONObject json = new JSONObject();
        json.put("addTime",1568191345000L);
        json.put("areaName","南京市");
        json.put("common",false);
        json.put("deleteStatus",0);
        json.put("id",992);
        json.put("level",1);
        json.put("sequence",999);

        Dto dto = JSONObject.toJavaObject(json,Dto.class);
        System.out.println(dto.getAreaName());

    }

}


@Data
class Dto{
    private long addTime;

    private String areaName;

    private boolean common;

    private int deleteStatus;

    private int id;

    private int level;

    private int sequence;

}
