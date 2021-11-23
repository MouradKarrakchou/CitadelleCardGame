package fr.unice.polytech.citadelle.characters_class;

import java.util.ArrayList;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.Game;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class King extends Character {
    public King(){
        super("King", 4 );
    }
    private void orderListOfPlayer(ArrayList<Bot> listOfBot,ArrayList<Bot> listOfBotNextRound,int positionOfKingHolder){
        int positionToChange=positionOfKingHolder;
        int sizeListOfPlayer=listOfBot.size();
        listOfBotNextRound.clear();
        for (int i=0;i<sizeListOfPlayer;i++){
            if (positionToChange>=sizeListOfPlayer) positionToChange=0;
            listOfBotNextRound.add(listOfBot.get(positionToChange));
            positionToChange++;
        }
    }

    @Override
    public void spellOfTurn(Bot bot, Game game, PrintCitadels printC){
        ArrayList<Bot> listOfBot=game.getListOfBot();
        for (int i=0;i<listOfBot.size();i++){
            if (bot.equals(listOfBot.get(i)))
                orderListOfPlayer(listOfBot, game.getListOfBotOfNextRound(),i);
        }
        printC.printKingSpell(bot.getPlayer());
    }
}
