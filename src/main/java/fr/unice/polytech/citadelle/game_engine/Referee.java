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
    public void calculateScoreOfPlayer(Player player) {
    	ArrayList<District> city = player.getCity().getBuiltDistrict();
    	city.forEach(district ->
        {
            player.updateScore(district.getValue());
        });
    }

    public void getWinner() {
    	ArrayList<Player> listOfPlayer = board.getListOfPlayer();
        listOfPlayer.forEach(player -> calculateScoreOfPlayer(player));
        Collections.sort(listOfPlayer);
        Collections.reverse(listOfPlayer);
        for (int i = 0; i < listOfPlayer.size(); i++) listOfPlayer.get(i).setRank(i + 1);
    }
}
