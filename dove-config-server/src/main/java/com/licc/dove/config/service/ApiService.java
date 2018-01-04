package com.licc.dove.config.service;

import com.licc.dove.config.domin.DoveConfigEnv;
import com.licc.dove.config.domin.DoveConfigEnvIp;
import com.licc.dove.dao.CommonDao;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/4 13:45
 * @see
 */
@Service
@Transactional
public class ApiService {
  @Resource
  CommonDao commonDao;
  /**
   * 通过IP获取当前环境
   * @param ip
   * @return
   */
  public  String getProfileByIp(String ip){
    DoveConfigEnvIp env = new DoveConfigEnvIp();
    env.setDeleteFlag(false);
    env.setIp(ip);
    List<DoveConfigEnvIp> list = commonDao.listByExample(env);
    if(CollectionUtils.isEmpty(list))return "dev";
    DoveConfigEnvIp envIp =  list.get(0);
    Long envId = envIp.getConfigEnvId();
    DoveConfigEnv doveConfigEnv = commonDao.get(DoveConfigEnv.class,envId);
    if(doveConfigEnv==null) return "dev";
    return doveConfigEnv.getProfile();
  }
}
