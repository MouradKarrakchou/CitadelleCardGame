package fr.unice.polytech.citadelle.game_interactor;

import java.util.ArrayList;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 * Execute the actions asked by the behaviour
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Executor {
	Player player;

	
	public Executor(Player player){
		this.player = player;
	}

	/**
	 * Allow the bot to pick a card.
	 * @param district The district to add to the player hand.
	 */
    public void takeCard(District district) {
		PrintCitadels.printAddCardToTheDeck(player,district);
		player.addDistrict(district);
		//deckDistrict.removeDistrict(district);
	}

	/**
	 * Allow the bot to build a city on the city of the player he controls.
	 * @param district The district to build.
	 */
	public void buildDistrict(District district){
		player.buildDistrict(district);
		PrintCitadels.printBuildDistrict(player, district);
		PrintCitadels.printBoardOfPlayer(player);
	}

	/**
	 * Allow the bot take gold for the player he controls.
	 */
	public void takeGold() {
		player.addGold();
		PrintCitadels.printTakeGold(player);
	}

	/**
	 * Allow the bot to put back a card to the deck of district for the player he controls.
	 * @param deckDistrict The current deck of district of the game.
	 * @param districtCard The district cart to put back in the deck of district.
	 */
	public void putCardBackInDeck(DeckDistrict deckDistrict, District districtCard) {
		PrintCitadels.printPutCardBackToTheDeck(player,districtCard);
		deckDistrict.addDistrict(districtCard);
	}

	/**
	 * Choose a card on a given deck of districts.
	 * @param deckDistrict The current deck of district of the game.
	 * @return The card picked from the deck of district.
	 */
	public District pickCard(DeckDistrict deckDistrict) {
		return pickBlindDistrict(deckDistrict);
	}

	/**
	 * Choose two cards on a given deck of districts.
	 * @param deckDistrict The current deck of district of the game.
	 * @return The array of picked district cards.
	 */
	public ArrayList<District> pickCards(DeckDistrict deckDistrict) {
		ArrayList<District> pickedCards = new ArrayList<>();
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

	/**
	 * Allow the bot to add a district card to the district of the player the bot controls.
	 * @param district The district card to add to the player's hand.
	 */
	public void addDistrict(District district) {
		player.getDistrictCards().add(district);
	}
}
