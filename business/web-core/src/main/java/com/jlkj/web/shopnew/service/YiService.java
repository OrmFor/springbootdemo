package com.jlkj.web.shopnew.service;


import com.jlkj.web.shopnew.yidto.request.EditYqsAddressRequest;
import com.jlkj.web.shopnew.yidto.request.SaveAddressRequest;

public interface YiService {


    void saveAddress(SaveAddressRequest saveAddressRequest);

    void updateAddress(Integer  id , Integer addressId);

    void editYqsAddress(EditYqsAddressRequest editYqsAddressRequest);
}
