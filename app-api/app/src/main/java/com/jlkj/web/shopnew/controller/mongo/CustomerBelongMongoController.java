package com.jlkj.web.shopnew.controller.mongo;

import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.pojo.User;
import com.jlkj.web.shopnew.pojo.mongo.CustomerBelongMongo;
import com.jlkj.web.shopnew.pojo.mongo.LogMongo;
import com.jlkj.web.shopnew.request.GetAllUserByTypeRequest;
import com.jlkj.web.shopnew.request.GetUserInfoRequest;
import com.jlkj.web.shopnew.request.ModifyCustomerLabel;
import com.jlkj.web.shopnew.request.ModifyCustomerRequest;
import com.jlkj.web.shopnew.service.IOrders;
import com.jlkj.web.shopnew.service.IUser;
import com.jlkj.web.shopnew.service.mongo.ICustomerBelongMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/1.0")
public class CustomerBelongMongoController extends BaseController {

    @Autowired
    private ICustomerBelongMongo  customerBelongMongoService;

    @Autowired
    private IOrders ordersService;

    @Autowired
    private ICustomerBelongMongo customerBelongMongo;

    @Autowired
    private IUser userService;

    //保存客户记录
    @RequestMapping(value = "/saveCustomer", method = RequestMethod.POST)
    public ResultCode saveCustomer(@RequestBody CustomerBelongMongo bean){
        this.customerBelongMongoService.saveCustomerBelong(bean);
        return new ResultCode(StatusCode.SUCCESS);
    }

    //获取客户详细信息（动态更新）
    @RequestMapping(value = "/getUserInfo" , method = RequestMethod.POST)
    public ResultCode getUserInfo(@RequestBody GetUserInfoRequest getUserInfoRequest){
        User bean = userService.selectByPrimaryKey(getUserInfoRequest.getUid());
        int userType = customerBelongMongo.getUserType(getUserInfoRequest);//获得客户类型
        if(userType != 2){
            int count  = ordersService.getOrders(getUserInfoRequest);//获取潜在/意向客户产生的订单数
            if(count > 0) {
                try {
                    ModifyCustomerRequest modifyCustomerRequest = new ModifyCustomerRequest();
                    modifyCustomerRequest.setBelongUid(getUserInfoRequest.getOperationId());
                    modifyCustomerRequest.setUid(getUserInfoRequest.getUid());
                    modifyCustomerRequest.setUserType(2);
                    customerBelongMongo.modifyCustomer(modifyCustomerRequest);//更新客户相关信息(归属,足迹)
                }catch (Exception e){

                }

                userType = 2;//标记为真实客户
            }

        }
        bean.setUserType(userType);
        return new ResultCode(StatusCode.SUCCESS,bean);
    }

    //获得客户标签
    @RequestMapping(value = "/getUserLabel" , method = RequestMethod.POST)
    public ResultCode getUserLabel(@RequestBody GetUserInfoRequest getUserInfoRequest){
        String label = customerBelongMongo.getUserLabel(getUserInfoRequest);
        return new ResultCode(StatusCode.SUCCESS,label);
    }

    //修改客户类型
    @RequestMapping(value = "/modifyCustomerUserType", method = RequestMethod.POST)
    public ResultCode modifyCustomerUserType(@RequestBody ModifyCustomerRequest bean){
        this.customerBelongMongoService.modifyCustomer(bean);
        return new ResultCode(StatusCode.SUCCESS);
    }

    //修改客户标签
    @RequestMapping(value = "/modifyCustomerLabel", method = RequestMethod.POST)
    public ResultCode modifyCustomerLabel(@RequestBody ModifyCustomerLabel bean){
        this.customerBelongMongoService.modifyCustomerLabel(bean);
        return new ResultCode(StatusCode.SUCCESS);
    }


    //获取某类型的客户各行为数据
    @RequestMapping(value = "/getAllUserByType" , method = RequestMethod.POST)
    public ResultCode getAllUserByType(@RequestBody GetAllUserByTypeRequest getAllUserByTypeRequest ){
//        Map<String,Object> data = customerBelongMongoService.getAllUserByType(getAllUserByTypeRequest);
        Map<String,Object> data = customerBelongMongoService.getAllUserByTypeNew(getAllUserByTypeRequest);
        return new ResultCode(StatusCode.SUCCESS , data);
    }

}
