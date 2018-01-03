package com.licc.dove.param;


import lombok.Data;

/**
 * 分页参数
 *
 * @author lichangchao
 * @date 2017 -04-25 19:25:03
 */
@Data
public class PageParam  extends BaseParam {
    private Integer page = 0;
    private Integer size = 10;


}
