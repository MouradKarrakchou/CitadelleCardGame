package fr.unice.polytech.citadelle.game_interactor;

import java.util.ArrayList;
import java.util.Optional;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;

public class NormalBot extends Behaviour {

	public NormalBot(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void normalBehaviour(DeckDistrict deckDistrict) {

		int goldOfPlayer = player.getGolds();
		if (goldOfPlayer == 0 || cityMan.districtWeHaveEnoughMoneyToBuild(goldOfPlayer + 2).size() > 0)
			takeGold();
		else {
			//library
			District choosenDistrictCard = pickCardsInDeck(deckDistrict);
			takeCard(choosenDistrictCard, deckDistrict);
		}
		ifPossibleBuildADistrict();
	}

	@Override
	public void endGameBehaviour(DeckDistrict deckDistrict) {
		printC.printPhase("Endgame", player);
		normalBehaviour(deckDistrict);
	}

	@Override
	public void lastTurnBehaviour(DeckDistrict deckDistrict) {
		printC.printPhase("LAST TURN", player);
		normalBehaviour(deckDistrict);
	}

	@Override
	public District chooseBetweenTwoCards(District firstDistrict, District secondDistrict, DeckDistrict deckDistrict) {
		ArrayList<District> pickedCards = new ArrayList<>();
		pickedCards.add(firstDistrict);
		pickedCards.add(secondDistrict);
		return selectTheHigherDistrict(deckDistrict, pickedCards);
	}



}
