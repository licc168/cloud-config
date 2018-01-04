package com.licc.dove.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.licc.dove.dao.CommonDao;
import com.licc.dove.dao.Page;
import com.licc.dove.dao.ParamMap;
import com.licc.dove.domain.DoveConfigEnvIp;
import com.licc.dove.param.DoveConfigEnvIpParam;
import com.licc.dove.service.base.impl.BaseServiceImpl;
import com.licc.dove.vo.DoveConfigEnvIpVO;

/**
 * @author  lichangchao
 * @version 1.0.0
 * @date 2017/12/29 13:18
 * @see
 */
@Service
public class DoveConfigEnvIpService extends BaseServiceImpl<DoveConfigEnvIp> {
  @Resource
  CommonDao commonDao;

  public Page<DoveConfigEnvIpVO> page(DoveConfigEnvIpParam param) {
    Page<DoveConfigEnvIpVO> menuPage = commonDao.findPageByParams(DoveConfigEnvIpVO.class, transPage(param),"DoveConfigEnvIpMapper.findList",new ParamMap(param));
    return menuPage;

  }




}
