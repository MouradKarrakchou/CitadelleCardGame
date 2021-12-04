package fr.unice.polytech.citadelle.game_interactor;

import java.util.ArrayList;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;

public class CityManagement {
	Player player;
	
	public CityManagement(Player player){
		this.player = player;
	}
	
	public ArrayList<District> getBuildableDistrictWithTwoMoreGold(){
		return districtWeCanBuild(districtWeHaveEnoughMoneyToBuild(player.getGolds() + 2));
	}
	
	public ArrayList<District> listOfDistrictBuildable() {
		System.out.println("listOfDistrictBuildable, district =" + player.getDistrictCards());
		return districtWeCanBuild(districtWeHaveEnoughMoneyToBuild(player.getGolds()));
	}
	
	public ArrayList<District> districtWeCanBuild(ArrayList<District> districtCards) {
		System.out.println("districtWeCanBuild, district =" + player.getDistrictCards());
		return districtCards.stream().filter(district -> !(isAlreadyBuilt(district.getName())))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<District> districtWeHaveEnoughMoneyToBuild(int gold) {
		System.out.println("districtWeHaveEnoughMoneyToBuild, district =" + player.getDistrictCards());
		return player.getDistrictCards().stream().filter(district -> 
		district.getValue() <= gold)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public boolean isAlreadyBuilt(String nameOfDistrict) {
		ArrayList<District> districtIsBuilt;
		districtIsBuilt = player.getCity().getBuiltDistrict().stream()
				.filter(builtDistrict -> builtDistrict.getName().equals(nameOfDistrict))
				.collect(Collectors.toCollection(ArrayList::new));
		if (districtIsBuilt.size() != 1)
			return false;

		return true;
	}
}
