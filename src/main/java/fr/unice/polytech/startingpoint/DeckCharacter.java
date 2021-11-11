package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class DeckCharacter {
    ArrayList<Character> deckCharacter;
    int size;
    public DeckCharacter(){
        size=8;
        deckCharacter=new ArrayList<>();
        this.initialise();
    }
    private void initialise(){
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
