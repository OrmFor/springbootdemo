package com.jlkj.web.shopnew.controller;

import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.request.ScoreGoodsRequest;
import com.jlkj.web.shopnew.service.IScoreGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0")
public class GoodsListController {

    @Autowired
    private IScoreGoods scoreGoodsService;

    @RequestMapping("/saveOrModifyScoreGoods")
    public ResultCode saveOrModifyScoreGoods(@RequestBody ScoreGoodsRequest scoreGoodsRequest){
        scoreGoodsService.saveOrModifyScoreGoods(scoreGoodsRequest);
        return new ResultCode(StatusCode.SUCCESS);
    }

}
