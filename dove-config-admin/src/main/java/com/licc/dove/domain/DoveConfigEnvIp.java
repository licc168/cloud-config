package com.licc.dove.domain;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.licc.dove.dao.anno.Column;
import com.licc.dove.dao.anno.Id;
import com.licc.dove.dao.anno.Sequence;
import com.licc.dove.dao.anno.Table;

import lombok.Data;

@Data

@Table(name = "dove_config_env_ip")
public class DoveConfigEnvIp {
    @Id
    @Sequence()
    private Long    id;
    @Column(name = "config_env_id")
    Long            configEnvId;
    String          ip;

    @Column(name = "delete_flag")
    private Boolean deleteFlag;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @CreatedDate
    private Date    createTime;
    /**
     * 修改时间
     */
    @Column(name = "update_time")
    @LastModifiedDate
    private Date    updateTime;
}
