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
        oneCard();
        doubleCards();
        tripleCards();
        quadraCards();
        pentaCards();
    }

    private void oneCard() {
        ArrayList<String> districtsNamesWithSameNumberOfCards = new ArrayList<>();

        deckDistrict.add(new District("Haunted City", 2,"Purple","Prestige"));

        Collections.addAll(districtsNamesWithSameNumberOfCards, "Laboratory", "Smithy", "Observatory", "Graveyard");
        districtsNamesWithSameNumberOfCards.stream().forEach(district -> deckDistrict.add(new District(district, 5,"purple","prestige")));
        districtsNamesWithSameNumberOfCards.clear();

        Collections.addAll(districtsNamesWithSameNumberOfCards, "Library", "University", "School of Magic", "Dragon Gate");
        districtsNamesWithSameNumberOfCards.stream().forEach(district -> deckDistrict.add(new District(district, 6,"Purple","Prestige")));
        districtsNamesWithSameNumberOfCards.clear();

    }

    private void doubleCards() {
        ArrayList<District> districtsWithSameValue = new ArrayList<>();

        districtsWithSameValue.add(new District("Cathedral",5,"Blue","Religion"));
        districtsWithSameValue.add(new District("Fortress",5,"Red","Soldiery"));
        districtsWithSameValue.add(new District("Town Hall",5,"Green","Trade and Handicrafts"));
        districtsWithSameValue.add(new District("Palace",5,"Yellow","Nobility"));
        districtsWithSameValue.add(new District("Keep",3,"Purple","Prestige"));

        for (int i = 0; i < 2; i++){
            districtsWithSameValue.stream().forEach(district -> deckDistrict.add(district));
        }
        districtsWithSameValue.clear();
    }

    private void tripleCards() {
        ArrayList<District> districtsNamesWithSameNumberOfCards = new ArrayList<>();

        districtsNamesWithSameNumberOfCards.add(new District("Temple",1,"Blue","Religion"));
        districtsNamesWithSameNumberOfCards.add(new District("Monastery",3,"Blue","Religion"));
        districtsNamesWithSameNumberOfCards.add(new District("Watchtower",1,"Red","Soldiery"));
        districtsNamesWithSameNumberOfCards.add(new District("Prison",2,"Red","Soldiery"));
        districtsNamesWithSameNumberOfCards.add(new District("Battlefield",3,"Red","Soldiery"));
        districtsNamesWithSameNumberOfCards.add(new District("Trading Post",2,"Green","Trade and Handicrafts"));
        districtsNamesWithSameNumberOfCards.add(new District("Docks",3,"Green","Trade and Handicrafts"));
        districtsNamesWithSameNumberOfCards.add(new District("Harbor",4,"Green","Trade and Handicrafts"));

        for (int i = 0; i < 3; i++){
            districtsNamesWithSameNumberOfCards.stream().forEach(district -> deckDistrict.add(district));
        }
        districtsNamesWithSameNumberOfCards.clear();
    }

    private void quadraCards () {
        ArrayList<District> districtsNamesWithSameNumberOfCards = new ArrayList<>();

        districtsNamesWithSameNumberOfCards.add(new District("Church",2,"Blue","Religion"));
        districtsNamesWithSameNumberOfCards.add(new District("Castle",4,"Yellow","Nobility"));
        districtsNamesWithSameNumberOfCards.add(new District("Market",2,"Green","Trade and Handicrafts"));

        for (int i = 0; i < 4; i++){
            districtsNamesWithSameNumberOfCards.stream().forEach(district -> deckDistrict.add(district));
        }
    }

    private void pentaCards() {
        for (int i = 0; i < 5; i++){
            deckDistrict.add(new District("Tavern", 1,"Green","Trade and Handicrafts"));
            deckDistrict.add(new District("Manor", 3,"Yellow","Nobility"));
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
