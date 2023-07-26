package net.babblebot.musicplugin.command;

import discord4j.core.object.entity.channel.VoiceChannel;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.babblebot.api.command.ICommandContext;
import net.babblebot.api.obj.message.discord.DiscordMessage;
import net.babblebot.api.obj.message.discord.embed.EmbedMessage;
import net.babblebot.musicplugin.music.GuildMusicManager;
import net.babblebot.musicplugin.music.MusicPlayer;
import net.babblebot.musicplugin.music.exception.TrackNotFoundException;
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
public class PlayCommand extends SummonedCommand<EmbedMessage> {
    private final MusicPlayer musicPlayer;

    public PlayCommand(MusicMangerService service, DiscordObjectService discordObjectService, MusicPlayer musicPlayer) {
        super(service, discordObjectService);
        this.musicPlayer = musicPlayer;
    }

    @Override
    protected EmbedMessage exec(ICommandContext commandContext,
                                DiscordMessage message,
                                GuildMusicManager gmm,
                                VoiceChannel channel) {
        String value = commandContext.hasParameter("url") ?
                commandContext.getParameter("url") :
                commandContext.getValue();
        try {
            val track = musicPlayer.getMusicTrack(value);
            return musicPlayer.queueMusicTrack(track, gmm, false);
        } catch (TrackNotFoundException e) {
            return EmbedMessage.builder()
                    .title("Track not found")
                    .description("Unable to find track: " + value)
                    .build();
        }
    }
}
