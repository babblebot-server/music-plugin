package net.babblebot.musicplugin.music;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ben.davies99@outlook.com (Ben Davies)
 * @since 1.0.0
 */
@Slf4j
public class MusicTrack {

    @Getter
    protected final AudioTrack audioTrack;

    @Getter
    protected final AudioPlaylist playlist;

    private MusicTrack(AudioTrack audioTrack, AudioPlaylist playlist) {
        this.audioTrack = audioTrack;
        this.playlist = playlist;
    }

    public MusicTrack(AudioTrack track) {
        this(track, null);
    }

    public MusicTrack(AudioPlaylist playlist) {
        this(null, playlist);
    }

    /**
     * Check whether it is a playlist
     *
     * @return boolean
     */
    public boolean isPlaylist() {
        return playlist != null;
    }


    @Override
    public String toString() {
        return "MusicTrack{" +
                "audioTrack=" + audioTrack +
                ", playlist=" + playlist +
                '}';
    }
}
