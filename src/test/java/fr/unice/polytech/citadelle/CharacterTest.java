package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.characters_class.Architect;
import fr.unice.polytech.citadelle.characters_class.Bishop;
import fr.unice.polytech.citadelle.characters_class.Magician;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CharacterTest {

    @Test
    void assassinePlayer(){
        Architect architect=new Architect();
        Bishop bishop=new Bishop();
        Magician magician=new Magician();
        ArrayList<Character>=new ArrayList<>();
        Bot bot =mock(Bot.class);
        when(bot.selectCharacterForAssassin()).thenReturn(magician)
    }
}
