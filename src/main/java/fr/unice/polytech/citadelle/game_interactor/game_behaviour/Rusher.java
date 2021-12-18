package fr.unice.polytech.citadelle.game_interactor.game_behaviour;

import java.util.ArrayList;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.SpellDistrict;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 * Rusher act by focusing cheap districts to finish first
 * and get the +4 bonus of the first one who finish
 */
public class Rusher extends Behaviour {

	private static final int MAX_VALUES_OF_CARDS = 3;

	public Rusher(Player player, Board board) {
		super(player, board);
	}

	public void executeSpell(ArrayList<SpellDistrict> spellDistrict, DeckDistrict deckDistrict) {
		spellDistrict.get(0).librarySpell(player, deckDistrict);
	}

	@Override
	public void normalBehaviour() {
		DeckDistrict deckDistrict = board.getDeckDistrict();
		ArrayList<District> buidableDistrict = cityMan.districtWeCanBuild(player.getDistrictCards());
		ArrayList<District> cheapersDistrictsBuildable = getAllCheapersDistricts(buidableDistrict);

		if (cheapersDistrictsBuildable.size() == 0) {
			ArrayList<SpellDistrict> spellDistrict = new ArrayList<>();
			for (District district : player.getCity().getBuiltDistrict()) {
				if (district.getName().equals("Library"))
					spellDistrict.add((SpellDistrict) district);
			}
			if (spellDistrict.size() != 0)
				executeSpell(spellDistrict, deckDistrict);

			District choosenDistrictCard = pickCardsInDeck();
			takeCard(choosenDistrictCard);

		} else {
			takeGold();
		}
		ifPossibleBuildACheapDistrict();
	}

	@Override
	public void endGameBehaviour() {
		PrintCitadels.printPhase("Endgame", player);
		DeckDistrict deckDistrict = board.getDeckDistrict();
		ArrayList<District> futurBuildableDistrict = cityMan.getBuildableDistrictWithTwoMoreGold();
		if (futurBuildableDistrict.size() > 0) {// s'il peut poser un bat en prenant les deux gold
			takeGold();
		} else {
			ArrayList<SpellDistrict> spellDistrict = new ArrayList<>();
			for (District district : player.getCity().getBuiltDistrict()) {
				if (district.getName().equals("Library"))
					spellDistrict.add((SpellDistrict) district);
			}
			if (spellDistrict.size() != 0)
				executeSpell(spellDistrict, deckDistrict);
			else {
				District choosenDistrictCard = pickCardsInDeck();
				takeCard(choosenDistrictCard);
			}
		}

		ifPossibleBuildADistrict();
	}

	@Override
	public void lastTurnBehaviour() {
		PrintCitadels.printPhase("LAST TURN", player);
		normalBehaviour();
	}

	public void ifPossibleBuildACheapDistrict() {
		ArrayList<District> districtWeCanBuild = cityMan.listOfDistrictBuildable();
		if (!districtWeCanBuild.isEmpty()) {
			District cheaperDistrict = getCheaperDistrict(districtWeCanBuild);
			if (cheaperDistrict.getValue() <= MAX_VALUES_OF_CARDS) {
				buildDistrict(cheaperDistrict);
			}
		}
	}

	public District getCheaperDistrict(ArrayList<District> districtWeCanBuild) {
		District cheaperDistrict = districtWeCanBuild.get(0);
		for (District current : districtWeCanBuild) {
			if (cheaperDistrict.getValue() > current.getValue())
				cheaperDistrict = current;
		}
		return cheaperDistrict;
	}

	private ArrayList<District> getAllCheapersDistricts(ArrayList<District> listOfDistrictsOfPlayer) {
		return listOfDistrictsOfPlayer.stream().filter(district -> district.getValue() <= MAX_VALUES_OF_CARDS)
				.collect(Collectors.toCollection(ArrayList::new));

	}

	@Override
	public District chooseBetweenTwoCards(District firstDistrict, District secondDistrict) {
		DeckDistrict deckDistrict = board.getDeckDistrict();
		ArrayList<District> pickedCards = new ArrayList<>();
		pickedCards.add(firstDistrict);
		pickedCards.add(secondDistrict);
		District chosenCard = selectTheLowerDistrict(pickedCards);
		if (chosenCard.equals(firstDistrict))
			executor.putCardBackInDeck(deckDistrict, secondDistrict);
		else
			executor.putCardBackInDeck(deckDistrict, firstDistrict);
		return chosenCard;
	}

}
