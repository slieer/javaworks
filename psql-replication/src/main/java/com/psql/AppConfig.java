package com.psql;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.datasource", ignoreUnknownFields = false)
public class AppConfig {

    private String url;
    private String username;
    private String password;

}
