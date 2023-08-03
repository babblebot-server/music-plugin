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
public class ResumeCommand extends PlayerControlsCommand<EmbedMessage> {
    public ResumeCommand(MusicMangerService service, DiscordGuildFactory discordObjectService) {
        super(service, discordObjectService);
    }

    @Override
    protected EmbedMessage exec(ICommandContext commandContext,
                                DiscordMessage message,
                                GuildMusicManager gmm,
                                VoiceChannel channel,
                                AudioPlayer player) {
        if (!player.isPaused()) {
            return EmbedMessage.builder()
                    .title("Player already playing")
                    .description("Use the pause command to pause playing")
                    .build();
        }

        player.setPaused(false);
        return EmbedMessage.builder()
                .title("Player resumed")
                .description("Use the pause command to pause playing")
                .build();
    }
}
