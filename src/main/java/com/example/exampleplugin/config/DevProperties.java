package com.example.exampleplugin.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Edit me
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Configuration
@ConfigurationProperties
@Data
@Slf4j
public class DevProperties {
    private Map<String, Object> plugin;
}
