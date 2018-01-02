package com.licc.dove.config.repository;


import com.licc.dove.config.domin.ConfigProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigPropertiesRepository  extends JpaRepository<ConfigProperties, Long> {
 List<ConfigProperties> findByAppId(Long appId);
}
