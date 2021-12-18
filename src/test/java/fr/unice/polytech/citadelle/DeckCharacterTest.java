package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game_character.*;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_engine.Initializer;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DeckCharacterTest {
    final int GAME_OF_4_PLAYERS = 4;
    final int GAME_OF_5_PLAYERS = 5;
    final int GAME_OF_6_PLAYERS = 6;
    final int GAME_OF_7_PLAYERS = 7;
    final int GAME_OF_8_PLAYERS = 8;

    private Assassin theAssassin = new Assassin();
    private Thief theThief = new Thief();
    private Magician theMagician = new Magician();
    private King theKing = new King();
    private Bishop theBishop = new Bishop();
    private Merchant theMerchant = new Merchant();
    private Architect theArchitect = new Architect();
    private Warlord theWarlord = new Warlord();

    private DeckCharacter deckOfCharacters;

    @RepeatedTest(1)
    @Test
    public void calculateNbCardToBurnTest() {
        deckOfCharacters = new DeckCharacter(GAME_OF_4_PLAYERS);
        assertEquals(2, deckOfCharacters.calculateNbCardToBurn());

        deckOfCharacters = new DeckCharacter(GAME_OF_5_PLAYERS);
        assertEquals(1, deckOfCharacters.calculateNbCardToBurn());

        deckOfCharacters = new DeckCharacter(GAME_OF_6_PLAYERS);
        assertEquals(0, deckOfCharacters.calculateNbCardToBurn());

        deckOfCharacters = new DeckCharacter(GAME_OF_7_PLAYERS);
        assertEquals(0, deckOfCharacters.calculateNbCardToBurn());

        deckOfCharacters = new DeckCharacter(GAME_OF_8_PLAYERS);
        assertEquals(0, deckOfCharacters.calculateNbCardToBurn());
    }

    @RepeatedTest(1)
    @Test
    public void burnCharacters4PlayersTest() {
        deckOfCharacters = new DeckCharacter(GAME_OF_4_PLAYERS);

        ArrayList<Character> customListOfCharacters = new ArrayList<>();
        customListOfCharacters.add(theWarlord);
        customListOfCharacters.add(theKing);
        customListOfCharacters.add(theThief);
        customListOfCharacters.add(theArchitect);
        customListOfCharacters.add(theMagician);
        customListOfCharacters.add(theMerchant);
        customListOfCharacters.add(theBishop);
        customListOfCharacters.add(theAssassin);
        deckOfCharacters.getDeckCharacter().addAll(customListOfCharacters);

        // Check if there is 8 cards in the deck of characters.
        assertEquals(8, deckOfCharacters.getDeckCharacter().size());

        deckOfCharacters.burnCharacters();

        // Check if 2 cards has been burned.
        assertEquals(2, deckOfCharacters.getBurnedAndVisibleCharacters().size());

        // Check if the warlord and the thief has been burned.
        assertTrue(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theWarlord));
        assertTrue(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theThief));

        // Check if the architect and the king has not been burned.
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theKing));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theArchitect));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theMagician));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theMerchant));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theBishop));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theAssassin));

        // Check the nex size of the deck
        assertEquals(6, deckOfCharacters.getDeckCharacter().size());
    }

    @RepeatedTest(1)
    @Test
    public void burnCharacters5PlayersTest() {
        deckOfCharacters = new DeckCharacter(GAME_OF_5_PLAYERS);

        ArrayList<Character> customListOfCharacters = new ArrayList<>();
        customListOfCharacters.add(theKing);
        customListOfCharacters.add(theMerchant);
        customListOfCharacters.add(theThief);
        customListOfCharacters.add(theArchitect);
        customListOfCharacters.add(theMagician);
        customListOfCharacters.add(theWarlord);
        customListOfCharacters.add(theBishop);
        customListOfCharacters.add(theAssassin);
        deckOfCharacters.getDeckCharacter().addAll(customListOfCharacters);

        // Check if there is 8 cards in the deck of characters.
        assertEquals(8, deckOfCharacters.getDeckCharacter().size());

        deckOfCharacters.burnCharacters();

        // Check if 1 card has been burned.
        assertEquals(1, deckOfCharacters.getBurnedAndVisibleCharacters().size());

        // Check if the merchant card is burned.
        assertTrue(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theMerchant));

        // Check if the sevenths last cards are in the deck.
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theKing));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theWarlord));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theArchitect));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theMagician));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theBishop));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theAssassin));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theThief));

        // Check the nex size of the deck
        assertEquals(7, deckOfCharacters.getDeckCharacter().size());
    }

    @RepeatedTest(1)
    @Test
    public void burnCharacters6PlayersTest() {
        deckOfCharacters = new DeckCharacter(GAME_OF_6_PLAYERS);

        ArrayList<Character> customListOfCharacters = new ArrayList<>();
        customListOfCharacters.add(theKing);
        customListOfCharacters.add(theMerchant);
        customListOfCharacters.add(theThief);
        customListOfCharacters.add(theArchitect);
        customListOfCharacters.add(theMagician);
        customListOfCharacters.add(theWarlord);
        customListOfCharacters.add(theBishop);
        customListOfCharacters.add(theAssassin);
        deckOfCharacters.getDeckCharacter().addAll(customListOfCharacters);

        // Check if there is 8 cards in the deck of characters.
        assertEquals(8, deckOfCharacters.getDeckCharacter().size());

        deckOfCharacters.burnCharacters();

        // Check if no card has been burned.
        assertEquals(0, deckOfCharacters.getBurnedAndVisibleCharacters().size());


        // Check if all the cards are still in the deck.
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theMerchant));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theKing));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theWarlord));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theArchitect));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theMagician));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theBishop));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theAssassin));
        assertFalse(deckOfCharacters.getBurnedAndVisibleCharacters().contains(theThief));

        // Check the new size of the deck
        assertEquals(8, deckOfCharacters.getDeckCharacter().size());
    }

    @RepeatedTest(1)
    @Test
    public void hideTheLastCard() {
        deckOfCharacters = new DeckCharacter(GAME_OF_4_PLAYERS);

        ArrayList<Character> customListOfCharacters = new ArrayList<>();
        customListOfCharacters.add(theMerchant);
        customListOfCharacters.add(theThief);
        customListOfCharacters.add(theArchitect);
        customListOfCharacters.add(theMagician);
        customListOfCharacters.add(theKing);
        customListOfCharacters.add(theWarlord);
        customListOfCharacters.add(theBishop);
        customListOfCharacters.add(theAssassin);
        deckOfCharacters.getDeckCharacter().addAll(customListOfCharacters);

        // Check if there is 8 cards in the deck of characters.
        assertEquals(8, deckOfCharacters.getDeckCharacter().size());

        deckOfCharacters.hideCard();

        // Check if there is 7 cards in the deck of characters.
        assertEquals(7, deckOfCharacters.getDeckCharacter().size());

        // Check if the hidden card is the Assassin
        assertEquals(theAssassin, deckOfCharacters.getHiddenCard());
    }

    @RepeatedTest(1)
    @Test
    public void deckStartRound4PlayersTest() {
        deckOfCharacters = new DeckCharacter(GAME_OF_4_PLAYERS);

        ArrayList<Character> customListOfCharacters = new ArrayList<>();
        customListOfCharacters.add(theKing);
        customListOfCharacters.add(theMerchant);
        customListOfCharacters.add(theThief);
        customListOfCharacters.add(theArchitect);
        customListOfCharacters.add(theMagician);
        customListOfCharacters.add(theWarlord);
        customListOfCharacters.add(theBishop);
        customListOfCharacters.add(theAssassin);
        deckOfCharacters.getDeckCharacter().addAll(customListOfCharacters);

        // Check if there is 8 cards in the deck of characters.
        assertEquals(8, deckOfCharacters.getDeckCharacter().size());

        deckOfCharacters.deckStartRound();

        // Check if 2 cards has been burned.
        assertEquals(2, deckOfCharacters.getBurnedAndVisibleCharacters().size());

        // Check the new size of the deck
        assertEquals(5, deckOfCharacters.getDeckCharacter().size());

        // Check if one card has been hid
        assertNotNull(deckOfCharacters.getHiddenCard());
    }
}