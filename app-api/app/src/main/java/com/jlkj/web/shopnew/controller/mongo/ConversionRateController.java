package com.jlkj.web.shopnew.controller.mongo;

import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dto.ConversionRateDto;
import com.jlkj.web.shopnew.request.GetConversionRateRequest;
import com.jlkj.web.shopnew.request.StatiscRequest;
import com.jlkj.web.shopnew.service.IConversionRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/1.0")
public class ConversionRateController extends BaseController {
    @Autowired
    private IConversionRate conversionRateService;

    @RequestMapping(value = "/getConversionRate", method = RequestMethod.POST)
    public ResultCode getConversionRate(@RequestBody StatiscRequest StatiscRequest){
        ConversionRateDto data = this.conversionRateService.getConversionRate(StatiscRequest);
        return new ResultCode(StatusCode.SUCCESS,data);

    }


}
