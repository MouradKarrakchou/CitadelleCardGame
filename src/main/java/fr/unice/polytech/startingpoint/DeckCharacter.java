package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

public class DeckCharacter {
    private final ArrayList<Character> deckCharacter;
    private int numberOfCharacters = 8;


    public DeckCharacter() {
        deckCharacter = new ArrayList<>();
        this.initialise();
    }

    public void initialise() {
        for (int k = 1; k <= numberOfCharacters; k++) {
            deckCharacter.add(new Character("villager", k));
        }
    }

    Character chooseCharacter() {
        Random random = new Random();
        return deckCharacter.remove(random.nextInt(deckCharacter.size()));
    }
}
