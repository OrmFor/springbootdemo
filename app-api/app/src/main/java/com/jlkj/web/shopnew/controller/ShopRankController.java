package com.jlkj.web.shopnew.controller;

import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dto.ShopRankDto;
import com.jlkj.web.shopnew.service.IGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/1.0")
public class ShopRankController extends BaseController {

    @Autowired
    private IGoods goodsService;


    @RequestMapping(value = "/shopRank" , method = RequestMethod.POST)
    public ResultCode shopRank(){
        List<ShopRankDto> result = goodsService.shopRank();
        return new ResultCode(StatusCode.SUCCESS,result);
    }


}
