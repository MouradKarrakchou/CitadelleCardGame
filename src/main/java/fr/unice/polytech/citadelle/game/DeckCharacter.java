package fr.unice.polytech.citadelle.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * A DeckCharacter is composed of all the character cards in the game.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class DeckCharacter {
    private final ArrayList<Character> deckCharacter;

    public DeckCharacter() {
        deckCharacter = new ArrayList<>();
    }

    public Character chooseRandomCharacter() {
        Random random = new Random();
        return deckCharacter.remove(random.nextInt(deckCharacter.size()));
    }

	public ArrayList<Character> getDeckCharacter() {
		return deckCharacter;
	}

	public int getSize(){
		return deckCharacter.size();
	}
}
