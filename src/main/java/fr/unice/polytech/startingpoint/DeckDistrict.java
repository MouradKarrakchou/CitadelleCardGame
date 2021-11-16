package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

public class DeckDistrict {
    ArrayList<District> deckDistrict;
    int size;
    public DeckDistrict(){
        size=12;
        deckDistrict=new ArrayList<>();
        this.initialise();
    }
    private void initialise(){
        for (int k=0;k<6;k++){
            deckDistrict.add(new District("house",1));
        }
        for (int k=0;k<6;k++){
            deckDistrict.add(new District("house",2));
        }
    }
    public ArrayList<District> getDeckCharacter() {
        return deckDistrict;
    }
    public int getSize() {
        return size;
    }
    void chooseDistrict(Player player){
        // Select a random district from the deck
        Random random=new Random();
        District removedDistrict = deckDistrict.remove(random.nextInt(deckDistrict.size()));

        // Check the cost of the selected district
        int districtCost = removedDistrict.getValue();

        // Decreasing player golds & giving the district.
        player.decreaseGoldsBy(districtCost);
        player.addDistrict(removedDistrict);

    }
}
