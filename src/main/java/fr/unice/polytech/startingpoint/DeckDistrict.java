package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

public class DeckDistrict {
    ArrayList<District> deckDistrict;
    int size;
    public DeckDistrict(){
        size=69;
        deckDistrict=new ArrayList<>();
        this.initialise();
    }
    private void initialise(){
        for (int k=0;k<size/2;k++){
            deckDistrict.add(new District("house",1));
        }
        for (int k=0;k<size/2;k++){
            deckDistrict.add(new District("house",2));
        }
    }
    public ArrayList<District> getDeckCharacter() {
        return deckDistrict;
    }
    public int getSize() {
        return size;
    }
    
    District chooseDistrict(){
        // Select a random district from the deck
        Random random=new Random();
        int randomValue = random.nextInt(deckDistrict.size());
        District choosenDistrict = deckDistrict.remove(randomValue);

        return choosenDistrict;

    }
}
