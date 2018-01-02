package com.licc.dove.domain;

import com.licc.dove.dao.anno.Column;
import com.licc.dove.dao.anno.Id;
import com.licc.dove.dao.anno.Sequence;
import com.licc.dove.dao.anno.Table;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data

@Table(name = "dove_menu")
public class Menu implements Serializable {
  @Id
  @Sequence()
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "path")
  private String path;
  @Column(name="order_num")
  private Integer orderNum;
  @Column(name="parent_id")
  private Long parentId;

  @Column(name = "icon")
  private String icon;



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
