package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class Deck {
    ArrayList<Character> deckCharacter;
    int size;
    public Deck(){
        size=8;
        deckCharacter=new ArrayList<>();
    }
    void initialise(){
        for (int k=1;k<9;k++){
            deckCharacter.add(new Character("villager",k));
        }
    }
    public ArrayList<Character> getDeckCharacter() {
        return deckCharacter;
    }
    public int getSize() {
        return size;
    }
}
