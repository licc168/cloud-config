package com.licc.dove.config.repository;

import com.licc.dove.config.domin.ConfigApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigAppRepository extends JpaRepository<ConfigApp, Long> {
    ConfigApp findFirstByApplicationAndProfileAndLabel(String application, String profile, String label);
}