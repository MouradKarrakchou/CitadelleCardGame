package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.characters_class.*;
import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.output.PrintCitadels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CharacterTest {
    private LinkedHashMap<Character, Bot> hashOfCharacters;
    public static final int ASSASIN_INDEX = 0;
    public static final int THIEF_INDEX = 1;
    public static final int MAGICIAN_INDEX = 2;
    public static final int KING_INDEX = 3;
    public static final int BISHOP_INDEX = 4;
    public static final int MERCHANT_INDEX = 5;
    public static final int ARCHITECT_INDEX = 6;
    public static final int WARLORD_INDEX = 7;
    ArrayList<Character> characterInGame;
    Board game;
    Bot botArchitecte;
    Bot botBishop;
    Bot botMagician;
    Bot botAssassin;
    Bot botKing;
    Architect architect;
    Bishop bishop;
    Magician magician;
    Assassin assassin;
    King king;
    PrintCitadels printC;
    ArrayList<Bot> listOfBot;

    @BeforeEach
    public void init(){
        hashOfCharacters = new LinkedHashMap<>();
        printC=new PrintCitadels();

        //creation of Bot
        botArchitecte = new Bot(new Player("architectePlayer"));
        botBishop =new Bot(new Player("bishopPlayer"));
        botMagician = new Bot(new Player("magicianPlayer"));
        botAssassin = mock(Bot.class);
        botKing=new Bot(new Player("kingPlayer"));

        //creation of the characters in game
        architect=new Architect();
        bishop=new Bishop();
        magician=new Magician();
        assassin=new Assassin();
        king=new King();

        //we set the character of our bot
        botKing.getPlayer().setRole(king);

        //creation of the hashOfCharacter
        hashOfCharacters.put(architect, botArchitecte);
        hashOfCharacters.put(bishop, botBishop);
        hashOfCharacters.put(magician, botMagician);

        //creation of the bot List
        listOfBot=new ArrayList<>();
        listOfBot.add(botArchitecte);
        listOfBot.add(botBishop);
        listOfBot.add(botMagician);
    }

    @Test
    void assassinatePlayer(){
        //initialize
        hashOfCharacters.put(assassin, botAssassin);
        listOfBot.add(botAssassin);

        //Creation of a mock for bot and the magician becomes the target
        when(botAssassin.selectCharacterForAssassin(hashOfCharacters)).thenReturn(magician);
        assassin.spellOfTurn(botAssassin,hashOfCharacters,printC);

        //We verify that the Magician is dead
        assertEquals(false,botMagician.getBotIsAlive());

        //We verify that some other character is alive
        assertEquals(true,botArchitecte.getBotIsAlive());
    }
    @Test
    void testKingPlayFirst(){
        //initialize
        hashOfCharacters.put(king, botKing);
        listOfBot.add(botKing);

        //The king plays and the players after him plays
        king.spellOfTurn(botKing,hashOfCharacters,printC);
        assertEquals(botKing.getBotIsKing(),true);
    }

}
