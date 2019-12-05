package com.jlkj.web.shopnew.yidto.request;


import lombok.Data;

@Data
public class ProcessOrderRequest {

    private String orderNum;

    private Integer id;

    private int orderStatus;
}
