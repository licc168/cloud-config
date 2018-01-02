package com.licc.dove.config.domin;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "config_app")
@Data
public class ConfigApp {
    @Id
    @GeneratedValue
    private Long id;
    private String application;   //应用名称
    private String profile;       //应用模块
    private String label;         //应用环境
    private String version;       //应用版本



}