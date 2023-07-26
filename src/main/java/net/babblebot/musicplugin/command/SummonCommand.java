package net.babblebot.musicplugin.command;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.object.entity.PartialMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.babblebot.api.obj.message.discord.DiscordMessage;
import net.babblebot.musicplugin.music.GuildMusicManager;
import net.babblebot.musicplugin.service.DiscordObjectService;
import net.babblebot.musicplugin.service.MusicMangerService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class SummonCommand {
    private final MusicMangerService mangerService;
    private final DiscordObjectService discordObjectService;

    public String exec(DiscordMessage message) {
        GuildMusicManager gmm = mangerService.getMusicManager(message.getGuild());
        Mono<Member> member = discordObjectService.memberFromDiscordUser(message.getGuild(), message.getAuthor());
        Optional<VoiceChannel> channelOpt = member.flatMap(PartialMember::getVoiceState)
                .blockOptional()
                .flatMap(vs -> vs.getChannel().blockOptional());

        if (channelOpt.isPresent()) {
            val channel = channelOpt.get();
            val connectionOpt = channel.join()
                    .withProvider(gmm.getAudioProvider())
                    .blockOptional();
            if (connectionOpt.isPresent()) {
                val connection = connectionOpt.get();
                gmm.setHasBeenSummoned(true);
                gmm.setVoiceConnection(connection);
                return "I have been summoned to: " + channel.getName();
            } else {
                return "Unable to join voice channel: " + channel.getName();
            }
        } else {
            return "You are not in a voice channel, please join channel first!";
        }
    }
}
