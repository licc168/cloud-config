package com.licc.dove.config.domin;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "config_properties")
@Data
public class ConfigProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name = "app_id")
    private Long appId;
    @Column(name = "pro_key")
    private String proKey;
    @Column(name = "pro_value")
    private String proValue;



}