package com.licc.dove.config.service;

import com.licc.dove.config.domin.ConfigApp;
import com.licc.dove.config.domin.ConfigProperties;
import com.licc.dove.config.repository.ConfigAppRepository;
import com.licc.dove.config.repository.ConfigPropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ConfigRepositoryService {
    @Autowired
    private ConfigAppRepository configAppRepository;
    @Autowired
    private ConfigPropertiesRepository configPropertiesRepository;
    public ConfigApp findByApplicationAndProfileAndLabel(String application, String profile, String label) {
        return configAppRepository.findFirstByApplicationAndProfileAndLabel(application, profile, label);
    }
    public List<ConfigProperties> findByAppId(Long appId){
       return configPropertiesRepository.findByAppId(appId);
    }

}