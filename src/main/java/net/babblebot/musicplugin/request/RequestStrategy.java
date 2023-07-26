package net.babblebot.musicplugin.request;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.babblebot.musicplugin.config.MusicPluginConfig;
import net.babblebot.musicplugin.music.MusicTrack;
import reactor.core.publisher.Mono;

/**
 * @author ben.davies99@outlook.com (Ben Davies)
 * @since 1.0.0
 */
public interface RequestStrategy {
    /**
     * This will be run when a new request is made.
     *
     * @param request        - This is the request that has been made by the user.
     * @param manager  the player manager
     * @return AudioTrack - audio-track enQueue.
     */
    Mono<MusicTrack> handle(String request, AudioPlayerManager manager);
}
