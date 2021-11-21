package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.characters_class.*;
import fr.unice.polytech.startingpoint.characters_class.Character;

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
        deckCharacter.clear();
        deckCharacter.add(new Architect());
        deckCharacter.add(new Assassin());
        deckCharacter.add(new Bishop());
        deckCharacter.add(new King());
        deckCharacter.add(new Magician());
        deckCharacter.add(new Merchant());
        deckCharacter.add(new Thief());
        deckCharacter.add(new Warlord());
    }

    Character chooseCharacter() {
        Random random = new Random();
        return deckCharacter.remove(random.nextInt(deckCharacter.size()));
    }
}
