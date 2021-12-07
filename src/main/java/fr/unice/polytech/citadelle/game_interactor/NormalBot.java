package fr.unice.polytech.citadelle.game_interactor;

import java.util.ArrayList;

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
	public void normalBehaviour() {
		DeckDistrict deckDistrict = board.getDeckDistrict();
		int goldOfPlayer = player.getGolds();
		if (goldOfPlayer == 0 || cityMan.districtWeHaveEnoughMoneyToBuild(goldOfPlayer + 2).size() > 0)
			takeGold();
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
	}
	@Override
	public void endGameBehaviour() {
		printC.printPhase("Endgame", player);
		normalBehaviour();
	}

	@Override
	public void lastTurnBehaviour() {
		printC.printPhase("LAST TURN", player);
		normalBehaviour();
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
