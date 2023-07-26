package net.babblebot.musicplugin.config;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.plugins.PluginConfig;

/**
 * Music Plugin Configuration Class
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@PluginConfig
@Slf4j
@Data
@Builder
@Jacksonized
public class MusicPluginConfig {
}
