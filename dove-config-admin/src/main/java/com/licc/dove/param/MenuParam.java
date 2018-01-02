package com.licc.dove.param;


import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MenuParam  extends  PageParam {

  private Long id;
  @NotNull(message = "菜单名称不能为空")
  private String name;

  private String path;

  private Integer orderNum;

  private Long parentId;


  private String icon;


}
