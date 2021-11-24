package fr.unice.polytech.citadelle.game_engine;

import fr.unice.polytech.citadelle.game.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Referee {

    ArrayList<Player> listOfPlayer;

    public Referee(ArrayList<Player> listOfPlayer){
        this.listOfPlayer=listOfPlayer;
    }
    public void calculateScoreOfPlayer(Player player) {
        player.getDistrictCards().forEach(district ->
        {
            player.updateScore(district.getValue());
        });
    }

    public void getWinner() {
        listOfPlayer.forEach(player -> calculateScoreOfPlayer(player));
        Collections.sort(listOfPlayer);
        Collections.reverse(listOfPlayer);
        for (int i = 0; i < listOfPlayer.size(); i++) listOfPlayer.get(i).setRank(i + 1);
    }
}
