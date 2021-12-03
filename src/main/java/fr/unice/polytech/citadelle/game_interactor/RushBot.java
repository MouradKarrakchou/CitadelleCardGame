package fr.unice.polytech.citadelle.game_interactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;

public class RushBot extends Behaviour {

	private static final int MAX_VALUES_OF_CARDS = 3;

	public RushBot(Player player) {
		super(player);
	}


	public void normalBehaviour(DeckDistrict deckDistrict) {
		ArrayList<District> buidableDistrict = cityMan.districtWeCanBuild(player.getDistrictCards());
		ArrayList<District> cheapersDistrictsBuildable = getAllCheapersDistricts(buidableDistrict);

		if (player.getDistrictCardsSize() == 0 || cheapersDistrictsBuildable.size() == 0) {
			Optional<District> districtCardChoosen = pick2CardsIntoTheDeck(deckDistrict);
			if(districtCardChoosen.isPresent())
				executor.takeCard(districtCardChoosen.get(), deckDistrict);
			else
				executor.takeGold();
		}
		else {
			executor.takeGold();
		}
		ifPossibleBuildACheapDistrict();
	}

	public void endGameBehaviour(DeckDistrict deckDistrict) {
		printC.printPhase("Endgame", player);
		
		ArrayList<District> futurBuildableDistrict = cityMan.getBuildableDistrictWithTwoMoreGold();
		if(futurBuildableDistrict.size() > 0) // s'il peut poser un bat en prenant les deux gold
			executor.takeGold();
		else {
			Optional<District> districtCardChoosen = pick2CardsIntoTheDeck(deckDistrict);
			if(districtCardChoosen.isPresent())
				executor.takeCard(districtCardChoosen.get(), deckDistrict);
			else
				executor.takeGold();
		}
	
		ifPossibleBuildADistrict();
	}

	public void lastTurnBehaviour(DeckDistrict deckDistrict) {
		endGameBehaviour(deckDistrict);
	}

	protected void ifPossibleBuildACheapDistrict() {
		ArrayList<District> districtWeCanBuild = cityMan.listOfDistrictBuildable();
		if (!districtWeCanBuild.isEmpty()) {
			District cheaperDistrict = getCheaperDistrict(districtWeCanBuild);
			if (cheaperDistrict.getValue() <= MAX_VALUES_OF_CARDS) {
				executor.buildDistrict(cheaperDistrict);
			}
		}
	}

	private District getCheaperDistrict(ArrayList<District> districtWeCanBuild) {
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

	

}
