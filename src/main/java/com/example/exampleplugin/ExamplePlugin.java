package com.example.exampleplugin;

import com.example.exampleplugin.command.AddPersonCommand;
import com.example.exampleplugin.command.GetConfigCommand;
import com.example.exampleplugin.command.ListPeopleCommand;
import com.example.exampleplugin.command.PingCommand;
import com.example.exampleplugin.config.ExamplePluginConfig;
import com.example.exampleplugin.model.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.command.Command;
import net.babblebot.api.command.CommandExample;
import net.babblebot.api.command.CommandParam;
import net.babblebot.api.command.ICommandContext;
import net.babblebot.api.obj.message.discord.DiscordMessage;
import net.babblebot.api.obj.message.discord.embed.EmbedMessage;
import net.babblebot.api.plugins.Plugin;

/**
 * Edit me
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Plugin
@RequiredArgsConstructor
@Slf4j
public class ExamplePlugin {
    private final PersonRepository repository;
    private final PingCommand pingCommand;
    private final AddPersonCommand addPersonCommand;
    private final ListPeopleCommand listPeopleCommand;
    private final GetConfigCommand getConfigCommand;

    @Command(description = "Ping the bot!")
    public String ping(DiscordMessage message, ICommandContext context) {
        return pingCommand.execute();
    }

    @Command(aliases = "addperson", description = "Add a person to the system")
    @CommandParam(value = "name", canBeEmpty = false, optional = false, exampleValue = "john")
    @CommandExample("${commandName} -name=John")
    public EmbedMessage addPerson(DiscordMessage message, ICommandContext context) {
        return addPersonCommand.execute(message, context);
    }

    @Command(aliases = "listpeople", description = "list all the people")
    public EmbedMessage listPeople(DiscordMessage message, ICommandContext context) {
        return listPeopleCommand.execute(message, context);
    }

    @Command(description = "Get plugin config")
    public String config(DiscordMessage message, ICommandContext context, ExamplePluginConfig config) {
        return getConfigCommand.execute(config);
    }
}

