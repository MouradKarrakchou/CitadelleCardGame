package fr.unice.polytech.citadelle.game_interactor;

import java.util.ArrayList;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;

/**
 * CityManagement class is used to allow a Behaviour to communicate with the given player's city.
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class CityManagement {
	Player player;
	
	public CityManagement(Player player){
		this.player = player;
	}

	/**
	 * Buildable district are districts player doesn't have in its city and where it has enough gold to build.
	 * @return The list of buildable districts the player has by imagining 2 more golds.
	 */
	public ArrayList<District> getBuildableDistrictWithTwoMoreGold(){
		return districtWeCanBuild(districtWeHaveEnoughMoneyToBuild(player.getGolds() + 2));
	}

	/**
	 * Buildable district are districts player doesn't have in its city and where it has enough gold to build.
	 * @return The list of buildable districts the player has.
	 */
	public ArrayList<District> listOfDistrictBuildable() {
		return districtWeCanBuild(districtWeHaveEnoughMoneyToBuild(player.getGolds()));
	}

	/**
	 * @param districtCards A given list of district cards.
	 * @return The list of district cards from the 'districtCards' the player doesn't have in its city.
	 */
	public ArrayList<District> districtWeCanBuild(ArrayList<District> districtCards) {
		return districtCards.stream().filter(district -> !(isAlreadyBuilt(district.getName())))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * @param gold The amount of golds.
	 * @return The list of districts player is able to built with the given amount of golds.
	 */
	public ArrayList<District> districtWeHaveEnoughMoneyToBuild(int gold) {
		return player.getDistrictCards().stream().filter(district -> 
		district.getValue() <= gold)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Check district given by its name is already built on the player's city.
	 * @param nameOfDistrict The name of the district to check.
	 * @return true/false - The district is already built in the city.
	 */
	public boolean isAlreadyBuilt(String nameOfDistrict) {
		ArrayList<District> districtIsBuilt;
		districtIsBuilt = player.getCity().getBuiltDistrict().stream()
				.filter(builtDistrict -> builtDistrict.getName().equals(nameOfDistrict))
				.collect(Collectors.toCollection(ArrayList::new));
		return districtIsBuilt.size() == 1;
	}
}
