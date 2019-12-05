package com.jlkj.web.shopnew.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.common.HttpUrlConfig;
import com.jlkj.web.shopnew.constant.ConstantYqs;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.pojo.Address;
import com.jlkj.web.shopnew.pojo.AreaNew;
import com.jlkj.web.shopnew.service.IAddress;
import com.jlkj.web.shopnew.service.IAreaNew;
import com.jlkj.web.shopnew.util.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.List;


@RestController
@RequestMapping("/api/1.0")
public class InitAddressController {

    private static final Logger logger = LogManager.getLogger(InitAddressController.class);

    @Autowired
    private IAreaNew areaNewService;

    @Autowired
    private HttpUrlConfig httpUrlConfig;

    /**
     * @Author wwy
     * @Description 初始化area_new表 三层递归调用 省 --->  市 ---> 区
     * @Date 14:41 2019/11/9
     * @Param []
     * @return void
     **/
    @RequestMapping("/saveInitArea")
    public ResultCode saveInitArea() {
        JSONArray province = getList(null);
        for(int i = 0; i < province.size() ; i++){
            JSONObject province1= (JSONObject) province.get(i);
            System.out.println(province1);
            logger.info(MessageFormat.format("省份:{0}",province1));

            AreaNew beanProvice = new AreaNew();
            beanProvice.setLevel(province1.getString("level"));
            beanProvice.setAreaName(province1.getString("areaName"));
            beanProvice.setYqsAreaId(province1.getInteger("id"));

            areaNewService.insert(beanProvice);

            JSONArray city = this.getList(province1.getInteger("id"));

            for(int j = 0; j < city.size() ; j++){
                JSONObject city1= (JSONObject) city.get(j);
                logger.info(MessageFormat.format("城市:{0}",city1));

                AreaNew beanCity = new AreaNew();
                beanCity.setLevel(city1.getString("level"));
                beanCity.setAreaName(city1.getString("areaName"));
                beanCity.setYqsAreaId(city1.getInteger("id"));
                beanCity.setParentId(beanProvice.getYqsAreaId());
                areaNewService.insert(beanCity);

                JSONArray area = this.getList(city1.getInteger("id"));

                for(int k = 0; k < area.size(); k++){
                    JSONObject area1= (JSONObject) area.get(k);
                    logger.info(MessageFormat.format("地区:{0}",area1));
                    AreaNew beanArea = new AreaNew();
                    beanArea.setLevel(area1.getString("level"));
                    beanArea.setAreaName(area1.getString("areaName"));
                    beanArea.setYqsAreaId(area1.getInteger("id"));
                    beanArea.setParentId(beanCity.getYqsAreaId());
                    areaNewService.insert(beanArea);
                }
            }

        }
        this.modifyAddress();
        return new ResultCode(StatusCode.SUCCESS);

    }

    private JSONArray getList(Integer parentId){
        CloseableHttpClient httpclient = HttpUtils.createWrapClient(httpUrlConfig.getHost());
        try {

            HttpPost httppost = new HttpPost(httpUrlConfig.getHost() + ConstantYqs.AREA_GETAREA_URL);
            ContentType strContent = ContentType.create("text/plain", Charset.forName("UTF-8"));
            HttpEntity reqEntity = null ;
            if(parentId == null) {
                reqEntity =   MultipartEntityBuilder.create()
                        .addTextBody("parentId",  "", strContent)
                        .build();
            }else{
                reqEntity =   MultipartEntityBuilder.create()
                        .addTextBody("parentId",  parentId+"", strContent)
                        .build();
            }


            httppost.setEntity(reqEntity);
            httppost.setHeader("chartSet", "UTF-8");
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, "UTF-8");
                    JSONObject json = JSONObject.parseObject(result);

                    JSONArray jsonArray = json.getJSONArray("data");

                    return jsonArray;

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

        return null;
    }

    @Autowired
    private IAddress addressService;

    @Autowired
    private IAreaNew iAreaNewService;

    public void modifyAddress(){
        List<Address> list = addressService.getList();

        for(Address address : list){
            String[] names = address.getArea().split(" ");
            System.out.println(names[0]);

            int areaId =-1;
            int cityId =-1;
            int proviceId =-1;
            if(names.length == 3) {
                proviceId = iAreaNewService.selectByName(names[0]);
                cityId = iAreaNewService.selectByName(names[1]);
                areaId =iAreaNewService.selectByName(names[2]);
            }else if(names.length == 2){
                proviceId = iAreaNewService.selectByName(names[0]);
                cityId = iAreaNewService.selectByName(names[1]);
            }

            Address update = new Address();
            update.setProviceId(proviceId);
            update.setCityId(cityId);
            update.setAreaId(areaId);

            Address condi = new Address();
            condi.setId(address.getId());

            addressService.updateByCondition(update,condi);
        }
    }
}
