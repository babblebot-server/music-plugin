package com.example.exampleplugin.command;

import com.example.exampleplugin.config.ExamplePluginConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Edit me
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Slf4j
public class GetConfigCommandTest {
    final GetConfigCommand configCommand = new GetConfigCommand();

    @Test
    @DisplayName("Test the Get config command returns the config!")
    void testConfigCommand() {
        ExamplePluginConfig config = ExamplePluginConfig.builder()
                .someValue("test")
                .build();
        String resp = configCommand.execute(config);
        assertEquals(config.toString(), resp);
    }
}
