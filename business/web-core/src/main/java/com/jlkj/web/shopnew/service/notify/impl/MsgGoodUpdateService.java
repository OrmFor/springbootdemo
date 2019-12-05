package com.jlkj.web.shopnew.service.notify.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.constant.ConstantYqs;
import com.jlkj.web.shopnew.pojo.Address;
import com.jlkj.web.shopnew.pojo.ScoreGoods;
import com.jlkj.web.shopnew.service.IScoreGoods;
import com.jlkj.web.shopnew.service.notify.CreateNotifyServiceAdapter;
import com.jlkj.web.shopnew.util.DateUtil;
import com.jlkj.web.shopnew.util.HttpUtils;
import com.jlkj.web.shopnew.yidto.message.bean.MqMessage;
import com.jlkj.web.shopnew.yidto.message.bean.YqsGoods;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;


/**
* @Author wwy
* @Description 修改商品
* @Date 11:18 2019/11/29
* @Param
* @return
**/
@Service
public class MsgGoodUpdateService extends CreateNotifyServiceAdapter {

    @Autowired
    private IScoreGoods scoreGoodsService;

    @Override
    public String processNotifyByMsgType(MqMessage mqMessage) {
        this.modifyGoodsFromYqs(mqMessage);
        return "success";
    }

    @Override
    public String[] msgType() {
        return new String[]{
                MqMessage.MSG_GOODS_UPDATE
        };
    }


    public void modifyGoodsFromYqs(MqMessage mqMessage){
        YqsGoods goods = JSON.parseObject(mqMessage.getContent(),YqsGoods.class);
        CloseableHttpClient httpclient = HttpUtils.createWrapClient(mqMessage.getHost());
        try {
            HttpPost httppost = new HttpPost(mqMessage.getHost() + ConstantYqs.GOODS_DETAIL_URL);
            ContentType strContent = ContentType.create("text/plain", Charset.forName("UTF-8"));
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addTextBody("id", goods.getGoodsId()+"", strContent)
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
                    if(jsonObject != null) {
                        BigDecimal goodsCurrentPrice = jsonObject.getBigDecimal("goodsCurrentPrice");
                        String goodsName = jsonObject.getString("goodsName");
                        Integer goodsSaleNum = jsonObject.getInteger("goodsSaleNum");
                        int goodsStatus = jsonObject.getInteger("goodsStatus");
                        String qrImgPath = jsonObject.getString("qr_img_path");
                        ScoreGoods condi = new ScoreGoods();
                        condi.setGoodsId(goods.getGoodsId());
                        ScoreGoods scoreGoods = scoreGoodsService.getByCondition(condi);
                        if (scoreGoods != null) {
                            ScoreGoods bean = new ScoreGoods();
                            bean.setGoodsStatus(goodsStatus)
                                    .setCostPrice(goodsCurrentPrice)
                                    .setGoodsName(goodsName)
                                    .setGoodsCurrentPrice(goodsCurrentPrice)
                                    .setQrImgPath(qrImgPath)
                                    .setSalesCount(goodsSaleNum)
                                    .setResult(result)
                                    .setUpdateTime(new Date());
                            scoreGoodsService.updateByCondition(bean, condi);
                        }else{
                            ScoreGoods bean = new ScoreGoods();
                            bean.setGoodsStatus(goodsStatus)
                                    .setCostPrice(goodsCurrentPrice)
                                    .setGoodsName(goodsName)
                                    .setGoodsCurrentPrice(goodsCurrentPrice)
                                    .setQrImgPath(qrImgPath)
                                    .setSalesCount(goodsSaleNum)
                                    .setResult(result)
                                    .setGoodsId(goods.getGoodsId())
                                    .setCreateTime(new Date());
                            scoreGoodsService.insert(bean);
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

}
