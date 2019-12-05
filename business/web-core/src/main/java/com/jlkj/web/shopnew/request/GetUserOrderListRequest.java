package com.jlkj.web.shopnew.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserOrderListRequest {

    private int pageSize;

    private int page;

    private int uid;

}
