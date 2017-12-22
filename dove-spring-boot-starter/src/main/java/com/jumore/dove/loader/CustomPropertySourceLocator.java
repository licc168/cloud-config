package com.jumore.dove.loader;


import java.util.HashMap;
import java.util.Map;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;


import org.springframework.web.context.support.StandardServletEnvironment;


@Configuration
@Order(-1)
public class CustomPropertySourceLocator implements PropertySourceLocator {
   private  final static String Cloud_Config = "spring.cloud.config.";
    @Override
    public PropertySource<?> locate(Environment environment) {

        MutablePropertySources propertySources = ((StandardServletEnvironment) environment).getPropertySources();
        updateBootstrap(propertySources);
        Map<String,Object> customMap =  new HashMap<>();
        return new MapPropertySource("custom",customMap);
    }

    /**
     * @author  lichangchao
     * @time  2017-12-22
     * 修改默认的cloud config配置
     */
    private void  updateBootstrap(MutablePropertySources propertySources){
        PropertySource<?> bootstartp =  propertySources.get("applicationConfig: [classpath:/bootstrap.yml]");
        propertySources.remove("applicationConfig: [classpath:/bootstrap.yml]");
        Map<String, Object> source = new HashMap<>();
        source.put(Cloud_Config+"profile", "test");
        source.put(Cloud_Config+"name",bootstartp.getProperty(Cloud_Config+"name"));
        source.put(Cloud_Config+"uri",bootstartp.getProperty(Cloud_Config+"uri"));
        source.put(Cloud_Config+"label",bootstartp.getProperty(Cloud_Config+"label"));
        propertySources.addLast(new MapPropertySource("applicationConfig: [classpath:/bootstrap.yml]",source));
     }



}