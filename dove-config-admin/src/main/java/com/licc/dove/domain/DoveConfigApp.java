package com.licc.dove.domain;

import com.licc.dove.dao.anno.Column;
import com.licc.dove.dao.anno.Id;
import com.licc.dove.dao.anno.Sequence;
import com.licc.dove.dao.anno.Table;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data

@Table(name = "dove_config_app")
public class DoveConfigApp {
  @Id
  @Sequence()
  private Long id;
  private String url;
  private String application;
  private String lable;
  private String profile;
  private String version;
  private String remark;

  @Column(name = "delete_flag")
  private Boolean deleteFlag;
  /**
   * 创建时间
   */
  @Column(name = "create_time")
  @CreatedDate
  private Date createTime;
  /**
   * 修改时间
   */
  @Column(name = "update_time")
  @LastModifiedDate
  private Date updateTime;
}
