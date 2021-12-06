package fr.unice.polytech.citadelle.game_interactor;

import java.util.ArrayList;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.SpellDistrict;

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
			ArrayList<SpellDistrict> spellDistrict = new ArrayList<>();
			for (District district : player.getCity().getBuiltDistrict()) {
				if (district.getName().equals("Library")) spellDistrict.add((SpellDistrict) district);
			}
			if (spellDistrict.size() != 0) spellDistrict.get(0).librarySpell(player, deckDistrict);
			else {
				District choosenDistrictCard = pickCardsInDeck(deckDistrict);
				takeCard(choosenDistrictCard, deckDistrict);
			}
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
		District chosenCard = selectTheHigherDistrict(deckDistrict, pickedCards);
		if(chosenCard.equals(firstDistrict))
			executor.putCardBackInDeck(deckDistrict, secondDistrict);
		else
			executor.putCardBackInDeck(deckDistrict, firstDistrict);
		return chosenCard;

	}



}
