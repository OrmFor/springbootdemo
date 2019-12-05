package com.jlkj.web.shopnew.service.impl;

import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.constant.Constant;
import com.jlkj.web.shopnew.constant.ConstantYqs;
import com.jlkj.web.shopnew.dao.ScoreGoodsMapper;
import com.jlkj.web.shopnew.enums.EnumScoreGoodsStatus;
import com.jlkj.web.shopnew.pojo.Address;
import com.jlkj.web.shopnew.pojo.ScoreGoods;
import com.jlkj.web.shopnew.request.ScoreGoodsRequest;
import com.jlkj.web.shopnew.service.IScoreGoods;
import com.jlkj.web.shopnew.util.DateUtil;
import com.jlkj.web.shopnew.util.HttpUtils;
import com.jlkj.web.shopnew.yidto.request.GetScoreGoodsRequest;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

@Service
public class ScoreGoodsImpl extends BaseServiceImpl<ScoreGoods, ScoreGoodsMapper, Integer> implements IScoreGoods {
    @Autowired
    private ScoreGoodsMapper scoreGoodsMapper;

    protected ScoreGoodsMapper getDao() {
        return scoreGoodsMapper;
    }


    @Override
    public void saveGetShopList(String host) {

        CloseableHttpClient httpclient = HttpUtils.createWrapClient(host);
        try {

            for(int i = 1 ;i <= 100 ; i++) {
                HttpPost httppost = new HttpPost(host + ConstantYqs.GOODS_LIST_URL);
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
                                ScoreGoods bean = new ScoreGoods();
                                bean.setCostPrice(json1.getBigDecimal("costPrice"));
                                bean.setGoodsCurrentPrice(json1.getBigDecimal("goods_current_price"));
                                bean.setGoodsId(json1.getInteger("id"));
                                bean.setGoodsName(json1.getString("goods_name"));
                                bean.setQrImgPath(json1.getString("qr_img_path"));
                                bean.setResult(json1.toJSONString());
                                bean.setGoodsStatus(json1.getInteger("goods_status"));
                                bean.setCreateTime(new Date());
                                bean.setUpdateTime(new Date());

                                this.insert(bean);
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

    }

    @Override
    public List<ScoreGoods> getScoreGoods(GetScoreGoodsRequest getScoreGoodsRequest) {
        int page = getScoreGoodsRequest.getPage() <=0 ? 1 : getScoreGoodsRequest.getPage();
        int pageSize = getScoreGoodsRequest.getPage() <=0 ? 10 : getScoreGoodsRequest.getPageSize();
        ScoreGoods condi = new ScoreGoods();
        condi.and(Expressions.gt("self_price",BigDecimal.ZERO));
        condi.and(Expressions.gt("score",BigDecimal.ZERO));
        condi.setGoodsStatus(EnumScoreGoodsStatus.GOODS_ON.getStatus());
        condi.setOrderBy("goods_current_price,costPrice asc");
        condi.setPageNumber(page);
        condi.setPageSize(pageSize);
        List<ScoreGoods> list = this.getList(condi);

        return list;
    }



    @Override
    @Transactional
    public void saveOrModifyScoreGoods(ScoreGoodsRequest scoreGoodsRequest) {
        ScoreGoods condi=new ScoreGoods();
        condi.setGoodsId(scoreGoodsRequest.getGoodsId());

        ScoreGoods insertBean=new ScoreGoods();
        insertBean.setGoodsId(scoreGoodsRequest.getGoodsId());
        insertBean.setGoodsName(scoreGoodsRequest.getGoodsName());
        insertBean.setQrImgPath(scoreGoodsRequest.getQrImgPath());
        insertBean.setScore(scoreGoodsRequest.getScore());
        insertBean.setCostPrice(scoreGoodsRequest.getCostPrice());
        insertBean.setGoodsCurrentPrice(scoreGoodsRequest.getGoodsCurrentPrice());
        insertBean.setCreateTime(new Date());
        insertBean.setUpdateTime(new Date());

        ScoreGoods bean=this.getByCondition(condi);
        if(bean==null){
            this.insert(insertBean);
        }else{
            this.updateByCondition(insertBean,condi);
        }
    }


}