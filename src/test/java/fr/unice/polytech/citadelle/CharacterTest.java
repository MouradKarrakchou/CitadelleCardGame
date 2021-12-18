package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game_character.*;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class CharacterTest {
    private LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters;
    Behaviour botArchitecte;
    Behaviour botBishop;
    Behaviour botMagician;
    Behaviour botAssassin;
    Behaviour botKing;
    Behaviour botThief;
    Behaviour botMerchant;
    Behaviour botWarlord;
    Architect architect;
    Bishop bishop;
    Magician magician;
    Assassin assassin;
    King king;
    Thief thief;
    Merchant merchant;
    Warlord warlord;
    Board board;
    ArrayList<Player> listOfPlayer;

    ArrayList<Behaviour> listOfBehaviour;
    DeckDistrict deckDistrict;

    @BeforeEach
    public void init(){
        hashOfCharacters = new LinkedHashMap<>();
        deckDistrict=new DeckDistrict();
        listOfPlayer=new ArrayList<>();
        board = new Board(listOfPlayer,new ArrayList<Character>(),deckDistrict,new DeckCharacter());
        board.getDeckDistrict().initialise();

        
        //creation of Behaviour
        botArchitecte = new Behaviour(new Player("architectePlayer"), board);
        botBishop =new Behaviour(new Player("bishopPlayer"), board);
        botMagician =spy(new Behaviour(new Player("magicianPlayer"), board));
        botAssassin = new Behaviour(new Player("assassinPlayer"), board);
        botKing=new Behaviour(new Player("kingPlayer"), board);
        botThief=spy(new Behaviour(new Player("thiefPlayer"), board));
        botMerchant=new Behaviour(new Player("merchantPlayer"), board);
        botWarlord=spy(new Behaviour(new Player("warlordPlayer"), board));

        //creation of the characters in game
        architect=new Architect();
        bishop=new Bishop();
        magician=new Magician();
        assassin=new Assassin();
        king=new King();
        thief=new Thief();
        merchant=new Merchant();
        warlord=new Warlord();

        //creation of the list of player
        listOfPlayer.add(botKing.getPlayer());
        listOfPlayer.add(botBishop.getPlayer());
        listOfPlayer.add(botArchitecte.getPlayer());
        listOfPlayer.add(botWarlord.getPlayer());
        listOfPlayer.add(botAssassin.getPlayer());
        listOfPlayer.add(botMagician.getPlayer());
        listOfPlayer.add(botThief.getPlayer());
        listOfPlayer.add(botMerchant.getPlayer());


        //we set the character of our bot
        botKing.getPlayer().setRole(king);
        botMerchant.getPlayer().setRole(merchant);
        botMagician.getPlayer().setRole(magician);
        botArchitecte.getPlayer().setRole(architect);
        botWarlord.getPlayer().setRole(warlord);
        botThief.getPlayer().setRole(thief);
        botAssassin.getPlayer().setRole(assassin);
        botBishop.getPlayer().setRole(bishop);


        //creation of the hashOfCharacter
        hashOfCharacters.put(assassin, Optional.of(botAssassin));
        hashOfCharacters.put(thief, Optional.of(botThief));
        hashOfCharacters.put(magician, Optional.of(botMagician));
        hashOfCharacters.put(king, Optional.of(botKing));
        hashOfCharacters.put(bishop, Optional.of(botBishop));
        hashOfCharacters.put(merchant, Optional.of(botMerchant));
        hashOfCharacters.put(architect, Optional.of(botArchitecte));
        hashOfCharacters.put(warlord, Optional.of(botWarlord));

        //Adding the character in the Board
        board.getListOfCharacter().add(assassin);
        board.getListOfCharacter().add(thief);
        board.getListOfCharacter().add(magician);
        board.getListOfCharacter().add(king);
        board.getListOfCharacter().add(bishop);
        board.getListOfCharacter().add(merchant);
        board.getListOfCharacter().add(architect);
        board.getListOfCharacter().add(warlord);

        //creation of the bot List
        listOfBehaviour=new ArrayList<>();
        listOfBehaviour.add(botArchitecte);
        listOfBehaviour.add(botBishop);
        listOfBehaviour.add(botMagician);


    }

    @RepeatedTest(1)
    @Test
    void assassinatePlayer(){
        //initialize
        botAssassin = mock(Behaviour.class);

        listOfBehaviour.add(botAssassin);

        //Creation of a mock for bot and the magician becomes the target
        when(botAssassin.selectCharacterForSpell(hashOfCharacters)).thenReturn(magician);
        assassin.spellOfTurn(botAssassin,hashOfCharacters);

        //We verify that the Magician is dead
        assertEquals(false,botMagician.getPlayer().getCharacter().getCharacterisAlive());

        //We verify that some other character is alive
        assertEquals(true,botArchitecte.getPlayer().getCharacter().getCharacterisAlive());
    }
    
    @RepeatedTest(1)
    void testKingPlayFirst(){
        //initialize
        listOfBehaviour.add(botKing);

        //The king plays and the players after him plays
        king.spellOfTurn(botKing,hashOfCharacters);
        assertEquals(true,botKing.getBehaviourIsKing());
    }
    
    @RepeatedTest(1)
    void testKingEarnsMoney(){
        //initialize
        listOfBehaviour.add(botKing);

        //The king plays and the players after him plays
        botKing.getPlayer().buildDistrict(new District("Test",1,"Yellow","Nobility"));
        botKing.getPlayer().buildDistrict(new District("Test",1,"Yellow","Nobility"));
        botKing.getPlayer().buildDistrict(new District("Test",1,"Yellow","Nobility"));
        botKing.getPlayer().buildDistrict(new District("Test",1,"Yellow","Nobility"));
        botKing.getPlayer().setGolds(0);
        king.spellOfTurn(botKing,hashOfCharacters);
        assertEquals(4,botKing.getPlayer().getGolds());
    }
    
    @RepeatedTest(1)
    void testThiefStealsGolds(){
        //initialize
        listOfBehaviour.add(botThief);
        //Setting the golds of our thief and magician
        botThief.getPlayer().setGolds(3);
        botMagician.getPlayer().setGolds(4);
        botMagician.getPlayer().setRole(magician);
        botThief.getPlayer().setRole(thief);
        //Creation of a mock for bot and the magician becomes the target
        when(botThief.selectCharacterForSpell(hashOfCharacters)).thenReturn(magician);
        thief.spellOfTurn(botThief,hashOfCharacters);

        //We verify that the Magician has been stolen
        assertEquals(0,botMagician.getPlayer().getGolds());
        assertEquals(7,botThief.getPlayer().getGolds());
    }
    
    @RepeatedTest(1)
    @Test
    void testMerchantEarnsMoney(){

        //The merchant win 4 gold with his district and 1 gold at the start of his turn
        botMerchant.getPlayer().buildDistrict(new District(" ",1," ","Trade and Handicrafts"));
        botMerchant.getPlayer().buildDistrict(new District(" ",1," ","Trade and Handicrafts"));
        botMerchant.getPlayer().buildDistrict(new District(" ",1," ","Trade and Handicrafts"));
        botMerchant.getPlayer().buildDistrict(new District(" ",1," ","Trade and Handicrafts"));
        botMerchant.getPlayer().setGolds(0);
        merchant.spellOfTurn(botMerchant,hashOfCharacters);
        assertEquals(5,botMerchant.getPlayer().getGolds());
    }
    
    @RepeatedTest(1)
    @Test
    void testBishopEarnsMoney(){

        //The bishop win 4 gold with his district and 1 gold at the start of his turn
        botBishop.getPlayer().buildDistrict(new District(" ",1," ","Religion"));
        botBishop.getPlayer().buildDistrict(new District(" ",1," ","Religion"));
        botBishop.getPlayer().buildDistrict(new District(" ",1," ","Religion"));
        botBishop.getPlayer().buildDistrict(new District(" ",1," ","Religion"));
        botBishop.getPlayer().setGolds(1);
        bishop.spellOfTurn(botBishop,hashOfCharacters);
        assertEquals(5,botBishop.getPlayer().getGolds());
    }
    
    @RepeatedTest(1)
    @Test
    void testArchitectTakes2Cards(){
        //We verify that the Value of behaviourIsArchitect becomes true
        architect.spellOfTurn(botArchitecte,hashOfCharacters);
        assertEquals(2, botArchitecte.getPlayer().getDistrictCards().size());
    }
    
    @RepeatedTest(1)
    @Test
    void testMagicianSwapWithAnotherCharacter(){
        ArrayList<District> cardsToSwap=new ArrayList<>();
        botMerchant.getPlayer().getDistrictCards().add(new District("MerchantDistrict",0," "," "));
        botMagician.getPlayer().getDistrictCards().add(new District("MagicianDistrict",0," "," "));
        when(botMagician.chooseMagicianAction()).thenReturn(cardsToSwap);
        when(botMagician.selectCharacterForSpell(hashOfCharacters)).thenReturn(merchant);
        magician.spellOfTurn(botMagician,hashOfCharacters);
        assertEquals("MagicianDistrict",botMerchant.getPlayer().getDistrictCards().get(0).getName());
        assertEquals("MerchantDistrict",botMagician.getPlayer().getDistrictCards().get(0).getName());
    }
    
    @RepeatedTest(1)
    @Test
    void testMagicianSwapWithTheDeck(){
        ArrayList<District> cardsToSwap=new ArrayList<>();
        District districtToSwap=new District("MagicianDistrict2",0," "," ");
        botMagician.getPlayer().getDistrictCards().add(new District("MagicianDistrict0",0," "," "));
        botMagician.getPlayer().getDistrictCards().add(new District("MagicianDistrict1",0," "," "));
        botMagician.getPlayer().getDistrictCards().add(districtToSwap);
        cardsToSwap.add(districtToSwap);
        when(botMagician.chooseMagicianAction()).thenReturn(cardsToSwap);
        when(botMagician.pickCard()).thenReturn(new District("MagicianDistrict3",0," "," "));
        magician.spellOfTurn(botMagician,hashOfCharacters);
        assertEquals("MagicianDistrict0",botMagician.getPlayer().getDistrictCards().get(0).getName());
        assertEquals("MagicianDistrict1",botMagician.getPlayer().getDistrictCards().get(1).getName());
        assertEquals("MagicianDistrict3",botMagician.getPlayer().getDistrictCards().get(2).getName());
    }
    
    @RepeatedTest(1)
    @Test
    void testWarlordDestroyDistrict(){
        Player player=new Player("playerDistrictToDestroy");
        player.setGolds(4);
        District district= new District("DistrictToBeDestroyed",2,"Green",null);
        player.buildDistrict(district);
        player.buildDistrict(new District("DistrictToNotBeDestroyed",2,"Green",null));
        board.getListOfPlayer().add(player);
        board.getListOfPlayer().add(botWarlord.getPlayer());
        botWarlord.getPlayer().setGolds(2);
        when(botWarlord.selectPlayerForWarlord()).thenReturn(player);
        when(botWarlord.chooseDistrictToDestroy(player)).thenReturn(district);
        warlord.spellOfTurn(botWarlord,hashOfCharacters);
        assertEquals(false,player.getCity().getBuiltDistrict().contains(district));
        assertEquals(1,botWarlord.getPlayer().getGolds());
    }


}
