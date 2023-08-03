package net.babblebot.musicplugin.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.IDiscordFacade;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.managers.AudioManager;

/**
 * This handles an AudioPlayer per Guild which allows your bot to run in multiple guilds.
 *
 * @author ben.davies99@outlook.com (Ben Davies)
 * @since 1.0.0
 */
@Slf4j
public class GuildMusicManager {

    @Getter
    private final AudioPlayer player;

    @Getter
    private final TrackQueue queue;

    @Getter
    @Setter
    private AudioManager audioManager;

    @Getter
    @Setter
    private boolean hasBeenSummoned = false;

    private final AudioSendHandler provider;

    public GuildMusicManager(final AudioPlayerManager manager, final IDiscordFacade facade) {
        this.player = manager.createPlayer();
        this.queue = new TrackQueue(player, facade);
        this.provider = new DiscordAudioProvider(player);
        this.player.addListener(queue);
    }

    public AudioSendHandler getAudioProvider() {
        return provider;
    }
}
