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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CharacterTest {
    private LinkedHashMap<Character, Optional<Bot>> hashOfCharacters;
    Bot botArchitecte;
    Bot botBishop;
    Bot botMagician;
    Bot botAssassin;
    Bot botKing;
    Bot botThief;
    Bot botMerchant;
    Architect architect;
    Bishop bishop;
    Magician magician;
    Assassin assassin;
    King king;
    Thief thief;
    Merchant merchant;

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
        botThief=spy(new Bot(new Player("thiefPlayer")));
        botMerchant=new Bot(new Player("merchantPlayer"));

        //creation of the characters in game
        architect=new Architect();
        bishop=new Bishop();
        magician=new Magician();
        assassin=new Assassin();
        king=new King();
        thief=new Thief();
        merchant=new Merchant();

        //we set the character of our bot
        botKing.getPlayer().setRole(king);
        botMerchant.getPlayer().setRole(merchant);
        botMagician.getPlayer().setRole(magician);
        botArchitecte.getPlayer().setRole(architect);


        //creation of the hashOfCharacter
        hashOfCharacters.put(architect, Optional.of(botArchitecte));
        hashOfCharacters.put(bishop, Optional.of(botBishop));
        hashOfCharacters.put(magician, Optional.of(botMagician));

        //creation of the bot List
        listOfBot=new ArrayList<>();
        listOfBot.add(botArchitecte);
        listOfBot.add(botBishop);
        listOfBot.add(botMagician);
    }

    @Test
    void assassinatePlayer(){
        //initialize
        hashOfCharacters.put(assassin, Optional.of(botAssassin));
        listOfBot.add(botAssassin);

        //Creation of a mock for bot and the magician becomes the target
        when(botAssassin.selectCharacterForSpell(hashOfCharacters)).thenReturn(magician);
        assassin.spellOfTurn(botAssassin,hashOfCharacters,printC);

        //We verify that the Magician is dead
        assertEquals(false,botMagician.getPlayer().getCharacter().isCharacterIsAlive());

        //We verify that some other character is alive
        assertEquals(true,botArchitecte.getPlayer().getCharacter().isCharacterIsAlive());
    }
    @Test
    void testKingPlayFirst(){
        //initialize
        hashOfCharacters.put(king, Optional.of(botKing));
        listOfBot.add(botKing);

        //The king plays and the players after him plays
        king.spellOfTurn(botKing,hashOfCharacters,printC);
        assertEquals(true,botKing.getBotIsKing());
    }
    @Test
    void testKingEarnsMoney(){
        //initialize
        hashOfCharacters.put(king, Optional.of(botKing));
        listOfBot.add(botKing);

        //The king plays and the players after him plays
        botKing.getPlayer().buildDistrict(new District("Test",1,"Yellow","Nobility"));
        botKing.getPlayer().buildDistrict(new District("Test",1,"Yellow","Nobility"));
        botKing.getPlayer().buildDistrict(new District("Test",1,"Yellow","Nobility"));
        botKing.getPlayer().buildDistrict(new District("Test",1,"Yellow","Nobility"));
        botKing.getPlayer().setGolds(0);
        king.spellOfTurn(botKing,hashOfCharacters,printC);
        assertEquals(4,botKing.getPlayer().getGolds());
    }
    @Test
    void testThiefStealsGolds(){
        //initialize
        hashOfCharacters.put(thief, Optional.of(botThief));
        listOfBot.add(botThief);
        //Setting the golds of our thief and magician
        botThief.getPlayer().setGolds(3);
        botMagician.getPlayer().setGolds(4);
        botMagician.getPlayer().setRole(magician);
        botThief.getPlayer().setRole(thief);
        //Creation of a mock for bot and the magician becomes the target
        when(botThief.selectCharacterForSpell(hashOfCharacters)).thenReturn(magician);
        thief.spellOfTurn(botThief,hashOfCharacters,printC);

        //We verify that the Magician has been stolen
        assertEquals(0,botMagician.getPlayer().getGolds());
        assertEquals(7,botThief.getPlayer().getGolds());
    }
    @Test
    void testMerchantEarnsMoney(){

        //The merchant win 4 gold with his district and 1 gold at the start of his turn
        botMerchant.getPlayer().buildDistrict(new District("Test",1,"Yellow","Trade and Handicrafts"));
        botMerchant.getPlayer().buildDistrict(new District("Test",1,"Yellow","Trade and Handicrafts"));
        botMerchant.getPlayer().buildDistrict(new District("Test",1,"Yellow","Trade and Handicrafts"));
        botMerchant.getPlayer().buildDistrict(new District("Test",1,"Yellow","Trade and Handicrafts"));
        botMerchant.getPlayer().setGolds(0);
        merchant.spellOfTurn(botMerchant,hashOfCharacters,printC);
        assertEquals(5,botMerchant.getPlayer().getGolds());
    }
}