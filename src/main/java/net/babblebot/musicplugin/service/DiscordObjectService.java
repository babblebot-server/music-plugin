package net.babblebot.musicplugin.service;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.babblebot.api.obj.message.discord.DiscordGuild;
import net.babblebot.api.obj.message.discord.DiscordId;
import net.babblebot.api.obj.message.discord.DiscordUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Edit me
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DiscordObjectService {
    private final GatewayDiscordClient discordClient;

    public Mono<Guild> guildFromDiscordGuild(DiscordGuild guild) {
        return discordClient.getGuildById(discordIdToSnowflake(guild.getId()));
    }

    public Mono<Member> memberFromDiscordUser(DiscordGuild guild, DiscordUser user) {
        val guildId = discordIdToSnowflake(guild.getId());
        val userId = discordIdToSnowflake(user.getId());
        return discordClient.getMemberById(guildId, userId);
    }

    public Snowflake discordIdToSnowflake(DiscordId id) {
        return Snowflake.of(id.toLong());
    }
}
