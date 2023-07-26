package net.babblebot.musicplugin.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.babblebot.api.IApplication;
import net.babblebot.api.obj.message.discord.embed.EmbedField;
import net.babblebot.api.obj.message.discord.embed.EmbedMessage;
import net.babblebot.musicplugin.music.exception.TrackNotFoundException;
import net.babblebot.musicplugin.request.RequestStrategy;
import net.babblebot.musicplugin.request.RequestStrategyFactory;
import net.babblebot.musicplugin.service.MusicMangerService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Edit me
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MusicPlayer {
    private final IApplication application;
    private final MusicMangerService service;

    public MusicTrack getMusicTrack(String url) {
        RequestStrategy strategy = RequestStrategyFactory.makeRequestStrategy(application);
        return strategy.handle(url, service.getPlayerManager())
                .onErrorComplete()
                .blockOptional()
                .orElseThrow(TrackNotFoundException::new);
    }

    public EmbedMessage queueMusicTrack(MusicTrack track, GuildMusicManager gmm, boolean loadFullPlaylist) {
        if (track.isPlaylist()) {
            if (loadFullPlaylist) {
                track.getPlaylist().getTracks()
                        .forEach(at -> gmm.getQueue().enQueue(at));
                return EmbedMessage.builder()
                        .title("Loaded playlist!")
                        .description("Loaded: " + track.getPlaylist().getTracks().size() + " items")
                        .addField(EmbedField.builder()
                                .name("Playlist time")
                                .value(getTimeFormatted(track.getPlaylist().getTracks().stream()
                                        .mapToLong(AudioTrack::getDuration).sum()))
                                .inline(true)
                                .build())
                        .build();
            } else {
                gmm.getQueue().enQueue(track.getPlaylist().getSelectedTrack());
                return getEmbedObject(
                        track.getPlaylist().getSelectedTrack(),
                        "Song requested",
                        true,
                        gmm
                );
            }
        } else {
            gmm.getQueue().enQueue(track.getAudioTrack());
            return getEmbedObject(
                    track.getAudioTrack(),
                    "Song requested",
                    true,
                    gmm
            );
        }
    }

    /**
     * This will return an embed object to display on discord.
     *
     * @param track     - The track that is being displayed.
     * @param desc      - The description of the embed object.
     * @param isRequest - this determines id the display is a request display.
     * @return EmbedObject
     */
    public EmbedMessage getEmbedObject(AudioTrack track, String desc, boolean isRequest, GuildMusicManager gmm) {
        val songUrlField = EmbedField.builder()
                .name("Song URL")
                .value(track.getInfo().uri)
                .inline(false)
                .build();

        val songLengthField = EmbedField.builder()
                .name("Song Length")
                .value(getSongLengthFormatted(track))
                .inline(true)
                .build();

        val timeField = EmbedField.builder()
                .name(isRequest ? "Wait time" : "Remaining Time")
                .inline(true)
                .value(isRequest ? getQueueTimeFormatted(gmm, track) : getSongLengthRemainingFormatted(track))
                .build();
        return EmbedMessage.builder()
                .title(track.getInfo().title)
                .description(desc)
                .addField(songUrlField)
                .addField(songLengthField)
                .addField(timeField)
                .build();
    }

    /**
     * This will return the queue time in a format of HH:MM:SS
     *
     * @return String
     */
    public String getQueueTimeFormatted(GuildMusicManager gmm, AudioTrack track) {
        long queueTime = getLengthOfQueue(gmm, track);
        return getTimeFormatted(queueTime);
    }

    /**
     * This will return the track's length in a format of HH:MM:SS
     *
     * @param track - The track you want to format
     * @return String
     */
    public String getSongLengthFormatted(AudioTrack track) {
        long queueTime = track.getDuration();
        return getTimeFormatted(queueTime);
    }

    /**
     * This will return the track's remaining length in a format of HH:MM:SS
     *
     * @param track - The track you want to format
     * @return String
     */
    public String getSongLengthRemainingFormatted(AudioTrack track) {
        long queueTime = track.getDuration() - track.getPosition();
        return getTimeFormatted(queueTime);
    }

    private String getTimeFormatted(long time) {
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);

        return (hours < 10 ? "0" + hours : hours)
                + ":" + (minutes < 10 ? "0" + minutes : minutes)
                + ":" + (seconds < 10 ? "0" + seconds : seconds);
    }

    /**
     * This will return the current length of the queue.
     *
     * @param gmm - the guild music manager
     * @return long
     */
    public long getLengthOfQueue(GuildMusicManager gmm, AudioTrack requestedTrack) {
        AtomicLong time = new AtomicLong();
        AudioTrack currentTrack = gmm.getPlayer().getPlayingTrack();
        if (currentTrack != null) {
            if (currentTrack != requestedTrack) {
                time.set(currentTrack.getDuration() - currentTrack.getPosition());
            }
        }
        for (AudioTrack track : gmm.getQueue().getUnderlyingQueue()) {
            if (track != requestedTrack) {
                time.addAndGet(track.getDuration() - track.getPosition());
            }
        }
        return time.get();
    }
}
