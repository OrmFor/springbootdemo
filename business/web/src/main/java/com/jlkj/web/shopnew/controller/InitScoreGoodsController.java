package com.jlkj.web.shopnew.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.common.HttpUrlConfig;
import com.jlkj.web.shopnew.constant.Constant;
import com.jlkj.web.shopnew.constant.ConstantYqs;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.pojo.ScoreGoods;
import com.jlkj.web.shopnew.service.IScoreGoods;
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
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/1.0")
public class InitScoreGoodsController {

    private static final Logger logger = LogManager.getLogger(InitScoreGoodsController.class);


    @Autowired
    private IScoreGoods scoreGoodsSevice;

    @Autowired
    private HttpUrlConfig httpUrlConfig;

    @RequestMapping("/saveInitGoods")
    public ResultCode saveInitGoods(){
        CloseableHttpClient httpclient = HttpUtils.createWrapClient(httpUrlConfig.getHost());
        try {

            for(int i = 1 ;i <= 100 ; i++) {
                HttpPost httppost = new HttpPost(httpUrlConfig.getHost() + ConstantYqs.GOODS_LIST_URL);
                ContentType strContent = ContentType.create("text/plain", Charset.forName("UTF-8"));
                HttpEntity reqEntity = MultipartEntityBuilder.create()
                        .addTextBody("currentPage", i+"", strContent)
                        .addTextBody("orderBy", "goods_current_price", strContent)
                        .addTextBody("orderType", "asc", strContent)
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
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        if(jsonArray.size() > 0){
                            List<JSONObject> list =JSONObject.parseArray(jsonArray.toJSONString(), JSONObject.class);
                            for(JSONObject json1 : list){
                                ScoreGoods condi = new ScoreGoods();
                                condi.setGoodsId(json1.getInteger("id"));
                                ScoreGoods scoreGoods = scoreGoodsSevice.getByCondition(condi);
                                if(scoreGoods != null){
                                    logger.info(MessageFormat.format("商品{0}已存在",
                                            json1.getString("goods_name") ));
                                    continue;
                                }

                                ScoreGoods bean = new ScoreGoods();
                                bean.setCostPrice(json1.getBigDecimal("costPrice"));
                                bean.setGoodsCurrentPrice(json1.getBigDecimal("goods_current_price"));
                                bean.setGoodsId(json1.getInteger("id"));
                                bean.setGoodsName(json1.getString("goods_name"));
                                bean.setQrImgPath(json1.getString("qr_img_path"));
                                bean.setResult(json1.toJSONString());
                                bean.setSalesCount(json1.getInteger("salesCount"));
                                bean.setGoodsStatus(json1.getInteger("goods_status"));
                                bean.setCreateTime(new Date());
                                bean.setUpdateTime(new Date());
                                scoreGoodsSevice.insert(bean);

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

            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ResultCode(StatusCode.SUCCESS);
    }

}
