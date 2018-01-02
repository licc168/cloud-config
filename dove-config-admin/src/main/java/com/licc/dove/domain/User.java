package com.licc.dove.domain;

import com.licc.dove.dao.anno.Column;
import com.licc.dove.dao.anno.Id;
import com.licc.dove.dao.anno.Sequence;
import com.licc.dove.dao.anno.Table;
import java.io.Serializable;


import java.util.Date;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * @author lichangchao
 * @Time 2017 -03-20 15:05:56
 */

@Table(name = "dove_user")
@Data
public class User   implements Serializable {

    @Id
    @Sequence()
    private Long id;

    @NotNull
    @Column(name = "user_name")
    private String userName;

    @JsonIgnore

    @Column(name = "password")
    private String password;



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
