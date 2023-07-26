package net.babblebot.musicplugin.command;

import discord4j.core.object.entity.channel.VoiceChannel;
import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.command.ICommandContext;
import net.babblebot.api.obj.message.discord.DiscordMessage;
import net.babblebot.musicplugin.music.GuildMusicManager;
import net.babblebot.musicplugin.service.DiscordObjectService;
import net.babblebot.musicplugin.service.MusicMangerService;
import org.springframework.stereotype.Service;

/**
 * Edit me
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Slf4j
@Service
public class ExileCommand extends SummonedCommand<String> {
    public ExileCommand(MusicMangerService service, DiscordObjectService discordObjectService) {
        super(service, discordObjectService);
    }

    @Override
    protected String exec(ICommandContext commandContext, DiscordMessage message, GuildMusicManager gmm, VoiceChannel channel) {
        gmm.getPlayer().destroy();
        gmm.getQueue().empty();
        gmm.setHasBeenSummoned(false);
        gmm.getVoiceConnection().disconnect().block();
        return "Disconnected from channel: " + channel.getName();
    }
}
