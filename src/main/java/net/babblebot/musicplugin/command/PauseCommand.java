package net.babblebot.musicplugin.command;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discord4j.core.object.entity.channel.VoiceChannel;
import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.command.ICommandContext;
import net.babblebot.api.obj.message.discord.DiscordMessage;
import net.babblebot.api.obj.message.discord.embed.EmbedMessage;
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
public class PauseCommand extends PlayerControlsCommand<EmbedMessage> {
    public PauseCommand(MusicMangerService service, DiscordObjectService discordObjectService) {
        super(service, discordObjectService);
    }

    @Override
    protected EmbedMessage exec(ICommandContext commandContext,
                                DiscordMessage message,
                                GuildMusicManager gmm,
                                VoiceChannel channel,
                                AudioPlayer player) {
        if (player.isPaused()) {
            return EmbedMessage.builder()
                    .title("Player already paused")
                    .description("Use the resume command to resume playing")
                    .build();
        }

        player.setPaused(true);
        return EmbedMessage.builder()
                .title("Player Paused")
                .description("Use the resume command to resume playing")
                .build();
    }
}
