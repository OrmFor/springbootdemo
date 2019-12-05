package com.jlkj.web.shopnew.request;

import lombok.Data;

import java.util.Date;

@Data
public class GetListByUidRequest {

    private int page;

    private int pageSize;

    private int uid;
}
