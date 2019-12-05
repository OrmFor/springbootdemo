package com.jlkj.web.shopnew.bean;


import com.jlkj.web.shopnew.enums.EnumNoticeNid;
import com.jlkj.web.shopnew.enums.EnumNoticeType;

import java.util.HashMap;

public class SmsHandeSendBean {

    EnumNoticeNid enumNoticeNid;
    EnumNoticeType enumNoticeType;
    String sentUser;
    String receiveUser;
    String receiveAddr;
    HashMap<String, Object> sendData;
    String code;
    String ip;
    Boolean validate;

    public EnumNoticeNid getEnumNoticeNid() {
        return enumNoticeNid;
    }

    public void setEnumNoticeNid(EnumNoticeNid enumNoticeNid) {
        this.enumNoticeNid = enumNoticeNid;
    }

    public EnumNoticeType getEnumNoticeType() {
        return enumNoticeType;
    }

    public void setEnumNoticeType(EnumNoticeType enumNoticeType) {
        this.enumNoticeType = enumNoticeType;
    }

    public String getSentUser() {
        return sentUser;
    }

    public void setSentUser(String sentUser) {
        this.sentUser = sentUser;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getReceiveAddr() {
        return receiveAddr;
    }

    public void setReceiveAddr(String receiveAddr) {
        this.receiveAddr = receiveAddr;
    }

    public HashMap<String, Object> getSendData() {
        return sendData;
    }

    public void setSendData(HashMap<String, Object> sendData) {
        this.sendData = sendData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }
}
