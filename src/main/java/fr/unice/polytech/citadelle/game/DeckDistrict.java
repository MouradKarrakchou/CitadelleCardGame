package fr.unice.polytech.citadelle.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * A DeckDistrict is composed of all the district cards in the game.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class DeckDistrict {
    private ArrayList<District> deckDistrict;

    public DeckDistrict() {
        deckDistrict = new ArrayList<>();
        this.initializer();
    }

    public void initializer(){
        ArrayList<District> districtsWithSameValue = new ArrayList<>();
        ArrayList<String> districtsNamesWithSameValue = new ArrayList<>();


        deckDistrict.add(new District("Haunted City", 2,"purple","prestige"));

        Collections.addAll(districtsNamesWithSameValue, "Laboratory", "Smithy", "Observatory", "Graveyard");
        districtsNamesWithSameValue.stream().forEach(district -> deckDistrict.add(new District(district, 5,"purple","prestige")));
        districtsNamesWithSameValue.clear();

        Collections.addAll(districtsNamesWithSameValue, "Library", "University", "School of Magic", "Dragon Gate");
        districtsNamesWithSameValue.stream().forEach(district -> deckDistrict.add(new District(district, 6,"purple","prestige")));
        districtsNamesWithSameValue.clear();

        districtsWithSameValue.add(new District("Cathedral",5,"blue","Religion"));
        districtsWithSameValue.add(new District("Fortress",5,"red","Soldiery"));
        districtsWithSameValue.add(new District("Town Hall",5,"green","Trade and Handicrafts"));
        districtsWithSameValue.add(new District("Palace",5,"yellow","Nobility"));
        districtsWithSameValue.add(new District("Keep",3,"purple","prestige"));

        for (int i = 0; i < 2; i++){
            districtsWithSameValue.stream().forEach(district -> deckDistrict.add(district));
        }
        districtsWithSameValue.clear();

        districtsWithSameValue.add(new District("Temple",1,"blue","Religion"));
        districtsWithSameValue.add(new District("Monastery",3,"blue","Religion"));
        districtsWithSameValue.add(new District("Watchtower",1,"red","Soldiery"));
        districtsWithSameValue.add(new District("Prison",2,"red","Soldiery"));
        districtsWithSameValue.add(new District("Battlefield",3,"red","Soldiery"));
        districtsWithSameValue.add(new District("Trading Post",2,"green","Trade and Handicrafts"));
        districtsWithSameValue.add(new District("Docks",3,"green","Trade and Handicrafts"));
        districtsWithSameValue.add(new District("Harbor",4,"green","Trade and Handicrafts"));


        for (int i = 0; i < 3; i++){
            districtsWithSameValue.stream().forEach(district -> deckDistrict.add(district));
        }
        districtsWithSameValue.clear();

        districtsWithSameValue.add(new District("Church",2,"blue","Religion"));
        districtsWithSameValue.add(new District("Castle",4,"yellow","Nobility"));
        districtsWithSameValue.add(new District("Market",2,"green","Trade and Handicrafts"));

        for (int i = 0; i < 4; i++){
            districtsWithSameValue.stream().forEach(district -> deckDistrict.add(district));
        }

        for (int i = 0; i < 5; i++){
            deckDistrict.add(new District("Tavern", 1,"green","Trade and Handicrafts"));
            deckDistrict.add(new District("Manor", 3,"yellow","Nobility"));
        }
    }

    public int getSize() {
        return deckDistrict.size();
    }

    public District chooseDistrict() {
        // Select a random district from the deck
        Random random = new Random();
        int randomValue = random.nextInt(deckDistrict.size());
        return deckDistrict.remove(randomValue);
    }

    public ArrayList<District> getDeckDistrict(){ return deckDistrict; }

    public void addDistrict(District district){
        deckDistrict.add(district);
    }
}
