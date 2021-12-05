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

	public District chooseBetweenTwoCards(District firstDistrict, District secondDistrict, DeckDistrict deckDistrict) {
		ArrayList<District> pickedCards = new ArrayList<>();
		pickedCards.add(firstDistrict);
		pickedCards.add(secondDistrict);
		return selectTheHigherDistrict(deckDistrict, pickedCards);
	}

	public District pickCardsInDeck(DeckDistrict deckDistrict) {
		ArrayList<District> pickedCards = new ArrayList<>();
		ArrayList<District> possibleCards = new ArrayList<>();
		District choosenDistrictCard = null; // bof

		pickedCards = pick2CardsIntoTheDeck(deckDistrict);
		possibleCards = chooseToKeepOrNotPickedCards(pickedCards, deckDistrict);

		switch (possibleCards.size()) {
		case ONE_CARD:
			choosenDistrictCard = possibleCards.get(0);
			break;
		case TWO_CARD:
			choosenDistrictCard = chooseBetweenTwoCards(possibleCards.get(0), possibleCards.get(1), deckDistrict);
			break;
		case ZERO_CARD:
			choosenDistrictCard = chooseBetweenTwoCards(pickedCards.get(0), pickedCards.get(1), deckDistrict);
			break;
		}
		return choosenDistrictCard;
	}

}
