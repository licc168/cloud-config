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
@Table(name = "dove_config_env_ip")
@Data
public class DoveConfigEnvIpVO {
  private Long id;
  private Long configEnvId;
  String       ip;
  String       envName;

  String       envProfile;


}
