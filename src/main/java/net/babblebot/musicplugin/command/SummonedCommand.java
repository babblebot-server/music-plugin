package net.babblebot.musicplugin.command;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.PartialMember;
import discord4j.core.object.entity.channel.VoiceChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.babblebot.api.command.ICommandContext;
import net.babblebot.api.obj.message.discord.DiscordMessage;
import net.babblebot.musicplugin.music.GuildMusicManager;
import net.babblebot.musicplugin.service.DiscordObjectService;
import net.babblebot.musicplugin.service.MusicMangerService;
import reactor.core.publisher.Mono;

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
    private final DiscordObjectService discordObjectService;

    public final T exec(ICommandContext commandContext, DiscordMessage message) {
        GuildMusicManager gmm = service.getMusicManager(message.getGuild());
        if (!gmm.isHasBeenSummoned()) {
            commandContext.getCommandResponse()
                    .sendString("Bot has not been summoned! please summon it");
            return null;
        }

        Mono<Member> member = discordObjectService.memberFromDiscordUser(message.getGuild(), message.getAuthor());
        Optional<VoiceChannel> channelOpt = member.flatMap(PartialMember::getVoiceState)
                .blockOptional()
                .flatMap(vs -> vs.getChannel().blockOptional());

        if (channelOpt.isPresent()) {
            val channel = channelOpt.get();
            val botChannelId = gmm.getVoiceConnection().getChannelId().blockOptional().orElseThrow();
            if (channel.getId().equals(botChannelId)) {
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
