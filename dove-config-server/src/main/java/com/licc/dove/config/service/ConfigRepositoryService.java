package com.licc.dove.config.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.licc.dove.config.domin.DoveConfigApp;
import com.licc.dove.config.domin.DoveConfigPropertie;
import com.licc.dove.dao.CommonDao;

@Service
@Transactional
public class ConfigRepositoryService  {
    @Resource
    CommonDao commonDao;


    public DoveConfigApp findByApplicationAndProfileAndLabel(String application, String profile, String label) {
        DoveConfigApp doveConfigApp = new DoveConfigApp();
        doveConfigApp.setDeleteFlag(false);
        doveConfigApp.setApplication(application);
        doveConfigApp.setProfile(profile);
        doveConfigApp.setLable(label);
        List<DoveConfigApp> list = commonDao.listByExample(doveConfigApp);
        if(CollectionUtils.isEmpty(list))return null;
        return list.get(0);

    }
    public List<DoveConfigPropertie> findByAppId(Long appId){
        DoveConfigPropertie propertie = new DoveConfigPropertie();
        propertie.setConfigAppId(appId);
        propertie.setDeleteFlag(false);
       return commonDao.listByExample(propertie);
    }

}