package fr.unice.polytech.citadelle.game;

import java.util.ArrayList;
import java.util.Random;

import fr.unice.polytech.citadelle.game_engine.Initialiser;
import fr.unice.polytech.citadelle.game_engine.RoundManager;

/**
 * A DeckCharacter is composed of all the character cards in the game.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class DeckCharacter {
    private final ArrayList<Character> deckCharacter;

    public DeckCharacter(ArrayList<Character> listOfCharacters) {
        deckCharacter = new ArrayList<>();
    }

    public void initialise(ArrayList<Character> listOfCharacters) {
        deckCharacter.clear();
        deckCharacter.add(listOfCharacters.get(Initialiser.ASSASIN_INDEX));
        deckCharacter.add(listOfCharacters.get(Initialiser.THIEF_INDEX));
        deckCharacter.add(listOfCharacters.get(Initialiser.MAGICIAN_INDEX));
        deckCharacter.add(listOfCharacters.get(Initialiser.KING_INDEX));
        deckCharacter.add(listOfCharacters.get(Initialiser.BISHOP_INDEX));
        deckCharacter.add(listOfCharacters.get(Initialiser.MERCHANT_INDEX));
        deckCharacter.add(listOfCharacters.get(Initialiser.ARCHITECT_INDEX));
        deckCharacter.add(listOfCharacters.get(Initialiser.WARLORD_INDEX));
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
