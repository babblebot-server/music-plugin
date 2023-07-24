package com.example.exampleplugin.command;

import com.example.exampleplugin.model.Person;
import com.example.exampleplugin.model.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.command.ICommandContext;
import net.babblebot.api.obj.message.discord.embed.EmbedMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

/**
 * Edit me
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Slf4j
public class AddPersonCommandTest {
    AddPersonCommand addPersonCommand;
    ICommandContext ctx;
    PersonRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = Mockito.mock(PersonRepository.class);
        Mockito.when(repository.saveAndFlush(any(Person.class)))
                .thenAnswer(a -> a.getArgument(0));
        addPersonCommand = new AddPersonCommand(repository);

        ctx = Mockito.mock(ICommandContext.class);
    }

    @Test
    @DisplayName("Test Add Person Command")
    void testAddPersonCommand() {
        Mockito.when(ctx.getParameter("name"))
                .thenReturn("John");
        EmbedMessage msg = addPersonCommand.execute(null, ctx);
        Mockito.verify(repository).saveAndFlush(any(Person.class));
        Person p = Person.builder().name("John").build();
        assertEquals(p.toString(), msg.getDescription());
    }
}
