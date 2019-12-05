package com.jlkj.web.shopnew.request;

import lombok.Data;

@Data
public class GetFollowListRequest {

    private int uid;

    private int bossId;

    private int page;

    private int pageSize;

}
