package fr.unice.polytech.citadelle.game_engine;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.BonusDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.purple_districts.DragonGate;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.game.purple_districts.University;

import java.util.ArrayList;
import java.util.Collections;

public class Referee {

	public static final int BONUS_FIRST = 4;
	public static final int BONUS_END = 2;
	Board board;

	
	
    public Referee(Board board){
        this.board=board;
    }
    public void updatePlayerWithCityScore(Player player) {
        // City Score
        int scoreToAdd = cityDistrictScore(player);
        // Add 3 bonus point if the player has 5 built city of different colors
        if (hasFiveDistrictColors(player)) scoreToAdd += 3;

        // Update Player score
        player.updateScore(scoreToAdd);

        if (isThereBonusDistrictInCity(player).size() != 0) activateBonus(player, isThereBonusDistrictInCity(player));
    }
    
	public void addBonusForPlayers(ArrayList<Behaviour> leaderBoard) {
		Player firstToEndCity = leaderBoard.get(0).getPlayer();
		firstToEndCity.updateScore(BONUS_FIRST);
		for(int i = 1; i < leaderBoard.size() ; i++) {
			Player firstWhoEndCity = leaderBoard.get(i).getPlayer();
			firstWhoEndCity.updateScore(BONUS_END);
		}
	}


    /**
     * @param player The player to process.
     * @return The score formed by summing built district score.
     */
    public int cityDistrictScore(Player player){
        int sum = 0;
        ArrayList<District> playerCity = player.getCity().getBuiltDistrict();

        for (District district : playerCity)
            sum += district.getValue();

        return sum;
    }

    /**
     * @param player The player to process.
     * @return True/False, the player has 5 district colors in its city.
     */
    public boolean hasFiveDistrictColors(Player player){
        ArrayList<District> playerCity = player.getCity().getBuiltDistrict();
        boolean hasBlue = false;
        boolean hasRed = false;
        boolean hasGreen = false;
        boolean hasYellow = false;
        boolean hasPurple = false;

        for (District district : playerCity){
            switch (district.getColor()) {
                case "Blue" -> hasBlue = true;
                case "Red" -> hasRed = true;
                case "Green" -> hasGreen = true;
                case "Yellow" -> hasYellow = true;
                case "Purple" -> hasPurple = true;
            }
        }

        return (hasBlue && hasRed && hasGreen && hasYellow && hasPurple);
    }

    public void getWinner() {
    	ArrayList<Player> listOfPlayer = board.getListOfPlayer();
        listOfPlayer.forEach(this::updatePlayerWithCityScore);
        Collections.sort(listOfPlayer);
        Collections.reverse(listOfPlayer);
        for (int i = 0; i < listOfPlayer.size(); i++) listOfPlayer.get(i).setRank(i + 1);
    }

    private ArrayList<BonusDistrict> isThereBonusDistrictInCity(Player player) {
        ArrayList<District> builtDistrict = player.getCity().getBuiltDistrict();
        builtDistrict.stream().filter(district -> district.getNameOfFamily().equals("Prestige"));

        ArrayList<BonusDistrict> bonusDistrictList = new ArrayList<>();

        for (District district : builtDistrict) {
            if (district.getName().equals("Dragon Gate"))
                bonusDistrictList.add(new DragonGate("Dragon Gate", 6,"Purple","Prestige"));
            if (district.getName().equals("University"))
                bonusDistrictList.add(new University("University", 6,"Purple","Prestige"));
        }

        return bonusDistrictList;
    }

    private void activateBonus(Player player, ArrayList<BonusDistrict> bonusDistrictList) {

        for (BonusDistrict bonusDistrict : bonusDistrictList) {
            bonusDistrict.bonusDistrict(player);
        }
    }
	public boolean CityIsComplete(Player player) {
		if(player.getCity().getSizeOfCity() >= 8) return true;
		return false;
	}

}
