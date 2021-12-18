package fr.unice.polytech.citadelle.game_engine;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.purple_districts.DragonGate;
import fr.unice.polytech.citadelle.game.purple_districts.HauntedCity;
import fr.unice.polytech.citadelle.game.purple_districts.University;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Check the results, give the scores and the ranks
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Referee {
	public static final int BONUS_FIRST = 4;
	public static final int BONUS_END = 2;
	private final Board board;

    public Referee(Board board){
        this.board = board;
    }

    /**
     * Called once for each player at the end of the game.
     * Allow the Referee to calculate the score of a given player.
     * @param player The player to calculate the score.
     */
    public void updatePlayerWithCityScore(Player player) {
        //Color joker in order to get 5 different district colors.
        if (isThereHauntedCityJokerInCity(player).size() != 0)
            activateHauntedCityJoker(player, isThereHauntedCityJokerInCity(player));

        // City Score processing.
        int scoreToAdd = cityDistrictScore(player);

        // Add 3 bonus points if the player has 5 built cites of different colors.
        if (hasFiveDistrictColors(player)) scoreToAdd += 3;

        // Update Player score according to city district values.
        player.updateScore(scoreToAdd);

        if (isThereBonusDistrictInCity(player).size() != 0)
            activateBonus(player, isThereBonusDistrictInCity(player));
    }

    /**
     * Adding bonus based on city completion for each player.
     * @param leaderBoard The game leaderboard.
     */
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

    /**
     * Called once at the end of the game.
     * Updating each player score player and set each player rank properly.
     */
    public void getWinner() {
    	ArrayList<Player> listOfPlayer = board.getListOfPlayer();
        listOfPlayer.forEach(this::updatePlayerWithCityScore);
        Collections.sort(listOfPlayer);
        Collections.reverse(listOfPlayer);
        for (int i = 0; i < listOfPlayer.size(); i++)
            listOfPlayer.get(i).setRank(i + 1);
    }

    /**
     * Return each bonus districts in the player city.
     * @param player The player to process.
     * @return The list of all bonus districts in the player city.
     */
    private ArrayList<BonusDistrict> isThereBonusDistrictInCity(Player player) {
        ArrayList<District> builtDistrict = player.getCity().getBuiltDistrict();
        ArrayList<BonusDistrict> bonusDistrictList = new ArrayList<>();

        for (District district : builtDistrict) {
            if (district.getName().equals("Dragon Gate"))
                bonusDistrictList.add(new DragonGate("Dragon Gate", 6,"Purple","Prestige"));
            if (district.getName().equals("University"))
                bonusDistrictList.add(new University("University", 6,"Purple","Prestige"));
        }

        return bonusDistrictList;
    }

    /**
     * Activate for a given player each bonus districtList found in the player city.
     * @param player The player to process.
     * @param bonusDistrictList The list of all bonus districts in the player city.
     */
    private void activateBonus(Player player, ArrayList<BonusDistrict> bonusDistrictList) {

        for (BonusDistrict bonusDistrict : bonusDistrictList) {
            bonusDistrict.bonusDistrict(player);
        }
    }

    /**
     * Return each haunted city joker districts in the player city.
     * @param player The player to process.
     * @return The list of all haunted city joker districts in the player city.
     */
    public ArrayList<HauntedCity> isThereHauntedCityJokerInCity(Player player) {
        ArrayList<HauntedCity> hauntedCityJoker = new ArrayList<>();

        ArrayList<District> builtDistrict = player.getCity().getBuiltDistrict();

        ArrayList<District> purpleDistricts = builtDistrict.stream()
                .filter(district -> district.getColor().equals("Purple"))
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<District> builtDistrictHC = builtDistrict.stream()
                .filter(district -> district.getName().equals("Haunted City"))
                .collect(Collectors.toCollection(ArrayList::new));

        if(!builtDistrictHC.isEmpty() && purpleDistricts.size() > 1) {
            HauntedCity hauntedCity = (HauntedCity) builtDistrictHC.get(0);
            if (hauntedCity.getRoundBuilt() < board.getRoundNumber()) {
                hauntedCityJoker.add(hauntedCity);
                return hauntedCityJoker;
            }
        }

        return hauntedCityJoker;
    }

    /**
     * Activate for a given player each bonus haunted city joker found in the player city.
     * @param player The player to process.
     * @param hauntedCityJoker The list of all haunted city joker districts in the player city.
     */
    public void activateHauntedCityJoker(Player player, ArrayList<HauntedCity> hauntedCityJoker) {
        hauntedCityJoker.get(0).hauntedCitySpell(player);
    }

    /**
     * Check if the city of a given player is completed.
     * @param player The player to process.
     * @return true/false The player has completed its city.
     */
	public boolean CityIsComplete(Player player) {
        return player.getCity().getSizeOfCity() >= 8;
    }

}
