package com.licc.dove.param;

import lombok.Data;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/2 9:13
 * @see
 */
@Data
public class DoveConfigEnvParam extends  PageParam      {
  private Long    id;
  String          name;
  String          profile;
}
