package net.babblebot.musicplugin.service;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.IDiscordFacade;
import net.babblebot.api.obj.message.discord.DiscordGuild;
import net.babblebot.api.obj.message.discord.DiscordId;
import net.babblebot.musicplugin.music.GuildMusicManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Edit me
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MusicMangerService {
    private final Map<DiscordId, GuildMusicManager> musicManagers = new HashMap<>();
    private final IDiscordFacade discordFacade;
    @Getter
    private AudioPlayerManager playerManager;

    public GuildMusicManager getMusicManager(DiscordGuild guild) {
        if (playerManager == null) {
            playerManager = new DefaultAudioPlayerManager();
            playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
            AudioSourceManagers.registerRemoteSources(playerManager);
        }

        if (musicManagers.containsKey(guild.getId())) {
            return musicManagers.get(guild.getId());
        }

        GuildMusicManager guildMusicManager = new GuildMusicManager(playerManager, discordFacade);
        musicManagers.put(guild.getId(), guildMusicManager);

        return guildMusicManager;
    }
}
