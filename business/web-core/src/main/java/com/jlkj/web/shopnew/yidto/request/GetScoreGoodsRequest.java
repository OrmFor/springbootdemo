package com.jlkj.web.shopnew.yidto.request;


import com.jlkj.web.shopnew.yidto.base.BaseYqsRequest;
import lombok.Data;

@Data
public class GetScoreGoodsRequest extends BaseYqsRequest {

    //页码
    private int page;

    private int pageSize;
}
