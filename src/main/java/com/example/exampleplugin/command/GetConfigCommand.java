package com.example.exampleplugin.command;

import com.example.exampleplugin.config.ExamplePluginConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Ping command, that will return pong on execution
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetConfigCommand {
    public String execute(ExamplePluginConfig config) {
        return config.toString();
    }
}
