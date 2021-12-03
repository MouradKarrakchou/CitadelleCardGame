package fr.unice.polytech.citadelle.game_engine;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Referee {

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
}
