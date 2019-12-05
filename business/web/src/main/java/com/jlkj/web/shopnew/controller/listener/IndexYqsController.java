package com.jlkj.web.shopnew.controller.listener;

import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.yidto.request.OrderRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexYqsController {

    @RequestMapping("/yqs")
    public ResultCode index(@RequestBody OrderRequest orderRequest){
        int uid = orderRequest.getUid();
        String index = "hello,积分商城！" + uid;
        return new ResultCode(StatusCode.SUCCESS,index);
    }
}
