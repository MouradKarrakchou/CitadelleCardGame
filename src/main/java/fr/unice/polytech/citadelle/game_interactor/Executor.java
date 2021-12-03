package fr.unice.polytech.citadelle.game_interactor;

import java.util.ArrayList;

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
		player.addDistrict(district);
		deckDistrict.removeDistrict(district);
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
		deckDistrict.addDistrict(districtCard);
	}
	
	public ArrayList<District> pickCards(DeckDistrict deckDistrict) {
		ArrayList<District> pickedCards = new ArrayList<District>();
				pickedCards.add(deckDistrict.pickBlindDistrict());
				pickedCards.add(deckDistrict.pickBlindDistrict());
				return pickedCards;
	}
	
}
