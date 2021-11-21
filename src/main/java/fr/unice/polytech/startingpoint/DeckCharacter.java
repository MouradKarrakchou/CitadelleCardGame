package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.characters_class.Character;

import java.util.ArrayList;
import java.util.Random;

public class DeckCharacter {
    private final ArrayList<Character> deckCharacter;
    private int numberOfCharacters = 8;


    public DeckCharacter() {
        deckCharacter = new ArrayList<>();
    }

    public void initialise(ArrayList<Character> listOfCharacters) {
        deckCharacter.clear();
        deckCharacter.add(listOfCharacters.get(Controller.ASSASIN_INDEX));
        deckCharacter.add(listOfCharacters.get(Controller.THIEF_INDEX));
        deckCharacter.add(listOfCharacters.get(Controller.MAGICIAN_INDEX));
        deckCharacter.add(listOfCharacters.get(Controller.KING_INDEX));
        deckCharacter.add(listOfCharacters.get(Controller.BISHOP_INDEX));
        deckCharacter.add(listOfCharacters.get(Controller.MERCHANT_INDEX));
        deckCharacter.add(listOfCharacters.get(Controller.ARCHITECT_INDEX));
        deckCharacter.add(listOfCharacters.get(Controller.WARLORD_INDEX));
    }

    Character chooseCharacter() {
        Random random = new Random();
        return deckCharacter.remove(random.nextInt(deckCharacter.size()));
    }

	public ArrayList<Character> getDeckCharacter() {
		return deckCharacter;
	}
    
    
}
