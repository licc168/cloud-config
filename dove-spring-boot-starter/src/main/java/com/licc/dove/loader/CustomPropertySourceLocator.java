package com.licc.dove.loader;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.web.context.support.StandardServletEnvironment;

import com.licc.dove.loader.util.HttpClientUtil;

/**
 * 通过当前机器的IP自动改变该项目的环境变量
 */
@Configuration
@Order(-1)
@ConditionalOnProperty("spring.cloud.config.custom")
public class CustomPropertySourceLocator implements PropertySourceLocator {
    private final static String Cloud_Config = "spring.cloud.config.";

    @Override
    public PropertySource<?> locate(Environment environment) {

        MutablePropertySources propertySources = ((StandardServletEnvironment) environment).getPropertySources();
        updateBootstrap(propertySources);
        Map<String, Object> customMap = new HashMap<>();
        return new MapPropertySource("custom", customMap);
    }

    /**
     * 通过当前服务器IP获取环境
     * 
     * @param propertySources
     * @return
     */
    private String getProfileByIp(MutablePropertySources propertySources, String serverUri) {
        PropertySource<?> bootstartp = propertySources.get("springCloudClientHostInfo");
        String ip = (String) bootstartp.getProperty("spring.cloud.client.ipAddress");
        try {
            Map<String, String> params = new HashMap<>();
            params.put("ip", ip);
            String profile = HttpClientUtil.get(serverUri + "/api/getProfileByIp", params);
            return profile;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * @author lichangchao
     * @time 2017-12-22 修改默认的cloud config配置
     */
    private void updateBootstrap(MutablePropertySources propertySources) {
        PropertySource<?> bootstartp = propertySources.get("applicationConfig: [classpath:/bootstrap.yml]");
        propertySources.remove("applicationConfig: [classpath:/bootstrap.yml]");
        Map<String, Object> source = new HashMap<>();
        source.put(Cloud_Config + "name", bootstartp.getProperty(Cloud_Config + "name"));
        source.put(Cloud_Config + "uri", bootstartp.getProperty(Cloud_Config + "uri"));
        source.put(Cloud_Config + "label", bootstartp.getProperty(Cloud_Config + "label"));
        source.put(Cloud_Config + "profile", getProfileByIp(propertySources, (String) bootstartp.getProperty(Cloud_Config + "uri")));
        propertySources.addLast(new MapPropertySource("applicationConfig: [classpath:/bootstrap.yml]", source));
    }

}