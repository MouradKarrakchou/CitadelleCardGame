package fr.unice.polytech.citadelle.game_interactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.basic_actions.BasicActions;
import fr.unice.polytech.citadelle.basic_actions.TakeGoldAction;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.SpellDistrict;

public class RushBot extends Behaviour {

	private static final int MAX_VALUES_OF_CARDS = 3;

	public RushBot(Player player, Board board) {
		super(player, board);
	}

	public void executeSpell(ArrayList<SpellDistrict> spellDistrict, DeckDistrict deckDistrict) {
		spellDistrict.get(0).librarySpell(player, deckDistrict);
	}

	@Override
	public ArrayList<BasicActions> normalBehaviour() {
		DeckDistrict deckDistrict = board.getDeckDistrict();
		ArrayList<BasicActions> basicActions = new ArrayList<>();
		ArrayList<District> buidableDistrict = cityMan.districtWeCanBuild(player.getDistrictCards());
		ArrayList<District> cheapersDistrictsBuildable = getAllCheapersDistricts(buidableDistrict);

		if (cheapersDistrictsBuildable.size() == 0) {
			ArrayList<SpellDistrict> spellDistrict = new ArrayList<>();
			for (District district : player.getCity().getBuiltDistrict()) {
				if (district.getName().equals("Library"))
					spellDistrict.add((SpellDistrict) district);
			}
			if (spellDistrict.size() != 0) executeSpell(spellDistrict, deckDistrict);
			else {
				District choosenDistrictCard = pickCardsInDeck();
				takeCard(choosenDistrictCard);
			}
		} else {
			TakeGoldAction takeGoldAction = takeGold();
			basicActions.add(takeGoldAction);
		}
		ifPossibleBuildACheapDistrict();
		return basicActions;
	}

	@Override
	public ArrayList<BasicActions> endGameBehaviour() {
		printC.printPhase("Endgame", player);
		ArrayList<BasicActions> basicActions = new ArrayList<>();
		DeckDistrict deckDistrict = board.getDeckDistrict();
		ArrayList<District> futurBuildableDistrict = cityMan.getBuildableDistrictWithTwoMoreGold();
		if (futurBuildableDistrict.size() > 0) {// s'il peut poser un bat en prenant les deux gold
			TakeGoldAction takeGoldAction = takeGold();
			basicActions.add(takeGoldAction);
		}
		else {
			ArrayList<SpellDistrict> spellDistrict = new ArrayList<>();
			for (District district : player.getCity().getBuiltDistrict()) {
				if (district.getName().equals("Library"))
					spellDistrict.add((SpellDistrict) district);
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
	public ArrayList<BasicActions> lastTurnBehaviour() {
		printC.printPhase("LAST TURN", player);
		return normalBehaviour();
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
		if(chosenCard.equals(firstDistrict))
			executor.putCardBackInDeck(deckDistrict, secondDistrict);
		else
			executor.putCardBackInDeck(deckDistrict, firstDistrict);
		return chosenCard;
	}

}
