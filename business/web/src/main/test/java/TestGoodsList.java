package java;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.Application;
import com.jlkj.web.shopnew.constant.Constant;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestGoodsList {

    private static final String url = Constant.HTTP_URL + "/third/goods/list";


    @Autowired
    private IScoreGoods scoreGoodsSevice;

    @Test
    public void test(){
        CloseableHttpClient httpclient = HttpUtils.createWrapClient(Constant.HTTP_URL);
        try {

            for(int i = 1 ;i <= 100 ; i++) {
                HttpPost httppost = new HttpPost(url);
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
    }
}
