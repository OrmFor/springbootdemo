//package com.jlkj.web.shopnew.controller;
//
//import cc.s2m.util.Page;
//import com.google.common.collect.Maps;
//import com.jlkj.web.shopnew.core.ResultCode;
//import com.jlkj.web.shopnew.core.StatusCode;
//import com.jlkj.web.shopnew.pojo.User;
//import com.jlkj.web.shopnew.controller.base.BaseController;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//
//@RequestMapping(value = "/api/1.0")
//@RestController
//public class UsersController extends BaseController {
//    private static final Logger LOGGER = LogManager.getLogger(UsersController.class);
//
//    @Autowired
//    private IUser userService;
//
//
//    @RequestMapping("/getUser")
//    public ResultCode getUser() {
//        User user = userService.selectByUserName("老吴");
//
//        return new ResultCode(StatusCode.SUCCESS, user);
//    }
//
//
//    @RequestMapping("/insertUser")
//    @Transactional(rollbackFor = RuntimeException.class)
//    public ResultCode insertUser() {
//        User user = new User();
//        user.setUserCode("12317567");
//        user.setUserName("老吴1");
//        userService.insert(user);
//		/*if(true) {
//			throw new RuntimeException("ERROR");
//		}*/
//        return new ResultCode(StatusCode.SUCCESS);
//    }
//
//    @RequestMapping(value = "/page", method = RequestMethod.GET)
//    public ResultCode index() {
//        User bean = new User();
//        bean.setUserName("老吴1");
//        bean.setPageNumber(1);
//        bean.setPageSize(1);
//        Page<User> page = userService.getPage(bean, null);
//
//        Map<String, Object> data = Maps.newHashMap();
//        data.put("list", page.getResult());
//        data.put("totalCount", page.getTotalRow());
//        return new ResultCode(StatusCode.SUCCESS, data);
//    }
//}