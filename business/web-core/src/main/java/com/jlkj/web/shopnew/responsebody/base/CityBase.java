package com.jlkj.web.shopnew.responsebody.base;

import java.io.Serializable;
import java.math.BigDecimal;

public class CityBase implements Serializable {

    private String cityName;

    private String cityCode;

    private BigDecimal amount;

    private String currency;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
