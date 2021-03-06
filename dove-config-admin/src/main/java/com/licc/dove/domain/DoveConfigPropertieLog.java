package com.licc.dove.domain;



import com.licc.dove.dao.anno.Column;
import com.licc.dove.dao.anno.Id;
import com.licc.dove.dao.anno.Sequence;
import com.licc.dove.dao.anno.Table;
import java.util.Date;
import lombok.Data;

@Data

@Table(name = "dove_config_propertie_log")
public class DoveConfigPropertieLog  {
    @Id
    @Sequence()
    private Long id;
    @Column(name = "config_propertie_id")
    Long    configPropertieId;
    @Column(name = "config_app_id")
    Long    configAppId;
    String  name;

    @Column(name = "pro_key")
    String  proKey;

    @Column(name = "pro_value")
    String  proValue;

    Integer type;

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
