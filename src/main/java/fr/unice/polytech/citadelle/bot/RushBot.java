package fr.unice.polytech.citadelle.bot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;

public class RushBot extends Bot {

	private static final int MAX_VALUES_OF_CARDS = 3;

	public RushBot(Player player) {
		super(player);
	} 

	public void normalBehaviour(DeckDistrict deckDistrict) {
		ArrayList<District> buidableDistrict = districtWeCanBuild(player.getDistrictCards());
		ArrayList<District> cheapersDistrictsBuildable = getAllCheapersDistricts(buidableDistrict);

		if (player.getDistrictCardsSize() == 0 || cheapersDistrictsBuildable.size() == 0)
			takeCard(deckDistrict);
		else {
			takeGold();
		}
		ifPossibleBuildACheapDistrict();
	}

	protected void endGameBehaviour(DeckDistrict deckDistrict) {
		printC.printPhase("Endgame", player);
		
		ArrayList<District> futurBuildableDistrict = getBuildableDistrictWithTwoMoreGold();
		if(futurBuildableDistrict.size() > 0) // s'il peut poser un bat en prenant les deux gold
			takeGold();
		else 
			takeCard(deckDistrict);
	
		ifPossibleBuildADistrict();
	}

	protected void lastTurnBehaviour(DeckDistrict deckDistrict) {
		endGameBehaviour(deckDistrict);
	}

	protected void ifPossibleBuildACheapDistrict() {
		ArrayList<District> districtWeCanBuild = listOfDistrictBuildable();
		if (!districtWeCanBuild.isEmpty()) {
			District cheaperDistrict = getCheaperDistrict(districtWeCanBuild);
			if (cheaperDistrict.getValue() <= MAX_VALUES_OF_CARDS) {
				buildDistrict(cheaperDistrict);
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
