package net.babblebot.musicplugin.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.babblebot.api.command.ICommandContext;
import net.babblebot.api.obj.message.discord.DiscordMessage;
import net.babblebot.discord.obj.factories.DiscordGuildFactory;
import net.babblebot.musicplugin.music.GuildMusicManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.babblebot.musicplugin.service.MusicMangerService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;

import java.util.Optional;

/**
 * Command that requires the bot to be summoned
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public abstract class SummonedCommand<T> {
    private final MusicMangerService service;
    private final DiscordGuildFactory discordGuildFactory;

    public final T exec(ICommandContext commandContext, DiscordMessage message) {
        GuildMusicManager gmm = service.getMusicManager(message.getGuild());
        if (!gmm.isHasBeenSummoned()) {
            commandContext.getCommandResponse()
                    .sendString("Bot has not been summoned! please summon it");
            return null;
        }

        Optional<VoiceChannel> botChannelOpt = Optional.ofNullable(gmm.getAudioManager().getConnectedChannel())
                .map(AudioChannelUnion::asVoiceChannel);

        Optional<Member> member =
                discordGuildFactory.makeInternalFromId(message.getGuild().getId().toLong())
                        .map(g -> g.getMemberById(message.getAuthor().getId().toLong()));
        Optional<VoiceChannel> channelOpt = member.map(Member::getVoiceState)
                .map(GuildVoiceState::getChannel)
                .map(AudioChannelUnion::asVoiceChannel);

        if (channelOpt.isPresent()) {
            val channel = channelOpt.get();
            if (botChannelOpt.isPresent() && botChannelOpt.get().getId().equals(channel.getId())) {
                return exec(commandContext, message, gmm, channel);
            } else {
                commandContext.getCommandResponse()
                        .sendString("You are not in the voice channel the bot is in!");
                return null;
            }
        } else {
            commandContext.getCommandResponse()
                    .sendString("You are not in a voice channel, cannot use music-bot commands");
            return null;
        }
    }

    protected abstract T exec(ICommandContext commandContext, DiscordMessage message,
                              GuildMusicManager gmm, VoiceChannel channel);
}
