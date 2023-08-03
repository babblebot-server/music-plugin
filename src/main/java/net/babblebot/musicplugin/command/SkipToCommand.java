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
public class SkipToCommand extends PlayerControlsCommand<EmbedMessage> {
    public SkipToCommand(MusicMangerService service, DiscordGuildFactory discordObjectService) {
        super(service, discordObjectService);
    }

    @Override
    protected EmbedMessage exec(ICommandContext context,
                                DiscordMessage message,
                                GuildMusicManager gmm,
                                VoiceChannel channel,
                                AudioPlayer player) {
        if (context.hasParameter("mins")) {
            int v = Integer.parseInt(context.getParameter("mins"));
            v = v * 1000 * 60;
            long value = Long.parseLong(Integer.toString(v));
            gmm.getPlayer().getPlayingTrack().setPosition(value);
            return create(context.getParameter("mins"), "mins");
        }

        if (context.hasParameter("seconds")) {
            int v = Integer.parseInt(context.getParameter("seconds"));
            v = v * 1000;
            long value = Long.parseLong(Integer.toString(v));
            gmm.getPlayer().getPlayingTrack().setPosition(value);
            return create(context.getParameter("seconds"), "seconds");
        }

        if (context.hasParameter("hours")) {
            int v = Integer.parseInt(context.getParameter("hours"));
            v = v * 1000;
            long value = Long.parseLong(Integer.toString(v));
            gmm.getPlayer().getPlayingTrack().setPosition(value);
            return create(context.getParameter("hours"), "hours");
        }

        long value = Long.parseLong(context.getValue());

        gmm.getPlayer().getPlayingTrack().setPosition(value);

        return create(context.getValue(), "ms");
    }

    private EmbedMessage create(String unitValue, String unitType) {
        return EmbedMessage.builder()
                .title("Track Skipped!")
                .description("Skipped to: " + unitValue + " " + unitType + ".")
                .build();
    }
}
