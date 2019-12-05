package com.jlkj.web.shopnew.request;


import lombok.Data;

@Data
public class GetLogListByCustomerRequest {

    private int page;

    private int pageSize;

    private int uid;

    private int operationId;//被访问者

    private boolean isBoss;
}
