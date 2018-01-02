package com.licc.dove.service;

import com.licc.dove.dao.CommonDao;
import com.licc.dove.dao.Page;
import com.licc.dove.dao.ParamMap;
import com.licc.dove.domain.DoveConfigApp;
import com.licc.dove.domain.DoveConfigPropertie;
import com.licc.dove.param.DoveConfigAppParam;
import com.licc.dove.param.DoveConfigPropertieParam;
import com.licc.dove.service.base.impl.BaseServiceImpl;
import com.licc.dove.util.BeanMapper;
import com.licc.dove.vo.DoveConfigPropertiesVO;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 配置中心-属性管理
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/12/29 15:21
 * @see
 */
@Service
public class DoveConfigPropertiesService extends BaseServiceImpl<DoveConfigPropertie> {
  @Resource
  CommonDao commonDao;

  public Page<DoveConfigPropertiesVO> findAll(DoveConfigPropertieParam param) {
    Page<DoveConfigPropertiesVO> menuPage = commonDao.findPageByParams(DoveConfigPropertiesVO.class, transPage(param),"DoveConfigPropertiesMapper.findList",new ParamMap(param));
    return menuPage;

  }
}
