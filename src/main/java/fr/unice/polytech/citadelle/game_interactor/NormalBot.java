package fr.unice.polytech.citadelle.game_interactor;

import java.util.ArrayList;

import fr.unice.polytech.citadelle.basic_actions.BasicActions;
import fr.unice.polytech.citadelle.basic_actions.TakeGoldAction;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.SpellDistrict;

public class NormalBot extends Behaviour {

	public NormalBot(Player player, Board board) {
		super(player, board);
	}

	public void executeSpell(ArrayList<SpellDistrict> spellDistrict, DeckDistrict deckDistrict) {
		spellDistrict.get(0).librarySpell(player, deckDistrict);
	}


	@Override
	public ArrayList<BasicActions> normalBehaviour() {
		ArrayList<BasicActions> basicActions = new ArrayList<>();
		DeckDistrict deckDistrict = board.getDeckDistrict();
		int goldOfPlayer = player.getGolds();
		TakeGoldAction takeGoldAction;
		if (goldOfPlayer == 0 || cityMan.districtWeHaveEnoughMoneyToBuild(goldOfPlayer + 2).size() > 0) {
			takeGoldAction = takeGold();
			basicActions.add(takeGoldAction);
		}
		else {
			ArrayList<SpellDistrict> spellDistrict = new ArrayList<>();
			for (District district : player.getCity().getBuiltDistrict()) {
				if (district.getName().equals("Library")) spellDistrict.add((SpellDistrict) district);
			}
			if (spellDistrict.size() != 0) executeSpell(spellDistrict, deckDistrict);
			else {
				District choosenDistrictCard = pickCardsInDeck();
				takeCard(choosenDistrictCard);
			}
		}
		ifPossibleBuildADistrict();
		return basicActions;
	}
	@Override
	public ArrayList<BasicActions> endGameBehaviour() {
		printC.printPhase("Endgame", player);
		return normalBehaviour();
	}

	@Override
	public ArrayList<BasicActions> lastTurnBehaviour() {
		printC.printPhase("LAST TURN", player);
		return normalBehaviour();
	}

	@Override
	public District chooseBetweenTwoCards(District firstDistrict, District secondDistrict) {
		DeckDistrict deckDistrict = board.getDeckDistrict();
		ArrayList<District> pickedCards = new ArrayList<>();
		pickedCards.add(firstDistrict);
		pickedCards.add(secondDistrict);
		District chosenCard = selectTheHigherDistrict(pickedCards);
		if(chosenCard.equals(firstDistrict))
			executor.putCardBackInDeck(deckDistrict, secondDistrict);
		else
			executor.putCardBackInDeck(deckDistrict, firstDistrict);
		return chosenCard;

	}



}
