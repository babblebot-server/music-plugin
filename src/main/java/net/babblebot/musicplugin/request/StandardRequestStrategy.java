package net.babblebot.musicplugin.request;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.IDiscordFacade;
import net.babblebot.musicplugin.music.MusicTrack;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author ben.davies99@outlook.com (Ben Davies)
 * @since 1.0.0
 */
@Slf4j
@Service
public class StandardRequestStrategy implements RequestStrategy {


    private final IDiscordFacade facade;

    public StandardRequestStrategy(IDiscordFacade facade) {
        this.facade = facade;
    }


    @Override
    public Mono<MusicTrack> handle(String request, AudioPlayerManager manager) {
        return Mono.create(sink -> manager.loadItem(request, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                sink.success(new MusicTrack(track));
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                sink.success(new MusicTrack(playlist));
            }

            @Override
            public void noMatches() {
                sink.error(new FriendlyException("No Matches found", FriendlyException.Severity.COMMON, null));
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                sink.error(exception);
            }
        }));
    }
}
