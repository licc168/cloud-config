package com.licc.dove.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.licc.dove.dao.CommonDao;
import com.licc.dove.dao.Page;
import com.licc.dove.domain.DoveConfigEnv;
import com.licc.dove.param.DoveConfigEnvParam;
import com.licc.dove.service.base.impl.BaseServiceImpl;
import com.licc.dove.util.BeanMapper;

/**
 * @author  lichangchao
 * @version 1.0.0
 * @date 2017/12/29 13:18
 * @see
 */
@Service
public class DoveConfigEnvService extends BaseServiceImpl<DoveConfigEnv> {
  @Resource
  CommonDao commonDao;

  public Page<DoveConfigEnv> page(DoveConfigEnvParam param) {
    DoveConfigEnv doveConfigApp = BeanMapper.map(param,DoveConfigEnv.class);
    doveConfigApp.setDeleteFlag(false);
    Page<DoveConfigEnv> menuPage = commonDao.findPageByExample(DoveConfigEnv.class, transPage(param), doveConfigApp);
    return menuPage;

  }

  public List<DoveConfigEnv> findAll(){
    DoveConfigEnv configEnv = new DoveConfigEnv();
    configEnv.setDeleteFlag(false);
    List<DoveConfigEnv> list =  commonDao.listByExample(configEnv);
    return list;
  }
}
