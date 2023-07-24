package com.example.exampleplugin;

import com.example.exampleplugin.config.DevProperties;
import com.example.exampleplugin.config.ExamplePluginConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.babblebot.BabblebotApplication;
import net.babblebot.api.IApplication;
import net.babblebot.api.config.EPluginPermission;
import net.babblebot.api.plugins.PluginType;
import net.babblebot.plugins.PluginConfigParser;
import net.babblebot.plugins.PluginModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Dev Main class for Development Only will not be used inside the
 * main application when importing the plugin
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */

@Slf4j
@SpringBootApplication
@Import(BabblebotApplication.class)
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"net.babblebot", "com.example.exampleplugin"})
@EntityScan(basePackages = {"net.babblebot", "com.example.exampleplugin"})
public class DevMain {
    public static void main(String[] args) {
        BabblebotApplication.make(DevMain.class, args);
    }

    @Bean
    CommandLineRunner onBoot(GenericApplicationContext gac, IApplication app, PluginConfigParser parser) {
        return args -> {
            registerPluginToDependencyInjector(gac);
            String config = setupPluginConfig(gac, parser);
            ExamplePlugin plugin = app.get(ExamplePlugin.class);
            addPluginToPluginContainer(plugin, app, config);
        };
    }

    private void addPluginToPluginContainer(ExamplePlugin plugin, IApplication app, String config) {
        app.getPluginContainer()
                .addPlugin(
                        plugin,
                        PluginModel
                                .builder()
                                .name("example")
                                .pluginType(PluginType.JAVA)
                                .config(config)
                                .namespace("ep")
                                .pluginPermissions(EPluginPermission.all())
                                .build()
                );
    }

    @SneakyThrows
    private String setupPluginConfig(GenericApplicationContext gac,
                                     PluginConfigParser parser) {
        val properties = gac.getBean(DevProperties.class);
        val pluginProps = properties.getPlugin();
        val str = parser.pluginConfigToString(pluginProps);
        ObjectMapper mapper = new ObjectMapper();
        ExamplePluginConfig config = mapper.readValue(str, ExamplePluginConfig.class);
        gac.registerBean(ExamplePluginConfig.class, () -> config);
        return parser.pluginConfigToString(config);
    }

    private void registerPluginToDependencyInjector(GenericApplicationContext gac) {
        gac.registerBean(ExamplePlugin.class);
    }
}