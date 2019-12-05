package com.jlkj.web.shopnew.common;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "yqs.http")
@Data
public class HttpUrlConfig {

    private String host;

    private String userId;

}
