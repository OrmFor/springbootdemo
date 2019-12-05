package com.jlkj.web.shopnew.yidto.request;


import com.jlkj.web.shopnew.yidto.base.BaseYqsRequest;
import lombok.Data;

@Data
public class EditYqsAddressRequest extends BaseYqsRequest {
    //areaInfo	 	R	详细地址
    //moblie	string	R	收件人手机号
    //trueName	string	R	收件人
    //zip	int	R	邮编
    //areaId	long	R	区县id
    //userId	string	R	商户id
    //isDefault	int	O	是否默认地址 0不是，1是
    //addressId	int	R	地址id

    private String areaInfo;

    private String mobile;

    private String trueName;

    private int zip;

    private long areaId;

    private String userId;

    private int isDefault;

    private int addressId;

    private int uid;

    private String area;
}
