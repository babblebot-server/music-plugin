package net.babblebot.musicplugin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.command.Command;
import net.babblebot.api.command.CommandExample;
import net.babblebot.api.command.CommandParam;
import net.babblebot.api.command.ICommandContext;
import net.babblebot.api.obj.message.discord.DiscordMessage;
import net.babblebot.api.obj.message.discord.embed.EmbedMessage;
import net.babblebot.api.plugins.Plugin;
import net.babblebot.musicplugin.command.*;

/**
 * Plugin Handler for the Music Plugin
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Plugin(value = "music-plugin", minServerVersion = "3.0.0-rc.27", author = "Ben Davies <me@bdavies.net>")
@RequiredArgsConstructor
@Slf4j
public class MusicPlugin {
    private final SummonCommand summonCommand;
    private final ExileCommand exileCommand;
    private final PlayCommand playCommand;
    private final SkipCommand skipCommand;
    private final PauseCommand pauseCommand;
    private final ResumeCommand resumeCommand;
    private final SkipToCommand skipToCommand;

    @Command(aliases = "summon", description = "Summon the bot the voice channel you are in.")
    public String summon(ICommandContext commandContext, DiscordMessage message) {
        return summonCommand.exec(message);
    }

    @Command(aliases = {"exile", "part"},
            description = "Exile the bot from the voice channel, only if you're in the channel")
    public String exile(ICommandContext context, DiscordMessage message) {
        return exileCommand.exec(context, message);
    }

    @Command(aliases = {"play", "req", "request"},
            description = "This will play a song.",
            exampleValue = "https://www.youtube.com/watch?v=we9jeU76Y9E")
    @CommandParam(value = "url",
            canBeEmpty = false,
            exampleValue = "https://www.youtube.com/watch?v=we9jeU76Y9E")
    public EmbedMessage play(ICommandContext commandContext, DiscordMessage message) {
        if (!commandContext.hasNonEmptyParameter("url") && commandContext.getValue().equals("")) {
            commandContext.getCommandResponse().sendString("You must supply a url or search string to play");
            return null;
        }

        return playCommand.exec(commandContext, message);
    }

    @Command(
            description = "Skip a track use the value of all to clear the queue",
            exampleValue = "all"
    )
    @CommandExample("!skip all")
    public EmbedMessage skip(ICommandContext context, DiscordMessage message) {
        return skipCommand.exec(context, message);
    }

    @Command(description = "Pause the current playing track.")
    public EmbedMessage pause(ICommandContext context, DiscordMessage message) {
        return pauseCommand.exec(context, message);
    }

    @Command(description = "Resume the current playing track.")
    public EmbedMessage resume(ICommandContext context, DiscordMessage message) {
        return resumeCommand.exec(context, message);
    }

    @Command(aliases = "skipto", description = "skip to a point in a track")
    @CommandParam(value = "mins", canBeEmpty = false, exampleValue = "1")
    @CommandParam(value = "seconds", canBeEmpty = false, exampleValue = "1")
    @CommandParam(value = "hours", canBeEmpty = false, exampleValue = "1")
    public EmbedMessage skipTo(DiscordMessage message, ICommandContext context) {
        return skipToCommand.exec(context, message);
    }
}

