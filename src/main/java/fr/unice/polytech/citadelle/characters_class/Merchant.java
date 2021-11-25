package fr.unice.polytech.citadelle.characters_class;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class Merchant extends Character {
    public Merchant(){
        super("Merchant", 6);
    }

    private int collectGold(Bot bot){
        int goldEarned=bot.getPlayer().getCity().getBuiltDistrict().stream()
                .filter(district -> district.getNameOfFamily().equals("Trade and Handicrafts"))
                .map(district ->1)
                .reduce(1, (total, count) -> total + count);
        bot.getPlayer().setGolds(goldEarned);
        return(goldEarned);
    }
    @Override
    public void spellOfTurn(Bot bot, LinkedHashMap<Character, Optional<Bot>> hashOfCharacters, PrintCitadels printC){
        printC.printMerchantEarnedMoney(collectGold(bot));
    }
}
