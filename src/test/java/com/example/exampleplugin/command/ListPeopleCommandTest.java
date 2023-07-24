package com.example.exampleplugin.command;

import com.example.exampleplugin.model.Person;
import com.example.exampleplugin.model.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import net.babblebot.api.obj.message.discord.embed.EmbedMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Edit me
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Slf4j
public class ListPeopleCommandTest {
    ListPeopleCommand listPeopleCommand;
    PersonRepository repository;
    List<Person> people = new LinkedList<>();

    @BeforeEach
    void beforeEach() {
        repository = Mockito.mock(PersonRepository.class);
        people.add(Person.builder().name("Tony").id(1).build());
        people.add(Person.builder().name("John").id(2).build());

        Mockito.when(repository.findAll()).thenReturn(people);
        listPeopleCommand = new ListPeopleCommand(repository);
    }

    @Test
    @DisplayName("Test List People Command")
    void testListPeopleCommand() {
        EmbedMessage msg = listPeopleCommand.execute(null, null);
        Mockito.verify(repository).findAll();
        assertEquals(people.size(), msg.getFields().size());
        for (int i = 0; i < people.size(); i++) {
            assertEquals(people.get(i).getName(), msg.getFields().get(i).getName());
            assertEquals(String.valueOf(people.get(i).getId()), msg.getFields().get(i).getValue());
        }
    }
}
