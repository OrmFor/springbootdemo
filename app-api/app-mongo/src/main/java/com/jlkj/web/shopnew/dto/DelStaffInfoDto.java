package com.jlkj.web.shopnew.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DelStaffInfoDto {

    private int staffId;//员工id

    private String staffName;//员工姓名

    private String staffPic;//员工头像

    private int customerCount;//客户总数
}
