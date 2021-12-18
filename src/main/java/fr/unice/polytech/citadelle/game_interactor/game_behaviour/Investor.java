package fr.unice.polytech.citadelle.game_interactor.game_behaviour;

import java.util.ArrayList;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.SpellDistrict;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 * Investor act by focusing expensive districts
 * to score a maximum of points
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Investor extends Behaviour {

	public Investor(Player player, Board board) {
		super(player, board);
	}

	/**
	 * Execute the spell of the Library which is keeping both card
	 * picked instead of choosing one and putting back the other
	 * @param spellDistrict
	 * @param deckDistrict
	 */
	public void executeSpell(ArrayList<SpellDistrict> spellDistrict, DeckDistrict deckDistrict) {
		spellDistrict.get(0).librarySpell(player, deckDistrict);
	}

	/**
	 * Build districts when possible and foresee if it will be possible to build with 2 more golds
	 */
	@Override
	public void normalBehaviour() {
		DeckDistrict deckDistrict = board.getDeckDistrict();
		int goldOfPlayer = player.getGolds();
		if (goldOfPlayer == 0 || cityMan.districtWeHaveEnoughMoneyToBuild(goldOfPlayer + 2).size() > 0 || board.getDeckDistrict().getSize()==0) {
			takeGold();
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
	}

	@Override
	public void endGameBehaviour() {
		PrintCitadels.printPhase("Endgame", player);
		normalBehaviour();
	}

	@Override
	public void lastTurnBehaviour() {
		PrintCitadels.printPhase("LAST TURN", player);
		normalBehaviour();
	}

	/**
	 * Choose between 2 cards
	 * @param firstDistrict
	 * @param secondDistrict
	 * @return the chosen card
	 */
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
