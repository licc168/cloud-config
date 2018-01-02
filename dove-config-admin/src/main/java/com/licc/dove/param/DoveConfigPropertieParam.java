package com.licc.dove.param;

import lombok.Data;

/**
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/12/29 13:33
 * @see
 */
@Data
public class DoveConfigPropertieParam extends PageParam {
    private Long id;

    Long         configAppId;
    String       name;
    String       proKey;

    String       proValue;
    String       appName;
    String       appProfile;
}
