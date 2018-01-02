package com.licc.dove.config.service;


import com.licc.dove.config.domin.ConfigApp;
import com.licc.dove.config.domin.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabasesEnvironmentRepository implements EnvironmentRepository {

    @Autowired
    private ConfigRepositoryService configRepositoryService;

    @Override
    public Environment findOne(String application, String profile, String label) {
        if (StringUtils.isEmpty(application) || StringUtils.isEmpty(profile))
            return null;
        ConfigApp configApp = configRepositoryService.findByApplicationAndProfileAndLabel(application, profile, label);
        if (configApp != null) {
            Environment environment = new Environment(application, StringUtils.commaDelimitedListToStringArray(profile));
            List<ConfigProperties> list = configRepositoryService.findByAppId(configApp.getId());
            if(!CollectionUtils.isEmpty(list)) {
                Map<String, Object> properties = new HashMap<>();

                for (ConfigProperties configProperties : list) {
                    properties.put(configProperties.getProKey(), configProperties.getProValue());
                }
                environment.add(new PropertySource(application, properties));
            }

            return environment;
        }
        return new Environment(application, profile);
    }
}