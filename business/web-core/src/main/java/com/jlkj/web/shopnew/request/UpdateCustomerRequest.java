package com.jlkj.web.shopnew.request;

        import lombok.Data;

@Data
public class UpdateCustomerRequest {

    private String visitorCode;

    private String visitedCode;

    private int type;
}
