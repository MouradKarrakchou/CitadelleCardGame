package fr.unice.polytech.citadelle.game;

import fr.unice.polytech.citadelle.game.purple_districts.*;

import java.util.ArrayList;
import java.util.Random;


/**
 * A DeckDistrict is composed of all the district cards in the game.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class DeckDistrict {
    private ArrayList<District> deckDistrict;

    public DeckDistrict() {
        deckDistrict = new ArrayList<>();
    }

    public void initialise(){
        oneCard();
        doubleCards();
        tripleCards();
        quadraCards();
        pentaCards();
    }

    private void oneCard() {
        ArrayList<String> districtsNamesWithSameNumberOfCards = new ArrayList<>();

        deckDistrict.add(new HauntedCity("Haunted City", 2,"Purple","Prestige", 0));

        deckDistrict.add(new Laboratory("Laboratory", 5,"Purple","Prestige"));
        deckDistrict.add(new Smithy("Smithy", 5,"Purple","Prestige"));
        deckDistrict.add(new Observatory("Observatory", 5,"Purple","Prestige"));
        deckDistrict.add(new Graveyard("Graveyard", 5,"Purple","Prestige"));

        deckDistrict.add(new Library("Library", 6,"Purple","Prestige"));
        deckDistrict.add(new University("University", 6,"Purple","Prestige"));
        deckDistrict.add(new SchoolOfMagic("School of Magic", 6,"Purple","Prestige"));
        deckDistrict.add(new DragonGate("Dragon Gate", 6,"Purple","Prestige"));

    }

    private void doubleCards() {
        ArrayList<District> districtsWithSameValue = new ArrayList<>();

        districtsWithSameValue.add(new District("Cathedral",5,"Blue","Religion"));
        districtsWithSameValue.add(new District("Fortress",5,"Red","Soldiery"));
        districtsWithSameValue.add(new District("Town Hall",5,"Green","Trade and Handicrafts"));
        districtsWithSameValue.add(new District("Palace",5,"Yellow","Nobility"));
        districtsWithSameValue.add(new Keep("Keep",3,"Purple","Prestige"));

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


    public ArrayList<District> getDeckDistrict(){ return deckDistrict; }

    public void addDistrict(District district){
        deckDistrict.add(district);
    }

	public void removeDistrict(District district) {
        deckDistrict.remove(district);
	}


    /**
     * Select a random district from the deck
     * @return The selected District removed from the deck.
     */
    public District blindPick() {
        Random random = new Random();
        int randomValue = random.nextInt(deckDistrict.size());
        return deckDistrict.remove(randomValue);
    }
}
