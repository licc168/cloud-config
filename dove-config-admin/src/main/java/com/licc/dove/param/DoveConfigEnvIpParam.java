package com.licc.dove.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/2 9:13
 * @see
 */
@Data
public class DoveConfigEnvIpParam extends PageParam {

    private Long id;
    @NotNull(message = "环境不能为空")
    Long         configEnvId;
    @NotNull(message = "IP不能为空")

    String       ip;

    String       envName;

    String       envProfile;
}
