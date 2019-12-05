package com.jlkj.web.shopnew.controller;

import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.pojo.ScoreGoods;
import com.jlkj.web.shopnew.service.IScoreGoods;
import com.jlkj.web.shopnew.yidto.request.GetScoreGoodsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/1.0")
public class ScoreGoodsController extends BaseController {


    @Autowired
    private IScoreGoods scoreGoodsService;


    @RequestMapping("/getScoreGoodsList")
    public ResultCode getScoreGoodsList(@RequestBody GetScoreGoodsRequest getScoreGoodsRequest){
        List<ScoreGoods> list = scoreGoodsService.getScoreGoods(getScoreGoodsRequest);
        return new ResultCode(StatusCode.SUCCESS,list);
    }

}
