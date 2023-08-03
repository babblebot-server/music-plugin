package net.babblebot.musicplugin.command;

import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.command.ICommandContext;
import net.babblebot.api.obj.message.discord.DiscordMessage;
import net.babblebot.discord.obj.factories.DiscordGuildFactory;
import net.babblebot.musicplugin.music.GuildMusicManager;
import net.babblebot.musicplugin.service.MusicMangerService;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
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
    public ExileCommand(MusicMangerService service, DiscordGuildFactory discordObjectService) {
        super(service, discordObjectService);
    }

    @Override
    protected String exec(ICommandContext commandContext, DiscordMessage message, GuildMusicManager gmm, VoiceChannel channel) {
        gmm.getPlayer().destroy();
        gmm.getQueue().empty();
        gmm.setHasBeenSummoned(false);
        gmm.getAudioManager().closeAudioConnection();
        return "Disconnected from channel: " + channel.getName();
    }
}
