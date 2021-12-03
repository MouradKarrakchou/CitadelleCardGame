package fr.unice.polytech.citadelle.characters_class;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class King extends Character {
    public King(){
        super("King", 4 );
    }

    private int collectGold(Behaviour bot){
        int goldEarned=bot.getPlayer().getCity().getBuiltDistrict().stream()
                .filter(district -> district.getNameOfFamily().equals("Nobility"))
                .map(district ->1)
                .reduce(0, (total, count) -> total + count);
        bot.getPlayer().setGolds(goldEarned);
        return(goldEarned);
    }
    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters, PrintCitadels printC){
        List<Optional<Behaviour>> listOfBot=hashOfCharacters.values().stream().toList();
        for (int i=0;i<listOfBot.size();i++){
            if (listOfBot.get(i).isPresent()&&bot.equals(listOfBot.get(i).get()))
                bot.setBehaviourIsKing(true);
        }
        printC.printKingSpell(bot.getPlayer());
        printC.printKingEarnedMoney(collectGold(bot));
    }
}
