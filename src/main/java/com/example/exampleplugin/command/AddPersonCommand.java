package com.example.exampleplugin.command;

import com.example.exampleplugin.model.Person;
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
public class AddPersonCommand {
    private final PersonRepository repository;

    public EmbedMessage execute(DiscordMessage message, ICommandContext ctx) {
       val e = repository.saveAndFlush(Person.builder()
                .name(ctx.getParameter("name"))
                .build());

        return EmbedMessage.builder()
                .title("Saved")
                .description(e.toString())
                .build();
    }
}
