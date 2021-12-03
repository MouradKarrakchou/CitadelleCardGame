package fr.unice.polytech.citadelle.characters_class;

import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class Merchant extends Character {
    public Merchant(){
        super("Merchant", 6);
    }

    private int collectGold(Behaviour bot){
        int goldEarned=bot.getPlayer().getCity().getBuiltDistrict().stream()
                .filter(district -> district.getNameOfFamily().equals("Trade and Handicrafts"))
                .map(district ->1)
                .reduce(1, (total, count) -> total + count);
        bot.getPlayer().setGolds(goldEarned);
        return(goldEarned);
    }
    
    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters, PrintCitadels printC){
        printC.printMerchantEarnedMoney(collectGold(bot));
    }
}
