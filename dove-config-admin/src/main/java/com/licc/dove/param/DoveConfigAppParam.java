package com.licc.dove.param;

import lombok.Data;

/**
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/12/29 13:33
 * @see
 */
@Data
public class DoveConfigAppParam    extends  PageParam   {
   private Long id;
   private String url;
   private String application;
   private String lable;
   private String profile;
   private String version;
   private String remark;

}
