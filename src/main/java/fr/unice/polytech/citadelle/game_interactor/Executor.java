package fr.unice.polytech.citadelle.game_interactor;

import java.util.ArrayList;
import java.util.Random;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class Executor {
	Player player;

	
	public Executor(Player player){
		this.player = player;
	}


    public void takeCard(District district, DeckDistrict deckDistrict) {
		PrintCitadels.printAddCardToTheDeck(player,district);
		player.addDistrict(district);
		//deckDistrict.removeDistrict(district);
	}
	
	public void buildDistrict(District district){
		player.buildDistrict(district);
		PrintCitadels.printBuildDistrict(player, district);
		PrintCitadels.printBoardOfPlayer(player);
	}
	
	public void takeGold() {
		player.addGold();
		PrintCitadels.printTakeGold(player);
	}
	
	public void putCardBackInDeck(DeckDistrict deckDistrict, District districtCard) {
		PrintCitadels.printPutCardBackToTheDeck(player,districtCard);
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

	/**
	 * Return the random card picked and display it in the console.
	 * @param deckDistrict - The district card deck of the game.
	 * @return The picked District.
	 */
    public District pickBlindDistrict(DeckDistrict deckDistrict) {
		District selectedDistrict = deckDistrict.blindPick();
		PrintCitadels.printTakeDistrictCard(player, selectedDistrict);
        return selectedDistrict;
    }

	public void addDistrict(District district) {
		player.getDistrictCards().add(district);
	}
}
