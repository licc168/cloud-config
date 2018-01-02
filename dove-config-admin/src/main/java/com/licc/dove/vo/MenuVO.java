package com.licc.dove.vo;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.licc.dove.dao.anno.Table;
import com.licc.dove.domain.Menu;

import lombok.Data;

@Data
@Table(name = "dove_menu")
public class MenuVO  {

    String          parentName;
    List<Menu>      children = Collections.EMPTY_LIST;

    private Long    id;
    private String  name;
    private String  path;
    private Integer orderNum;
    private Long    parentId;

    private String  icon;

    private Boolean deleteFlag;
    /**
     * 创建时间
     */
    private Date    createTime;
    /**
     * 修改时间
     */
    private Date    updateTime;
}
