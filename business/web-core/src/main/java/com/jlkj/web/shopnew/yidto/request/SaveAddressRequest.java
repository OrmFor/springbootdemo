package com.jlkj.web.shopnew.yidto.request;


import com.jlkj.web.shopnew.yidto.base.BaseYqsRequest;
import lombok.Data;

@Data
public class SaveAddressRequest extends BaseYqsRequest {
    private int uid;

    private String areaInfo;

    private String area;//省 市 区

    private String mobile;

    private String trueName;

    private int zip;

    private int areaId;

    private String userId;

    private int isDefault;


}
