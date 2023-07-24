package com.example.exampleplugin.command;

import com.example.exampleplugin.model.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.babblebot.api.command.ICommandContext;
import net.babblebot.api.obj.message.discord.DiscordMessage;
import net.babblebot.api.obj.message.discord.embed.EmbedMessage;
import org.springframework.stereotype.Component;

/**
 * Edit me
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ListPeopleCommand {
    private final PersonRepository repository;

    public EmbedMessage execute(DiscordMessage message, ICommandContext ctx) {
        val tests = repository.findAll();
        val em = EmbedMessage.builder()
                .title("All People")
                .build();
        tests.forEach(t -> em.addField(t.getName(), String.valueOf(t.getId()), false));

        return em;
    }
}
