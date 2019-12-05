package com.jlkj.web.shopnew.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.constant.Constant;
import com.jlkj.web.shopnew.constant.ConstantYqs;
import com.jlkj.web.shopnew.pojo.Address;
import com.jlkj.web.shopnew.pojo.User;
import com.jlkj.web.shopnew.service.IAddress;
import com.jlkj.web.shopnew.service.IUser;
import com.jlkj.web.shopnew.service.YiService;
import com.jlkj.web.shopnew.util.DateUtil;
import com.jlkj.web.shopnew.util.HttpUtils;
import com.jlkj.web.shopnew.yidto.request.EditYqsAddressRequest;
import com.jlkj.web.shopnew.yidto.request.SaveAddressRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

@Service
public class YiImpl implements YiService {

    @Autowired
    private IAddress addressService;

    @Autowired
    private IUser userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAddress(SaveAddressRequest saveAddressRequest) {
        CloseableHttpClient httpclient = HttpUtils.createWrapClient(saveAddressRequest.getHost());
        try {
            HttpPost httppost = new HttpPost(saveAddressRequest.getHost() + ConstantYqs.ADDRESS_ADD_URL);
            ContentType strContent = ContentType.create("text/plain", Charset.forName("UTF-8"));
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addTextBody("areaInfo", saveAddressRequest.getAreaInfo(), strContent)
                    .addTextBody("moblie", saveAddressRequest.getMobile(), strContent)
                    .addTextBody("trueName", saveAddressRequest.getTrueName(), strContent)
                    .addTextBody("zip", saveAddressRequest.getZip() + "", strContent)
                    .addTextBody("areaId", saveAddressRequest.getAreaId() + "", strContent)
                    .addTextBody("userId", saveAddressRequest.getUserId() + "", strContent)
                    .addTextBody("isDefault", saveAddressRequest.getIsDefault() + "", strContent)
                    .build();

            httppost.setEntity(reqEntity);
            httppost.setHeader("chartSet", "UTF-8");
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, "UTF-8");
                    JSONObject json = JSONObject.parseObject(result);
                    JSONObject jsonObject = json.getJSONObject("data");
                    Integer addressId = jsonObject.getInteger("id");

                    Address bean = new Address();
                    bean.setAddressId(addressId).setUid(saveAddressRequest.getUid())
                            .setPhone(saveAddressRequest.getMobile())
                            .setArea(saveAddressRequest.getArea() + "")
                            .setAddress(saveAddressRequest.getAreaInfo())
                            .setCreateTime((int) DateUtil.getTime(new Date()))
                            .setUpdateTime((int) DateUtil.getTime(new Date()))
                            .setConsignee(saveAddressRequest.getTrueName())
                            .setAreaId(saveAddressRequest.getAreaId());
                    addressService.insert(bean);

                    if (saveAddressRequest.getIsDefault() == 1) {
                        saveDefaultAddress(saveAddressRequest.getUid(), bean.getId());
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @return void
     * @Author wwy
     * @Description 更新addressId
     * @Date 18:30 2019/11/8
     * @Param [id, addressId]
     **/
    @Transactional(rollbackFor = Exception.class)
    public void updateAddress(Integer id, Integer addressId) {
        Address condi = new Address();
        condi.setId(id);
        Address bean = new Address();
        bean.setAddressId(addressId);
        addressService.updateByCondition(bean, condi);
    }

    @Override
    public void editYqsAddress(EditYqsAddressRequest editYqsAddressRequest) {
        CloseableHttpClient httpclient = HttpUtils.createWrapClient(editYqsAddressRequest.getHost());
        try {
            HttpPost httppost = new HttpPost(editYqsAddressRequest.getHost() + ConstantYqs.ADDRESS_EDIT_URL);
            ContentType strContent = ContentType.create("text/plain", Charset.forName("UTF-8"));
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addTextBody("areaInfo", editYqsAddressRequest.getAreaInfo(), strContent)
                    .addTextBody("moblie", editYqsAddressRequest.getMobile(), strContent)
                    .addTextBody("trueName", editYqsAddressRequest.getTrueName(), strContent)
                    .addTextBody("zip", editYqsAddressRequest.getZip() + "", strContent)
                    .addTextBody("areaId", editYqsAddressRequest.getAreaId() + "", strContent)
                    .addTextBody("userId", editYqsAddressRequest.getUserId() + "", strContent)
                    .addTextBody("addressId", editYqsAddressRequest.getAddressId() + "", strContent)
                    .addTextBody("isDefault", editYqsAddressRequest.getIsDefault() + "", strContent)
                    .build();

            httppost.setEntity(reqEntity);
            httppost.setHeader("chartSet", "UTF-8");
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, "UTF-8");
                    JSONObject json = JSONObject.parseObject(result);
                    int httpCode = json.getInteger("code");
                    if (HttpStatus.SC_OK == httpCode) {

                        Address condi = new Address();
                        condi.setAddressId(editYqsAddressRequest.getAddressId())
                                .setUid(editYqsAddressRequest.getUid());

                        Address bean = new Address();
                        bean.setPhone(editYqsAddressRequest.getMobile())
                                .setArea(editYqsAddressRequest.getAreaId() + "")
                                .setAddress(editYqsAddressRequest.getAreaInfo())
                                .setArea(editYqsAddressRequest.getArea())
                                .setUpdateTime((int) DateUtil.getTime(new Date()))
                                .setConsignee(editYqsAddressRequest.getTrueName())
                                .setAreaId(editYqsAddressRequest.getAddressId());
                        addressService.updateByCondition(bean, condi);

                        //修改user表
                        if (editYqsAddressRequest.getIsDefault() == 1) {
                            saveDefaultAddress(editYqsAddressRequest.getUid(), bean.getId());
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveDefaultAddress(int uid, int addressId) {
        User condition = new User();
        condition.setId(uid);
        User update = new User();
        update.setAddressId(addressId);
        userService.updateByCondition(update, condition);
    }
}
