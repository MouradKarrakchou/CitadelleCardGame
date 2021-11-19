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

    public void initializer(){
        ArrayList<String> districtsWithSameValue = new ArrayList<>();

        deckDistrict.add(new District("Haunted City", 2));

        Collections.addAll(districtsWithSameValue, "Laboratory", "Smithy", "Observatory", "Graveyard");
        districtsWithSameValue.stream().forEach(district -> deckDistrict.add(new District(district, 5)));
        districtsWithSameValue.clear();

        Collections.addAll(districtsWithSameValue, "Library", "University", "School of Magic", "Dragon Gate");
        districtsWithSameValue.stream().forEach(district -> deckDistrict.add(new District(district, 6)));
        districtsWithSameValue.clear(); 

        Collections.addAll(districtsWithSameValue, "Cathedral", "Palace", "Town Hall", "Fortress");
        for (int i = 0; i < 2; i++){
            deckDistrict.add(new District("Keep", 3));
            districtsWithSameValue.stream().forEach(district -> deckDistrict.add(new District(district, 5)));
        }
        districtsWithSameValue.clear();

        ArrayList<String> districtsWithSameValue2 = new ArrayList<>();
        ArrayList<String> districtsWithSameValue3 = new ArrayList<>();
        Collections.addAll(districtsWithSameValue, "Temple", "Watchtower");
        Collections.addAll(districtsWithSameValue2, "Trading Post", "Prison");
        Collections.addAll(districtsWithSameValue3, "Monastery", "Docks", "Battlefield");
        for (int i = 0; i < 3; i++){
            districtsWithSameValue.stream().forEach(district -> deckDistrict.add(new District(district, 1)));
            districtsWithSameValue2.stream().forEach(district -> deckDistrict.add(new District(district, 2)));
            districtsWithSameValue3.stream().forEach(district -> deckDistrict.add(new District(district, 3)));
            deckDistrict.add(new District("Harbor", 4));
        }
        districtsWithSameValue.clear();

        Collections.addAll(districtsWithSameValue,"Church", "Market");
        for (int i = 0; i < 4; i++){
            districtsWithSameValue.stream().forEach(district -> deckDistrict.add(new District(district, 2)));
            deckDistrict.add(new District("Castle", 4));
        }

        for (int i = 0; i < 5; i++){
            deckDistrict.add(new District("Tavern", 1));
            deckDistrict.add(new District("Manor", 3));
        }
    }

    public int getSize() {
        return deckDistrict.size();
    }

    District chooseDistrict() {
        // Select a random district from the deck
        Random random = new Random();
        int randomValue = random.nextInt(deckDistrict.size());
        return deckDistrict.remove(randomValue);
    }

    ArrayList<District> getDeckDistrict(){ return deckDistrict; }

    public void addDistrict(District district){
        deckDistrict.add(district);
    }
}
