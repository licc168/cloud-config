package com.licc.dove.service;

import com.licc.dove.dao.CommonDao;
import com.licc.dove.dao.Page;
import com.licc.dove.dao.ParamMap;
import com.licc.dove.domain.DoveConfigApp;
import com.licc.dove.param.DoveConfigAppParam;
import com.licc.dove.param.PageParam;
import com.licc.dove.service.base.impl.BaseServiceImpl;
import com.licc.dove.util.BeanMapper;
import com.licc.dove.vo.MenuVO;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author  lichangchao
 * @version 1.0.0
 * @date 2017/12/29 13:18
 * @see
 */
@Service
public class DoveConfigAppService extends BaseServiceImpl<DoveConfigApp> {
  @Resource
  CommonDao commonDao;

  public Page<DoveConfigApp> page(DoveConfigAppParam param) {
   DoveConfigApp doveConfigApp = BeanMapper.map(param,DoveConfigApp.class);
    Page<DoveConfigApp> menuPage = commonDao.findPageByExample(DoveConfigApp.class, transPage(param), doveConfigApp);
    return menuPage;

  }

  public List<DoveConfigApp> findAll(){
    DoveConfigApp configApp = new DoveConfigApp();
    configApp.setDeleteFlag(false);
    List<DoveConfigApp> list =  commonDao.listByExample(configApp);
    return list;
  }
}
