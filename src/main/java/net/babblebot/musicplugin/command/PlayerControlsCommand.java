package net.babblebot.musicplugin.command;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.command.ICommandContext;
import net.babblebot.api.obj.message.discord.DiscordMessage;
import net.babblebot.api.obj.message.discord.embed.EmbedMessage;
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
public abstract class PlayerControlsCommand<T> extends SummonedCommand<T> {
    public PlayerControlsCommand(MusicMangerService service, DiscordGuildFactory discordObjectService) {
        super(service, discordObjectService);
    }

    @Override
    protected T exec(ICommandContext commandContext,
                     DiscordMessage message,
                     GuildMusicManager gmm,
                     VoiceChannel channel) {
        if (gmm.getPlayer().getPlayingTrack() == null) {
            commandContext.getCommandResponse()
                    .sendEmbed(
                            EmbedMessage.builder()
                                    .title("No Track Playing")
                                    .description("To use a player control command a track needs to be playing")
                                    .build()
                    );
            return null;
        }

        return exec(commandContext, message, gmm, channel, gmm.getPlayer());
    }

    protected abstract T exec(ICommandContext commandContext, DiscordMessage message,
                              GuildMusicManager gmm, VoiceChannel channel, AudioPlayer player);
}
