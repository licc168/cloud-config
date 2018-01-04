package com.licc.dove.vo;

import com.licc.dove.dao.anno.Table;
import lombok.Data;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/12/29 15:24
 * @see
 */
@Table(name = "dove_config_propertie")
@Data
public class DoveConfigPropertiesVO {
  private Long id;

  Long         configAppId;
  String       name;
  String       proKey;

  String       proValue;

  String appName;
  String appProfile;
  String appLable;
}
