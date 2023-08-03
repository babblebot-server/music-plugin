package net.babblebot.musicplugin.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.babblebot.api.obj.message.discord.DiscordMessage;
import net.babblebot.discord.obj.factories.DiscordGuildFactory;
import net.babblebot.musicplugin.music.GuildMusicManager;
import net.babblebot.musicplugin.service.MusicMangerService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

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
    private final DiscordGuildFactory discordGuildFactory;

    public String exec(DiscordMessage message) {
        GuildMusicManager gmm = mangerService.getMusicManager(message.getGuild());
        Optional<Guild> guild = discordGuildFactory.makeInternalFromId(message.getGuild().getId().toLong());
        Optional<Member> member =
                discordGuildFactory.makeInternalFromId(message.getGuild().getId().toLong())
                        .map(g -> g.getMemberById(message.getAuthor().getId().toLong()));
        Optional<VoiceChannel> channelOpt = member.map(Member::getVoiceState)
                .map(GuildVoiceState::getChannel)
                .map(AudioChannelUnion::asVoiceChannel);

        if (guild.isEmpty()) {
            return "Guild: " + message.getGuild().getName() + " not found";
        }

        if (channelOpt.isPresent()) {
            val channel = channelOpt.get();
            AudioManager manager = guild.get().getAudioManager();
            manager.setSendingHandler(gmm.getAudioProvider());
            manager.openAudioConnection(channel);
            gmm.setHasBeenSummoned(true);
            gmm.setAudioManager(manager);
            return "I have been summoned to: " + channel.getName();
        } else {
            return "You are not in a voice channel, please join channel first!";
        }
    }
}
