package com.example.exampleplugin.config;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.plugins.PluginConfig;

/**
 * Edit me
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@PluginConfig
@Slf4j
@Data
@Builder
@Jacksonized
public class ExamplePluginConfig {
    private String someValue;
}
