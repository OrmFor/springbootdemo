package com.jlkj.web.shopnew.request;

import lombok.Data;

@Data
public class UpdateCustomerTypeRequest {

    private int operationId;

    private int uid;

    private int type;
}
