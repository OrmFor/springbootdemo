package com.jlkj.web.shopnew.yidto.request;

import lombok.Data;

@Data
public class ProcessOrderNumRequest {

    private String selfOrderNum;

    private int orderId;

    private String orderNum;
}
