package com.licc.dove.config.domin;


import java.util.Date;


import com.licc.dove.dao.anno.Column;
import com.licc.dove.dao.anno.Id;
import com.licc.dove.dao.anno.Sequence;
import com.licc.dove.dao.anno.Table;

import lombok.Data;

@Data

@Table(name = "dove_config_propertie")
public class DoveConfigPropertie {
 @Id
 @Sequence()
 private Long id;
 @Column(name = "config_app_id")
  Long configAppId;
  String  name;
  @Column(name = "pro_key")
  String proKey;

  @Column(name = "pro_value")
  String proValue;

 @Column(name = "delete_flag")
 private Boolean deleteFlag;
 /**
  * 创建时间
  */
 @Column(name = "create_time")
 private Date createTime;
 /**
  * 修改时间
  */
 @Column(name = "update_time")
 private Date updateTime;
}
