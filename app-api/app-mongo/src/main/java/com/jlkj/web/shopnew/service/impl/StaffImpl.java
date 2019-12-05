package com.jlkj.web.shopnew.service.impl;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.google.common.collect.Lists;
import com.jlkj.web.shopnew.constant.Constant;
import com.jlkj.web.shopnew.constant.ConstantDateType;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dao.StaffMapper;
import com.jlkj.web.shopnew.dto.*;
import com.jlkj.web.shopnew.enums.EnumFlagType;
import com.jlkj.web.shopnew.enums.EnumIsShare;
import com.jlkj.web.shopnew.enums.EnumLogType;
import com.jlkj.web.shopnew.enums.EnumStaffType;
import com.jlkj.web.shopnew.exception.BussinessException;
import com.jlkj.web.shopnew.pojo.*;
import com.jlkj.web.shopnew.pojo.mongo.FollowMongo;
import com.jlkj.web.shopnew.pojo.mongo.LogMongo;
import com.jlkj.web.shopnew.request.*;
import com.jlkj.web.shopnew.service.*;
import com.jlkj.web.shopnew.service.mongo.ICustomerMongo;
import com.jlkj.web.shopnew.service.mongo.IFollowMongo;
import com.jlkj.web.shopnew.service.mongo.ILogMongo;
import com.jlkj.web.shopnew.util.DateUtil;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StaffImpl extends BaseServiceImpl<Staff, StaffMapper, Integer> implements IStaff {

    private final static Logger logger = LoggerFactory.getLogger(StaffImpl.class);

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private ICustomerMongo customerMongoService;

    @Autowired
    private ICompany companyService;

    @Autowired
    private ILogMongo logMongoService;

    @Autowired
    private IOrders ordersService;

    @Autowired
    private IFollowMongo followMongoService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ICompanyInvite companyInviteService;


    @Autowired
    private IConversionRate conversionRateService;

    @Autowired
    private IStaff staffService;

    @Autowired
    private IUser userService;

    @Autowired
    private IManagers managersService;

    protected StaffMapper getDao() {
        return staffMapper;
    }

    @Override
    public List<Integer> getCompanyStaff(int uid) {
        int companyId = companyService.getCompanyId(uid);//获得企业 -1 id

        if (companyId == -1) {
            throw new BussinessException(StatusCode.ERROR_NO_COMPANY);
        }

        Staff condiAll = new Staff();
        condiAll.setCompanyId(companyId);
        List<Staff> staffList = this.getList(condiAll);//存在企业的所有员工

        if (staffList == null || staffList.size() == 0) {
            return Lists.newArrayList(uid);//只有自己
        }

        List<Integer> list = Lists.newArrayList();
        for (Staff staff : staffList) {
            list.add(staff.getStaffId());//所有员工
        }

        return list;//返回自己或企业所有员工
    }

    @Override
    public List<Integer> getCompanyStaffNoPartner(int uid) {
        int companyId = companyService.getCompanyId(uid);

        if (companyId == -1) {
            throw new BussinessException(StatusCode.ERROR_NO_COMPANY);
        }

        Staff condiAll = new Staff();
        condiAll.setCompanyId(companyId);
        condiAll.and(Expressions.in("role",Lists.newArrayList(EnumStaffType.STAFF.getCode(),EnumStaffType.BOSS.getCode())));

        List<Staff> staffList = this.getList(condiAll);


        if (staffList == null || staffList.size() == 0) {
            return Lists.newArrayList(uid);
        }

        List<Integer> list = Lists.newArrayList();
        for (Staff staff : staffList) {
            list.add(staff.getStaffId());
        }

        return list;
    }


    @Override
    public List<Integer> getStaffUids(int uid) {
        int companyId = companyService.getCompanyId(uid);

        if (companyId == -1) {
            return Lists.newArrayList(uid);
        }

        Staff condiAll = new Staff();
        condiAll.setCompanyId(companyId);
        List<Staff> staffList = this.getList(condiAll);

        if (staffList == null || staffList.size() == 0) {
            return Lists.newArrayList(uid);
        }

        List<Integer> list = Lists.newArrayList();
        for (Staff staff : staffList) {
            list.add(staff.getStaffId());
        }

        return list;
    }

    @Override
    public List<Integer> getStaffUidsNoPartner(int uid) {
        int companyId = companyService.getCompanyId(uid);

        if (companyId == -1) {
            return Lists.newArrayList(uid);
        }

        Staff condiAll = new Staff();
        condiAll.setCompanyId(companyId);
        condiAll.and(Expressions.in("role",Lists.newArrayList(EnumStaffType.STAFF.getCode(),EnumStaffType.BOSS.getCode())));
        List<Staff> staffList = this.getList(condiAll);

        if (staffList == null || staffList.size() == 0) {
            return Lists.newArrayList(uid);
        }

        List<Integer> list = Lists.newArrayList();
        for (Staff staff : staffList) {
            list.add(staff.getStaffId());
        }

        return list;
    }

    @Override
    public List<StaffDto> staffSort(GetStaffSortRequest getStaffSortRequest) {
        DateDto dateDto = DateUtil.getDayByType(getStaffSortRequest.getDateType(),
                getStaffSortRequest.getStartDay(), getStaffSortRequest.getEndDay());

        Company bean = companyId(getStaffSortRequest.getUid());

        if (bean == null) {
            throw new BussinessException(StatusCode.ERROR_NO_COMPANY);
        }

        Staff condi = new Staff();
        condi.setCompanyId(bean.getId());
        condi.and(Expressions.in("role",Lists.newArrayList(EnumStaffType.STAFF.getCode(),EnumStaffType.BOSS.getCode())));
        List<Staff> staffList = getList(condi);
        List<StaffDto> list = new ArrayList<>();
        for (Staff staff : staffList) {
            int countType = getStaffSortRequest.getCountType();
            int count = 0;
            if (countType == 0) {
                //按照客户数
               /* count = conversionRateService.getCustomerByType(getStaffSortRequest,
                        dateDto, "customerTime");*/

                count = conversionRateService.getCustomerByType(getStaffSortRequest,
                        dateDto, 2);
            } else if (countType == 1) {
                //按照跟进数
                count = followMongoService.getFollowCount(getStaffSortRequest.getUid(), dateDto);
            } else if (countType == 2) {
                //按照订单数
                count = ordersService.getOrdersCount(getStaffSortRequest.getUid(), dateDto);
            } else {
                //按照转化率
                int intentionalCount = conversionRateService.getCustomerByType(getStaffSortRequest,
                        dateDto, 1);
                int customerCount = conversionRateService.getCustomerByType(getStaffSortRequest,
                        dateDto, 2);

                count = intentionalCount == 0 ? 0 :
                        new BigDecimal(customerCount)
                                .divide(new BigDecimal(intentionalCount))
                                .setScale(2, RoundingMode.HALF_UP).intValue();


            }

            StaffDto dto = new StaffDto();
            dto.setStaffId(staff.getStaffId());
            dto.setStaffName(staff.getStaffName());
            dto.setStaffPic(staff.getStaffPic());
            dto.setStaffPosition(staff.getStaffPosition());
            dto.setCount(count);
            list.add(dto);
        }

        Collections.sort(list, new Comparator<StaffDto>() {
            public int compare(StaffDto o1, StaffDto o2) {
                return o2.getCount() - o1.getCount();
            }
        });
        return list;
    }

    //GetStaffAnalysisRequest 老板id     StatiscRequest 员工id/老板id
    //1 客户总数：员工的客户总数/企业总客户数*100
    //2 跟进客户：员工的跟进客户数/企业总跟进客户数*100
    //3 昨日新增客户：员工的昨日新增客户数/企业昨日的跟进客户数*100
    //4 客户咨询数：员工昨日的咨询数/企业昨日总的咨询数*100
    //5 客户转化率：员工的客户转化率*100
    //6 成交订单：员工的成交订单/企业的成交订单*100
    @Override
    public List<StaffAnalysisDto> staffAnalysis(GetStaffAnalysisRequest getStaffAnalysisRequest) {
        Company bean = companyId(getStaffAnalysisRequest.getUid());//获得boss的企业
        if (bean == null) {
            throw new BussinessException(StatusCode.ERROR_NO_COMPANY);//用户没有企业，无需分析
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Staff condi = new Staff();
        condi.setCompanyId(bean.getId());
        condi.and(Expressions.in("role",Lists.newArrayList(EnumStaffType.STAFF.getCode(),EnumStaffType.BOSS.getCode())));//普通员工 老板员工自己
        List<Staff> staffList = getList(condi);//分析自己或自己企业所有

        StatiscRequest statiscRequest = new StatiscRequest();
        statiscRequest.setBoss(true);//标识老板
        statiscRequest.setUid(getStaffAnalysisRequest.getUid());//boss
        statiscRequest.setDateType(ConstantDateType.ALL_DAY);//累计
        DateDto dateDto = new DateDto();
        Date date = DateUtil.getMongoDate();//new Date
        dateDto.setEndDay(DateUtil.dateToISODate(format.format(date)));

        int customerAllCount = getCustomerAllCount(statiscRequest, dateDto);//获得累计公司客户总数
        int followAllCount = this.getUserTypeFollowCount(statiscRequest, dateDto);//获得累计公司跟进总数
        int ordersAllCount = ordersService.getOrdersCount(statiscRequest, dateDto);//获得累计登陆用户相关的商户的订单总数

        statiscRequest.setDateType(ConstantDateType.YESTERDAY);//昨天
        dateDto = DateUtil.getDayByType(ConstantDateType.YESTERDAY, date, date);//返回昨天的日期Dto(YESTERDAY,new Date,new Date)
        int yesterdayCustomerAddAllCount = this.getCustomerAllCount(statiscRequest, dateDto);//获得昨天公司客户总数
        int yseterdayCustomerChatAllCount = logMongoService.getChatCount(statiscRequest, dateDto);//统计昨天公司咨询总数

        List<StaffAnalysisDto> list = new ArrayList<>();//员工分析Dto
        for (Staff staff : staffList) {//公司各员工进行统计
            StatiscRequest statiscRequestStaff = new StatiscRequest();
            statiscRequestStaff.setBoss(false);//非老板
            statiscRequestStaff.setUid(staff.getStaffId());//设置员工用户id
            statiscRequestStaff.setDateType(ConstantDateType.ALL_DAY);//累计
            DateDto dateDtoStaff = new DateDto();
            dateDtoStaff.setEndDay(date);

            int customerStaffCount = getCustomerStaffCount(statiscRequestStaff, dateDtoStaff);//获得累计员工客户统计数
            int followStaffCount = this.getUserTypeFollowCount(statiscRequestStaff, dateDtoStaff);//获得累计员工客户跟进统计数
            int ordersStaffCount = ordersService.getOrdersCount(statiscRequestStaff, dateDtoStaff);//获得累计员工订单总数

            statiscRequest.setDateType(ConstantDateType.YESTERDAY);//昨天
            dateDtoStaff = DateUtil.getDayByType(ConstantDateType.YESTERDAY, date, date);//(YESTERDAY,new Date,new Date)
            int yesterdayCustomerAddStaffCount = this.getCustomerAllCount(statiscRequestStaff, dateDtoStaff);;//统计该员工昨天相关的客户数
            int yseterdayCustomerChatStaffCount = logMongoService.getChatCount(statiscRequestStaff,
                    dateDtoStaff);//统计该员工昨天相关的咨询数
            //员工分析Dto，封装统计信息和员工信息
            StaffAnalysisDto dto = new StaffAnalysisDto();
            //客户总数占比（累计员工客户统计数/累计公司客户总数）
            BigDecimal customerScale = customerAllCount == 0 ? BigDecimal.ZERO :
                    new BigDecimal(customerStaffCount).divide(
                            new BigDecimal(customerAllCount)).setScale(2, RoundingMode.HALF_UP);//保留两位小数，四舍五入
            dto.setCustomerScale(customerScale);
            //跟进客户数占比（累计员工客户跟进统计数/累计公司跟进总数）
            BigDecimal followScale = followAllCount == 0 ? BigDecimal.ZERO :
                    new BigDecimal(followStaffCount).divide(
                            new BigDecimal(followStaffCount)).setScale(2, RoundingMode.HALF_UP);
            dto.setFollowScale(followScale);
            //昨日客户咨询数占比(员工昨天相关的咨询数/昨天公司咨询总数)
            BigDecimal chatScale = yseterdayCustomerChatAllCount == 0 ? BigDecimal.ZERO :
                    new BigDecimal(yseterdayCustomerChatStaffCount).divide(
                            new BigDecimal(yseterdayCustomerChatAllCount)
                                    .setScale(2, RoundingMode.HALF_UP));
            dto.setChatScale(chatScale);
            //昨日新增客户数占比(昨天相关的客户数/昨天公司客户总数)
            BigDecimal customerAddScale = yesterdayCustomerAddAllCount == 0 ? BigDecimal.ZERO :
                    new BigDecimal(yesterdayCustomerAddStaffCount).divide(
                            new BigDecimal(yesterdayCustomerAddAllCount)
                                    .setScale(2, RoundingMode.HALF_UP));
            dto.setCustomerAddScale(customerAddScale);
            //成交订单数占比(累计员工订单总数/今日公司订单总数)
            BigDecimal ordersScale = ordersAllCount == 0 ? BigDecimal.ZERO :
                    new BigDecimal(ordersStaffCount).divide(
                            new BigDecimal(ordersAllCount)
                    ).setScale(2, RoundingMode.HALF_UP);
            dto.setOrdersScale(ordersScale);
            //总占比=客户总数占比+跟进客户数占比+客户咨询数占比+新增客户数占比+成交订单数占比
            BigDecimal allScale = customerScale.add(followScale)
                    .add(chatScale).add(customerAddScale).add(ordersScale);
            dto.setAllScale(allScale);

            //转换率
            GetConversionRateRequest rateRequest = new GetConversionRateRequest();
            rateRequest.setBoss(false);
            rateRequest.setUid(staff.getStaffId());
            rateRequest.setDateType(ConstantDateType.ALL_DAY);
            int intentional = conversionRateService.getAllOfCustomer(rateRequest, "intentionalTime");//统计意向客户归属记录,(转换率请求对象,客户时间类型)
            int customer = conversionRateService.getAllOfCustomer(rateRequest, "customerTime");//统计真实客户归属记录

            BigDecimal rateScale = intentional == 0 ? BigDecimal.ZERO :
                    new BigDecimal(customer).divide(new BigDecimal(intentional))
                            .setScale(2, RoundingMode.HALF_UP);
            dto.setRateScale(rateScale);//设置客户转换率

            dto.setStaffId(staff.getStaffId());
            dto.setStaffName(staff.getStaffName());
            dto.setStaffPic(staff.getStaffPic());
            dto.setStaffPosition(staff.getStaffPosition());
            list.add(dto);
        }

        Collections.sort(list, new Comparator<StaffAnalysisDto>() {
            public int compare(StaffAnalysisDto o1, StaffAnalysisDto o2) {

                return o2.getAllScale().compareTo(o1.getAllScale());
            }
        });

        return list;
    }

    @Override
    public boolean deleteStaff(DeleteStaffRequest deleteStaffRequest) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("belongUid").is(deleteStaffRequest.getUid())
        );

        Query query = Query.query(criteria);
        FollowMongo bean = this.mongoTemplate.findOne(query, FollowMongo.class);

        if (bean != null) {
            Update update = Update.update("belongUid", deleteStaffRequest.getBossId());
            UpdateResult result = this.mongoTemplate.updateMulti(query, update, FollowMongo.class);
        }

        //删除staff
        Staff staff = new Staff();
        staff.setStaffId(deleteStaffRequest.getUid());
        this.delete(staff);

        CompanyInvite condi = new CompanyInvite();
        condi.setUid(deleteStaffRequest.getUid());
        CompanyInvite udpate = new CompanyInvite();
        udpate.setFlag(2);

        companyInviteService.updateByCondition(udpate, condi);

        //足迹全部归老板
        try {
            Criteria criteria1 = new Criteria().andOperator(
                    Criteria.where("belongUid").is(deleteStaffRequest.getUid())
            );

            Query query1 = Query.query(criteria1);
            List<LogMongo> logMongo = this.mongoTemplate.find(query1, LogMongo.class);
            if (logMongo != null && logMongo.size() > 0) {
                Update update = Update.update("operationId", deleteStaffRequest.getBossId());
                UpdateResult result = this.mongoTemplate.updateMulti(query1, update, LogMongo.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return true;
    }
    //获得boss的企业
    public Company companyId(int bossId) {
        Company condi = new Company();
        condi.setBossId(bossId);
        return companyService.getByCondition(condi);
    }

    private int getCustomerAllCount(StatiscRequest statiscRequest, DateDto dateDto) {
        int customerAllCount = logMongoService.
                getUserTypeCount(statiscRequest, dateDto, "customerTime");//根据日期时间 公司真实客户数统计
        return customerAllCount;
    }

    private int getCustomerStaffCount(StatiscRequest statiscRequest, DateDto dateDto) {
        int customerAllCount = logMongoService.
                getUserTypeCount(statiscRequest, dateDto, "customerTime");
        return customerAllCount;
    }

    //获得某类型客户的跟进数
    public int getUserTypeFollowCount(StatiscRequest statiscRequest, DateDto dateDto) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("userType").in(Lists.newArrayList(2))//0潜在客户 1意向客户 2 真实客户
        );
        if (ConstantDateType.ALL_DAY.equals(statiscRequest.getDateType())) {//累计
            criteria.and("updateTime").lte(dateDto.getEndDay());
        } else {
            criteria.andOperator(
                    Criteria.where("updateTime").lte(dateDto.getEndDay()),
                    Criteria.where("updateTime").gte(dateDto.getStartDay())
            );
        }

        if (statiscRequest.isBoss()) {//统计公司的
            List<Integer> list = this.getCompanyStaff(statiscRequest.getUid());
            criteria.and("belongUid").in(list);
        } else {//统计普通员工个人的
            criteria.and("belongUid").is(statiscRequest.getUid());
        }

        Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group("userType").count().as("num"),//分组情况唯一，无影响
                Aggregation.project("num").andExclude("_id")
        );
        AggregationResults<FollowCountDto> results = mongoTemplate.aggregate(agg, Constant.FOLLOWMONGO, FollowCountDto.class);//相关的真实客户统计数
        List<FollowCountDto> mappedResults = results.getMappedResults();
        return (mappedResults == null || mappedResults.size() == 0) ? 0 : mappedResults.get(0).getNum();
    }

    //删除合伙人
    @Override
    public boolean deletePartner(DeleteStaffRequest deleteStaffRequest) {

        Staff staff = new Staff();
        staff.setStaffId(deleteStaffRequest.getUid());
        staff.setRole(2);//合伙人
        int count=this.delete(staff);

        CompanyInvite condi = new CompanyInvite();//条件对象
        condi.setUid(deleteStaffRequest.getUid());
        CompanyInvite udpate = new CompanyInvite();//更新对象
        udpate.setFlag(2);//废弃邀请记录

        companyInviteService.updateByCondition(udpate, condi);

        if(count!=0){
            return true;
        }else{
            return false;
        }
    }

    //合伙人排行（分享 3 32）
    @Override
    public List<StaffDto> sortPartner(GetStaffSortRequest getStaffSortRequest) {
        DateDto dateDto = DateUtil.getDayByType(getStaffSortRequest.getDateType(),
                getStaffSortRequest.getStartDay(), getStaffSortRequest.getEndDay());

        Company bean = companyId(getStaffSortRequest.getUid());//查询boss的企业

        if (bean == null) {
            throw new BussinessException(StatusCode.ERROR_NO_COMPANY);
        }

        //存在企业，对所有合伙人进行排行
        Staff condi=new Staff();
        condi.setCompanyId(bean.getId());
        condi.setRole(EnumStaffType.PARTNER.getCode());
        List<Staff> staffList=this.getList(condi);
        List<StaffDto> staffDtoList=new ArrayList<>();
        for(Staff staff:staffList){
            StaffDto staffDto=new StaffDto();
            staffDto.setCreateTime(staff.getCreateTime());
            staffDto.setStaffName(staff.getStaffName());
            staffDto.setStaffPic(staff.getStaffPic());
            staffDto.setStaffPosition(staff.getStaffPosition());

            //统计各员工的分享次数
            staffDto.setCount(partnerShareCount(staff.getStaffId(),getStaffSortRequest,dateDto));
            staffDtoList.add(staffDto);
        }

        Collections.sort(staffDtoList, new Comparator<StaffDto>() {
            public int compare(StaffDto o1, StaffDto o2) {
                return o2.getCount() - o1.getCount();//根据统计数排序
            }
        });
        return staffDtoList;
    }

    /**
     * 合伙人分享统计
     * @param uid
     * @param getStaffSortRequest
     * @return
     */
    public int partnerShareCount(int uid,GetStaffSortRequest getStaffSortRequest,DateDto dateDto) {
        Criteria criteria = new Criteria();
        if(uid!=0){
            criteria.and("uid").is(uid);
        }
        if(getStaffSortRequest.getUid()!=0){
            criteria.and("operationId").is(getStaffSortRequest.getUid());
        }

        criteria.and("logType").in(Lists.newArrayList(3,32));

        if (ConstantDateType.ALL_DAY.equals(getStaffSortRequest.getDateType())) {//累计 一端
            criteria.and("createTime").lte(dateDto.getEndDay());
        } else {//非累计
            criteria.and("createTime").lte(dateDto.getEndDay()).gte(dateDto.getStartDay());
        }

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group().count().as("count"),
                Aggregation.project("count").andExclude("_id")
        );
        AggregationResults<StaffDto> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, StaffDto.class);
        List<StaffDto> mappedResults = results!=null?results.getMappedResults():Lists.newArrayList();

        return mappedResults==null||mappedResults.size()==0 ? 0 : mappedResults.get(0).getCount();
    }


    //合伙列表
    public Map<String,Object> partnerLogList(GetStaffSortRequest getStaffSortRequest) {
        DateDto dateDto = DateUtil.getDayByType(getStaffSortRequest.getDateType(),
                getStaffSortRequest.getStartDay(), getStaffSortRequest.getEndDay());
        return partnerListShareCount(getStaffSortRequest,dateDto);
    }

    @Override
    public int getRole(int operationId) {
        Staff staff = new Staff();
        staff.setStaffId(operationId);
        Staff bean = getByCondition(staff);
        return bean == null ? 0 : bean.getRole();
    }

    //合伙列表分享统计及相关信息
    public Map<String,Object> partnerListShareCount(GetStaffSortRequest getStaffSortRequest,DateDto dateDto) {

        int compnayId = companyService.getCompanyId(getStaffSortRequest.getUid());
        if(compnayId == -1){
            throw new BussinessException(StatusCode.ERROR_NO_COMPANY);
        }

        Criteria criteria = new Criteria();
        criteria.and("uid").is(getStaffSortRequest.getUid()); //自己
        criteria.and("logType").in(Lists.newArrayList(3,32));
        criteria.and("operationRole").is(EnumStaffType.PARTNER.getCode());//合伙人
        criteria.and("companyId").is(compnayId);

        if (ConstantDateType.ALL_DAY.equals(getStaffSortRequest.getDateType())) {//累计 一端
            criteria.and("createTime").lte(dateDto.getEndDay());
        } else {//非累计
            criteria.and("createTime").lte(dateDto.getEndDay()).gte(dateDto.getStartDay());
        }

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group().count().as("num"),
                Aggregation.project().andExclude("_id")
        );
        AggregationResults<LogDto> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogDto.class);
        List<LogDto> mappedResults = results.getMappedResults();
        int count=(int)(mappedResults.size()==0?0:mappedResults.get(0).getNum());
        List<LogDto> logDtoList=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        if(count==0) {
            map.put("totalCount", count);
            map.put("list", logDtoList);
            return  map;
        }

        agg=Aggregation.newAggregation(
                Aggregation.match(criteria)
        );
        AggregationResults<LogMongo> logMongoResults= mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        List<LogMongo> mappedLogDtoResults = logMongoResults!=null?logMongoResults.getMappedResults():Lists.newArrayList();
        logDtoList=new ArrayList<>();
        for (LogMongo logMongo:mappedLogDtoResults){
            LogDto logDto=new LogDto();
            logDto.setCreateTime(logMongo.getCreateTime().getTime());
            logDto.setGoodName(logMongo.getGoodName()== null ? "" : logMongo.getGoodName());
            logDto.setUid(logMongo.getUid());
            logDtoList.add(logDto);
        }

        Collections.sort(logDtoList, new Comparator<LogDto>() {
            public int compare(LogDto o1, LogDto o2) {
                return (int)(o2.getCreateTime()-o1.getCreateTime());//自定义排序
            }
        });

        map.put("totalCount",count);
        map.put("list",logDtoList);
        return map;
    }

    //员工统计视图
    public List<StaffCountDto> staffCountView(StaffCountRequest staffCountRequest) {
        int boss=staffCountRequest.getUid();
        Company condi=new Company();
        condi.setBossId(boss);

        Company company=companyService.getByCondition(condi);
        logger.info("[==员工统计 所在公司==]"+company);
        Staff staffCondi=new Staff();
        staffCondi.setCompanyId(company.getId());
        List<Staff> list = staffService.getList(staffCondi);
        List<StaffCountDto> staffCountDtoList=new ArrayList<>();

        //统计各员工
        for (Staff staff:list){
            staffCountRequest.setStaffId(staff.getStaffId());
            int followNum=getFollowNumByStaffId(staffCountRequest);
            int shareNum=getShareNumByStaffId(staffCountRequest);
            int ordersNum=getOrdersNumByStaffId(staffCountRequest);
            int chatNum=getChatNumByStaffId(staffCountRequest);

            StaffCountDto staffCountDto=new StaffCountDto();
            staffCountDto.setStaffId(staff.getStaffId());
            staffCountDto.setStaffName(staff.getStaffName());
            staffCountDto.setFollowNum(followNum);
            staffCountDto.setShareNum(shareNum);
            staffCountDto.setOrdersNum(ordersNum);
            staffCountDto.setChatNum(chatNum);

            staffCountDtoList.add(staffCountDto);

            logger.info("[==]"+staff.getStaffId()+" "+staff.getStaffName()+"[跟进数]"+followNum+"[分享数]"
                    +shareNum+"[聊天数]"+chatNum+"[订单数]"+ordersNum);

        }

        return staffCountDtoList;
    }

    //统计指定员工的跟进数
    public int getFollowNumByStaffId(StaffCountRequest staffCountRequest){
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("belongUid").is(staffCountRequest.getStaffId())
        );

        Map<String, Date> date =
                DateUtil.getDateByType(staffCountRequest.getDateType(), staffCountRequest.getStartDay(), staffCountRequest.getEndDay());
        criteria.and("updateTime").gte(date.get("startDay")).lte(date.get("endDay"));

        Query query = Query.query(criteria);
        Long totalCount = mongoTemplate.count(query, FollowMongo.class);//查询总记录数
        return totalCount.intValue();
    }

    //统计指定员工的分享次数
    public int getShareNumByStaffId(StaffCountRequest staffCountRequest){

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("uid").is(staffCountRequest.getStaffId())
        );

        Map<String, Date> date =
                DateUtil.getDateByType(staffCountRequest.getDateType(), staffCountRequest.getStartDay(), staffCountRequest.getEndDay());
        criteria.and("updateTime").gte(date.get("startDay")).lte(date.get("endDay"));
        criteria.and("logType").in(Lists.newArrayList(EnumLogType.LOG_SHOP.getType(),EnumLogType.LOG_SHOP_SHARE.getType()));

        Query query = Query.query(criteria);
        Long totalCount = mongoTemplate.count(query, LogMongo.class);//查询总记录数
        return totalCount.intValue();
    }

    //统计指定员工的订单数
    public int getOrdersNumByStaffId(StaffCountRequest staffCountRequest){
        DateDto dateDto = DateUtil.getDayByType(staffCountRequest.getDateType(),
                staffCountRequest.getStartDay(), staffCountRequest.getEndDay());

        GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId=new GetLogListByLogTypeAndStaffId();
        getLogListByLogTypeAndStaffId.setStaffId(staffCountRequest.getStaffId());
        getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartDay());
        getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndDay());

        int count= ordersService.getOrderCountByStaffId(getLogListByLogTypeAndStaffId);
        return count;
    }

    //统计指定员工的聊天数
    public int getChatNumByStaffId(StaffCountRequest staffCountRequest){
        int totalCount=0;

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").is(staffCountRequest.getStaffId())
        );

        Map<String, Date> date =
                DateUtil.getDateByType(staffCountRequest.getDateType(), staffCountRequest.getStartDay(), staffCountRequest.getEndDay());
        criteria.and("updateTime").gte(date.get("startDay")).lte(date.get("endDay"));
        criteria.and("logType").is(EnumLogType.LOG_CHAT.getType());

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria)
        );

        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        if(results==null||results.getMappedResults()==null||results.getMappedResults().size()==0){
            return 0;
        }

        totalCount=results.getMappedResults().size();
        return totalCount;
    }

    //获取员工头像相关信息
    public StaffDto getStaffById(int staffId){
        Staff condi=new Staff();
        condi.setStaffId(staffId);

        Staff staff=staffService.getByCondition(condi);
        StaffDto staffDto=new StaffDto();
        staffDto.setStaffName(staff.getStaffName());
        staffDto.setStaffPic(staff.getStaffPic());

        return staffDto;
    }

    //boss: 员工排行 新
    public List<StaffDto> staffSortNew(GetStaffSortRequest getStaffSortRequest) {
        DateDto dateDto = DateUtil.getDayByType(getStaffSortRequest.getDateType(),
                getStaffSortRequest.getStartDay(), getStaffSortRequest.getEndDay());//处理后的日期时间

        Company bean = companyId(getStaffSortRequest.getUid());//获取boss的企业

        if (bean == null) {
            throw new BussinessException(StatusCode.ERROR_NO_COMPANY);//企业不存在，无需统计
        }

        int mgId=managersService.getMgIdByUid(bean.getBossId());//获取boss的商户id

        Staff condi = new Staff();
        condi.setCompanyId(bean.getId());
        condi.and(Expressions.in("role",Lists.newArrayList(EnumStaffType.STAFF.getCode(),EnumStaffType.BOSS.getCode())));
        List<Staff> staffList = getList(condi);//获取该企业所有员工
        List<StaffDto> list = new ArrayList<>();
        for (Staff staff : staffList) {
            int countType = getStaffSortRequest.getCountType();
            int count = 0;
            if (countType == 0) {
                //按照客户数
                count = conversionRateService.getCustomerByType(getStaffSortRequest,
                        dateDto, 2);
            } else if (countType == 1) {
                //按照跟进数
                count = followMongoService.getFollowCount(staff.getStaffId(), dateDto);
            } else if (countType == 2) {
                StatiscRequest statiscRequest=new StatiscRequest();
                statiscRequest.setUid(staff.getStaffId());
                statiscRequest.setMgId(mgId);

                //按照订单数
                count = ordersService.getOrdersStaffCountNew(statiscRequest, dateDto);

            }else if(countType==3){
                //按照分享数
                count= logMongoService.getShareCount(staff.getStaffId(),dateDto);
            } else {
                //按照聊天数
                count = logMongoService.getChatCount(staff.getStaffId(),dateDto);

            }

            StaffDto dto = new StaffDto();
            dto.setStaffId(staff.getStaffId());
            dto.setStaffName(staff.getStaffName());
            dto.setStaffPic(staff.getStaffPic());
            dto.setStaffPosition(staff.getStaffPosition());
            dto.setCount(count);//统计数
            list.add(dto);
        }

        Collections.sort(list, new Comparator<StaffDto>() {
            public int compare(StaffDto o1, StaffDto o2) {
                return o2.getCount() - o1.getCount();
            }
        });
        return list;
    }

    //1 客户总数：员工的客户总数/企业总客户数*100
    //2 跟进客户：员工的跟进客户数/企业总跟进客户数*100
    //3 昨日新增客户：员工的昨日新增客户数/企业昨日的跟进客户数*100
    //4 客户咨询数：员工昨日的咨询数/企业昨日总的咨询数*100
    //5 客户转化率：员工的客户转化率*100
    //6 成交订单：员工的成交订单/企业的成交订单*100
    @Override
    public List<StaffAnalysisDto> staffAnalysisNew(GetStaffAnalysisRequest getStaffAnalysisRequest) {
        Company bean = companyId(getStaffAnalysisRequest.getUid());//获得boss的企业
        if (bean == null) {
            throw new BussinessException(StatusCode.ERROR_NO_COMPANY);//用户没有企业，无需分析
        }

        int mgId=managersService.getMgIdByUid(bean.getBossId());//获取boss的商户id

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Staff condi = new Staff();
        condi.setCompanyId(bean.getId());
        condi.and(Expressions.in("role",Lists.newArrayList(EnumStaffType.STAFF.getCode(),EnumStaffType.BOSS.getCode())));//普通员工 老板员工自己
        List<Staff> staffList = getList(condi);//分析自己或自己企业所有

        StatiscRequest statiscRequest = new StatiscRequest();
        statiscRequest.setBoss(true);//标识老板
        statiscRequest.setUid(getStaffAnalysisRequest.getUid());//boss
        statiscRequest.setDateType(ConstantDateType.ALL_DAY);//累计
        DateDto dateDto = new DateDto();
        Date date = DateUtil.getMongoDate();//new Date
        dateDto.setEndDay(DateUtil.dateToISODate(format.format(date)));

        int customerAllCount = getCustomerAllCount(statiscRequest, dateDto);//获得累计公司客户总数
        int followAllCount = this.getUserTypeFollowCount(statiscRequest, dateDto);//获得累计公司跟进总数
        int ordersAllCount = ordersService.getOrdersCountNew(statiscRequest, dateDto);//获得累计boss商户的订单总数

        statiscRequest.setDateType(ConstantDateType.YESTERDAY);//昨天
        dateDto = DateUtil.getDayByType(ConstantDateType.YESTERDAY, date, date);//返回昨天的日期Dto(YESTERDAY,new Date,new Date)
        int yesterdayCustomerAddAllCount = this.getCustomerAllCount(statiscRequest, dateDto);//获得昨天公司客户总数
        int yseterdayCustomerChatAllCount = logMongoService.getChatCount(statiscRequest, dateDto);//统计昨天公司咨询总数

        List<StaffAnalysisDto> list = new ArrayList<>();//员工分析Dto
        for (Staff staff : staffList) {//公司各员工进行统计
            StatiscRequest statiscRequestStaff = new StatiscRequest();
            statiscRequestStaff.setBoss(false);//非老板
            statiscRequestStaff.setUid(staff.getStaffId());//设置员工用户id
            statiscRequestStaff.setDateType(ConstantDateType.ALL_DAY);//累计
            statiscRequestStaff.setMgId(mgId);
            DateDto dateDtoStaff = new DateDto();
            dateDtoStaff.setEndDay(date);

            int customerStaffCount = getCustomerStaffCount(statiscRequestStaff, dateDtoStaff);//获得累计员工客户统计数
            int followStaffCount = this.getUserTypeFollowCount(statiscRequestStaff, dateDtoStaff);//获得累计员工客户跟进统计数
            int ordersStaffCount = ordersService.getOrdersCountNew(statiscRequestStaff, dateDtoStaff);//获得(累计)员工邀请订单总数

            statiscRequest.setDateType(ConstantDateType.YESTERDAY);//昨天
            dateDtoStaff = DateUtil.getDayByType(ConstantDateType.YESTERDAY, date, date);//(YESTERDAY,new Date,new Date)
            int yesterdayCustomerAddStaffCount = this.getCustomerAllCount(statiscRequestStaff, dateDtoStaff);//统计该员工昨天相关的客户数
            int yseterdayCustomerChatStaffCount = logMongoService.getChatCount(statiscRequestStaff,
                    dateDtoStaff);//统计该员工昨天相关的咨询数

            //员工分析Dto，封装统计信息和员工信息
            StaffAnalysisDto dto = new StaffAnalysisDto();
            //客户总数占比（累计员工客户统计数/累计公司客户总数）
            BigDecimal customerScale = customerAllCount == 0 ? BigDecimal.ZERO :
                    new BigDecimal(customerStaffCount).divide(
                            new BigDecimal(customerAllCount),2,RoundingMode.HALF_UP);//保留两位小数，四舍五入
            dto.setCustomerScale(customerScale);

            //跟进客户数占比（累计员工客户跟进统计数/累计公司跟进总数）
            BigDecimal followScale = followAllCount == 0 ? BigDecimal.ZERO :
                    new BigDecimal(followStaffCount).divide(
                            new BigDecimal(followAllCount),2,RoundingMode.HALF_UP);
            dto.setFollowScale(followScale);

            //昨日客户咨询数占比(员工昨天相关的咨询数/昨天公司咨询总数)
            BigDecimal chatScale = yseterdayCustomerChatAllCount == 0 ? BigDecimal.ZERO :
                    new BigDecimal(yseterdayCustomerChatStaffCount).divide(
                            new BigDecimal(yseterdayCustomerChatAllCount),2,RoundingMode.HALF_UP
                    );
            dto.setChatScale(chatScale);

            //昨日新增客户数占比(昨天相关的客户数/昨天公司客户总数)
            BigDecimal customerAddScale = yesterdayCustomerAddAllCount == 0 ? BigDecimal.ZERO :
                    new BigDecimal(yesterdayCustomerAddStaffCount).divide(
                            new BigDecimal(yesterdayCustomerAddAllCount),2,RoundingMode.HALF_UP
                    );
            dto.setCustomerAddScale(customerAddScale);

            //成交订单数占比(累计员工订单总数/今日公司订单总数)
            BigDecimal ordersScale = ordersAllCount == 0 ? BigDecimal.ZERO :
                    new BigDecimal(ordersStaffCount).divide(
                            new BigDecimal(ordersAllCount),2,RoundingMode.HALF_UP
                    );
            dto.setOrdersScale(ordersScale);
            System.out.println(ordersScale);

            //总占比=客户总数占比+跟进客户数占比+客户咨询数占比+新增客户数占比+成交订单数占比
            BigDecimal allScale = customerScale.add(followScale)
                    .add(chatScale).add(customerAddScale).add(ordersScale);
            dto.setAllScale(allScale);

            //转换率
            GetConversionRateRequest rateRequest = new GetConversionRateRequest();
            rateRequest.setBoss(false);
            rateRequest.setUid(staff.getStaffId());
            rateRequest.setDateType(ConstantDateType.ALL_DAY);
            int intentional = conversionRateService.getAllOfCustomer(rateRequest, "intentionalTime");//统计意向客户归属记录,(转换率请求对象,客户时间类型)
            int customer = conversionRateService.getAllOfCustomer(rateRequest, "customerTime");//统计真实客户归属记录

            BigDecimal rateScale = intentional == 0 ? BigDecimal.ZERO :
                    new BigDecimal(customer).divide(new BigDecimal(intentional),
                            2,RoundingMode.HALF_UP);
            dto.setRateScale(rateScale);//设置客户转换率

            dto.setStaffId(staff.getStaffId());
            dto.setStaffName(staff.getStaffName());
            dto.setStaffPic(staff.getStaffPic());
            dto.setStaffPosition(staff.getStaffPosition());
            list.add(dto);
        }

        Collections.sort(list, new Comparator<StaffAnalysisDto>() {
            public int compare(StaffAnalysisDto o1, StaffAnalysisDto o2) {

                return o2.getAllScale().compareTo(o1.getAllScale());
            }
        });

        return list;
    }

    //合伙人"我"分享链接足迹
    public Map<String,Object> partnerShareUrlCount(GetStaffSortRequest getStaffSortRequest){
        int page = getStaffSortRequest.getPage() == 0 ? 1 : getStaffSortRequest.getPage();
        int pageSize = getStaffSortRequest.getPageSize() == 0 ? 10 : getStaffSortRequest.getPageSize();
        int totalCount=0;

        Map<String, Object> map = new HashMap<>();

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").is(getStaffSortRequest.getUid())
        );

        criteria.and("isShare").is(EnumIsShare.SHARE_URL_CLICKED.getCode());
        criteria.and("operationRole").is(EnumStaffType.PARTNER.getCode());

        //某客户某种行为的最新记录
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","logType").max("updateTime").as("updateTime")
        );

        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        if(results==null||results.getMappedResults()==null||results.getMappedResults().size()==0){
            map.put("totalCount",totalCount);
            map.put("list",Lists.newArrayList());
            return map;
        }
        totalCount=results.getMappedResults().size();//聚合统计数

        agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","logType").max("updateTime").as("updateTime")
                        .first("userName").as("userName")
                        .first("operationName").as("operationName")
                        .first("num").as("num")
                        .first("userImg").as("userImg")
                        .first("followNum").as("followNum")
                        .first("lastFollowTime").as("lastFollowTime")
                        .first("goodName").as("goodName"),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.skip((page-1)*pageSize),
                Aggregation.limit(pageSize)
        );

        results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        List<LogMongo> mappedResults = results.getMappedResults();
        map.put("list", mappedResults);
        map.put("totalCount",totalCount);

        return map;
    }

    @Override
    public int getCountSubAccountByBossId(int bossId) {
        int companyId = companyService.getCompanyId(bossId);
        Staff condi = new Staff();
        condi.setCompanyId(companyId);
        return this.getCount(condi);
    }

    //获取其它所有员工
    @Override
    public List<Staff> getAllStaffExceptSelf(int staffId,int companyId) {
        List<Staff> list = staffMapper.getAllStaffExceptSelf(staffId,companyId);
        return list;
    }

    //删除之前的员工
    @Override
    public void deleteOriginalStaff(int originalStaffId){
        CompanyInvite condi=new CompanyInvite();
        condi.setUid(originalStaffId);

        CompanyInvite update=new CompanyInvite();
        update.setFlag(EnumFlagType.COMPANY_INVITE_DELETE.getCode());
        companyInviteService.updateByCondition(update,condi);

        Staff condiStaff=new Staff();
        condiStaff.setStaffId(originalStaffId);
        staffService.delete(condiStaff);


        User condi1 = new User();
        condi1.setId(originalStaffId);
        User update1 = new User();
        update1.setType(1);
        update1.setPayTime(0L);
        update1.setParentId(0);
        update1.setSource(1);
        userService.updateByCondition(update1,condi1);

    }

    @Override
    public Staff getStaffByStaffId(int staffId) {
        Staff condi = new Staff();
        condi.setStaffId(staffId);
        return this.getByCondition(condi);
    }
}