package com.licc.dove;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义属性
 * 
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/4 9:55
 * @see
 */
@ConfigurationProperties(prefix = "config", ignoreUnknownFields = false)
public class ConfigProperties {
    private final Mail mail = new Mail();

    public Mail getMail() {
        return mail;
    }

    public static class Mail {

        private String from = "tuxAdmin@localhost";

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }
    }
}
