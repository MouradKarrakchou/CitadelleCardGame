package fr.unice.polytech.citadelle.game_character;

import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

public class Merchant extends Character {
    public Merchant(){
        super("Merchant", 6);
    }
    
    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters){
        bot.getPlayer().setGolds(bot.getPlayer().getGolds()+1);
        PrintCitadels.printMerchantEarnedStartRoundMoney();
        super.spellOfTurnDistrictFamily(bot,"Merchant","Trade and Handicrafts");
    }
}
