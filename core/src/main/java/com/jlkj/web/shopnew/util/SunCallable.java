package com.jlkj.web.shopnew.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class SunCallable implements Callable<List> {

    private int pageIndex;

/*    private List<CityZoomDtoNew> list;

    private String userCode;


    public SunCallable(int pageIndex, List<CityZoomDtoNew> list, String userCode) {
        this.pageIndex = pageIndex;
        this.list = list;
        this.userCode = userCode;
    }*/

    @Override
    public List call() throws Exception {
     /*   System.err.println(String.format("pageIndex:%s size:%s,userCode:%s", pageIndex, list.size(),userCode));
        List result = new ArrayList();
        if (null != list && list.size() > 0) {
            for (CityZoomDtoNew dto : list) {
                dto.setiS(StringUtils.isNotBlank(userCode) &&
                        userCode.equals(dto.getuC())?
                        EnumCityInitStatus.USER_SELF.getStatus() :
                        EnumCityInitStatus.USER.getStatus());


                result.add(dto);
            }
        }
        return result;*/
     return null;
    }
}

