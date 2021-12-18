package fr.unice.polytech.citadelle.game;

import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static fr.unice.polytech.citadelle.game_engine.Initializer.NUMBER_OF_PLAYER;

/**
 * A DeckCharacter is composed of all the character cards in the game.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class DeckCharacter {
    private final ArrayList<Character> deckOfCharacters;
    private final ArrayList<Character> burnedAndVisibleCharacters;
    private Character hiddenCard;
    private final int nbPlayers;

    public DeckCharacter(){
        deckOfCharacters = new ArrayList<>();
        burnedAndVisibleCharacters = new ArrayList<>();
        nbPlayers = NUMBER_OF_PLAYER;
    }

    /**
     * Custom constructor used on the test class to set different values for the number of players.
     * @param nbPlayers The number of players.
     */
    public DeckCharacter(int nbPlayers) {
        deckOfCharacters = new ArrayList<>();
        burnedAndVisibleCharacters = new ArrayList<>();
        this.nbPlayers = nbPlayers;
    }

    public Character chooseRandomCharacter() {
        Random random = new Random();
        return deckOfCharacters.remove(random.nextInt(deckOfCharacters.size()));
    }

    /**
     * @return A list of character cards a player can actually pick.
     */
	public ArrayList<Character> getDeckCharacter() {
		return deckOfCharacters;
	}

    /**
     * @return The card hid at the beginning of a round. The last player to play should be able to take it.
     */
    public Character getHiddenCard(){
        return hiddenCard;
    }

    /**
     * @return A list of characters cards a player can see but can't pick (cards are burned).
     */
    public ArrayList<Character> getBurnedAndVisibleCharacters(){
        return burnedAndVisibleCharacters;
    }

	public int getSize(){
		return deckOfCharacters.size();
	}

    /**
     * Called once at the beginning of each round.
     * Shuffle the entire deck of charters.
     */
    public void shuffleDeck(){
        Collections.shuffle(deckOfCharacters);
        PrintCitadels.printShuffle("deck of characters");
    }

    /**
     * According to the number of players, the number of burned visible characters will not be the same.
     * @return The number of characters we need to burn before starting taking a card.
     */
    public int calculateNbCardToBurn(){
        return switch (nbPlayers) {
            case 4 -> 2;
            case 5 -> 1;
            default -> 0;
        };
    }

    /**
     * Burn a certain number of cards according to the number of players.
     */
    public void burnCharacters(){
        int nbCardToBurn = calculateNbCardToBurn();
        Character kingCard = removeKingFromDeck();

        for(int i = 0; i < nbCardToBurn; i++){
            Character burnedCharacter = deckOfCharacters.remove(0);
            burnedAndVisibleCharacters.add(burnedCharacter);
            PrintCitadels.printBurned(burnedCharacter);
        }

        // If the king is not the hidden card
        if (kingCard != null)
            deckOfCharacters.add(kingCard);
    }

    /**
     * Remove the king form the list to properly burn characters card.
     * @return The removed king card.
     */
    public Character removeKingFromDeck(){
        int sizeOfDeck = deckOfCharacters.size();
        for (int index = 0; index < sizeOfDeck; index++) {
            if (deckOfCharacters.get(index).getName().equals("King")){
                return deckOfCharacters.remove(index);
            }
        }
        return null;
    }

    /**
     * Add the hidden card to deck when the last player of a game of 7 players is playing.
     */
    public void checkAndUpdateDeckForLastPlayer(){
        if (canPickTheHiddenCard())
            deckOfCharacters.add(hiddenCard);
    }

    /**
     * According to the character selection advancement, the last player could take the hidden card.
     * @return True/False - The player can take the hidden card.
     */
    public boolean canPickTheHiddenCard(){
        return deckOfCharacters.size() == 1;
    }

    /**
     * Hide the last card of the actual deck of character.
     * The king character should not bo hid, so the next card will be hid.

     */
    public void hideCard(){
        int sizeOfDeck = deckOfCharacters.size();
        hiddenCard = deckOfCharacters.remove(sizeOfDeck - 1);
        PrintCitadels.printHidCharacter(hiddenCard);
    }

    /**
     * The 8 character cards are shuffled, face down, and a certain number are discarded and put aside.
     *
     * The player who has the card "Crown" takes the remaining character cards and secretly chooses one,
     * which he places in front of him face down.
     *
     * He then passes the deck of cards to his neighbor on the left, who does the same, and so on.
     *
     * When the last player has taken one of the last two cards,
     * he places the remaining card, face down, with the cards discarded at the start of the round.
     */
    public void deckStartRound(){
        shuffleDeck();
        hideCard();
        burnCharacters();
    }
}
