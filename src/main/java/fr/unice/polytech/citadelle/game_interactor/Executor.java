package fr.unice.polytech.citadelle.game_interactor;

import java.util.ArrayList;
import java.util.Random;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class Executor {
	Player player;
	PrintCitadels printC = new PrintCitadels();

	
	public Executor(Player player){
		this.player = player;
	}
	
	public void takeCard(District district, DeckDistrict deckDistrict) {
    	System.out.println(player.getName()+" add 1 cards "+district.getName());
		player.addDistrict(district);
		//deckDistrict.removeDistrict(district);
	}
	
	public void buildDistrict(District district){
		player.buildDistrict(district);
		printC.printBuildDistrict(player, district);
		printC.printBoardOfPlayer(player);
	}
	
	public void takeGold() {
		printC.printTakeGold(player);
		player.addGold();
	}
	
	public void putCardBackInDeck(DeckDistrict deckDistrict, District districtCard) {
    	System.out.println(player.getName()+" putBack 1 cards "+districtCard.getName());
		deckDistrict.addDistrict(districtCard);
	}
	public District pickCard(DeckDistrict deckDistrict) {
		return pickBlindDistrict(deckDistrict);
	}

	public ArrayList<District> pickCards(DeckDistrict deckDistrict) {
		ArrayList<District> pickedCards = new ArrayList<District>();
				pickedCards.add(pickBlindDistrict(deckDistrict));
				pickedCards.add(pickBlindDistrict(deckDistrict));
				return pickedCards;
	}
	
    public District pickBlindDistrict(DeckDistrict deckDistrict) {
        // Select a random district from the deck
        Random random = new Random();
        int randomValue = random.nextInt(deckDistrict.getDeckDistrict().size());
    	System.out.println(player.getName()+" take one cards "+ deckDistrict.getDeckDistrict().get(randomValue).getName());
        return deckDistrict.getDeckDistrict().remove(randomValue);
    }

	public void addDistrict(District district) {
		player.getDistrictCards().add(district);
	}
}
