package com.licc.dove.config;

import com.licc.dove.dao.config.MyBatisConfigManager;
import org.springframework.stereotype.Component;

/**
 * mybaits 配置
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/12/29 9:37
 * @see
 */
@Component
public class MyBatisConfig implements MyBatisConfigManager {

  @Override
  public String getTypeAliasesPackage() {
    return "com.licc.dove.domain";
  }
}
