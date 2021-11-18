package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DeckDistrict {
    private ArrayList<District> deckDistrict;
    private final int numberOfDistricts = 65;

    public DeckDistrict() {
        deckDistrict = new ArrayList<>();
        this.initializer();
    }

    /*
    private void initialise() {
        for (int k = 0; k < numberOfDistricts / 8; k++) {
            deckDistrict.add(new District("house", 1));
        }
        for (int k = 0; k < numberOfDistricts / 2; k++) {
            deckDistrict.add(new District("house", 2));
        }
    }
    */

    private void initializer(){
        deckDistrict.add(new District("Haunted City", 2));

        ArrayList<String> districtsWithSameValue = new ArrayList<>();
        Collections.addAll(districtsWithSameValue, "Laboratory", "Smithy", "Observatory", "Graveyard");
        districtsWithSameValue.stream().forEach(district -> deckDistrict.add(new District(district, 5)));

        districtsWithSameValue = new ArrayList<>();
        Collections.addAll(districtsWithSameValue, "Library", "University", "School of Magic", "Dragon Gate");
        districtsWithSameValue.stream().forEach(district -> deckDistrict.add(new District(district, 6)));


        districtsWithSameValue = new ArrayList<>();
        Collections.addAll(districtsWithSameValue, "Cathedral", "Palace", "Town Hall", "Fortress");
        for (int i = 0; i < 2; i++){
            deckDistrict.add(new District("Keep", 3));
            districtsWithSameValue.stream().forEach(district -> deckDistrict.add(new District(district, 5)));
        }

        ArrayList<String> districtsWithSameValue2 = new ArrayList<>();
        ArrayList<String> districtsWithSameValue3 = new ArrayList<>();
        districtsWithSameValue = new ArrayList<>();
        Collections.addAll(districtsWithSameValue, "Temple", "Watchtower");
        Collections.addAll(districtsWithSameValue2, "Trading Post", "Prison");
        Collections.addAll(districtsWithSameValue3, "Monastery", "Docks", "Battlefield");
        for (int i = 0; i < 3; i++){
            districtsWithSameValue.stream().forEach(district -> deckDistrict.add(new District(district, 1)));
            districtsWithSameValue2.stream().forEach(district -> deckDistrict.add(new District(district, 2)));
            districtsWithSameValue3.stream().forEach(district -> deckDistrict.add(new District(district, 3)));
            deckDistrict.add(new District("Harbor", 4));
        }

        districtsWithSameValue = new ArrayList<>();
        Collections.addAll(districtsWithSameValue,"Church", "Market");
        for (int i = 0; i < 4; i++){
            districtsWithSameValue.stream().forEach(district -> deckDistrict.add(new District(district, 2)));
            deckDistrict.add(new District("Castle", 4));
        }

        districtsWithSameValue = new ArrayList<>();
        Collections.addAll(districtsWithSameValue,"Tavern", "Manor");
        for (int i = 0; i < 5; i++){
            districtsWithSameValue.stream().forEach(district -> deckDistrict.add(new District(district, 2)));
        }
    }


    public ArrayList<District> getDeckCharacter() {
        return deckDistrict;
    }

    public int getSize() {
        return numberOfDistricts;
    }

    District chooseDistrict() {
        // Select a random district from the deck
        Random random = new Random();
        int randomValue = random.nextInt(deckDistrict.size());
        District choosenDistrict = deckDistrict.remove(randomValue);

        return choosenDistrict;

    }
}
