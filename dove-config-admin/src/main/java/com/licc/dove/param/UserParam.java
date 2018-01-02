package com.licc.dove.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


import lombok.Data;

/**
 * @author lichangchao
 * @Time 2017 -04-13 20:22:31
 */
@Data
public class UserParam extends PageParam implements Serializable {
    @NotNull(message = "用户名不能为空")
    private String userName;


    private String password;


}
