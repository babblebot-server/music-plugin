package net.babblebot.musicplugin.request;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.IApplication;

/**
 * @author ben.davies99@outlook.com (Ben Davies)
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
public class RequestStrategyFactory {
    public static RequestStrategy makeRequestStrategy(IApplication app) {
        return app.get(StandardRequestStrategy.class);
    }
}
