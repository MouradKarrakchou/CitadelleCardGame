package fr.unice.polytech.citadelle.characters_class;

import java.util.ArrayList;

import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.Game;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class King extends Character {
    public King(){
        super("King", 4 );
    }
    private void orderListOfPlayer(ArrayList<Player> listOfPlayer,ArrayList<Player> listOfPlayerNextRound,int positionOfKingHolder){
        int positionToChange=positionOfKingHolder;
        int sizeListOfPlayer=listOfPlayer.size();
        listOfPlayerNextRound.clear();
        for (int i=0;i<sizeListOfPlayer;i++){
            if (positionToChange>=sizeListOfPlayer) positionToChange=0;
            listOfPlayerNextRound.add(listOfPlayer.get(positionToChange));
            positionToChange++;
        }
    }
    @Override
    public void spellOfBeginningOfRound(Player robot, Game game){
        ArrayList<Player> listOfPlayer=game.getListOfPlayer();
        for (int i=0;i<listOfPlayer.size();i++){
            if (robot.equals(listOfPlayer.get(i)))
                orderListOfPlayer(listOfPlayer, game.getListOfPlayerOfNextRound(),i);
        }
    }
    public void printOfBeginningOfTurn(Player player, PrintCitadels printC){
        printC.printKingSpell(player);
    }
}
