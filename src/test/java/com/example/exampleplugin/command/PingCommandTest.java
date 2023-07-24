package com.example.exampleplugin.command;

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
public class PingCommandTest {
    final PingCommand pingCommand = new PingCommand();

    @Test
    @DisplayName("Test the Ping command returns pong!")
    void testPingCommand() {
        String resp = pingCommand.execute();
        assertEquals("pong!", resp);
    }
}
