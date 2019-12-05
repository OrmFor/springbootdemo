package com.jlkj.web.shopnew.request;

import com.jlkj.web.shopnew.dto.DistributeDto;
import lombok.Data;

import java.util.List;

@Data
public class DistributeRequest {

    private int originalStaffId;//旧员工id

    private List<DistributeDto> list;//分配对应关系

    private int page;

    private int pageSize;

}
