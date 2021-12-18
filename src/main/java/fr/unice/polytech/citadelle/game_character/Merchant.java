package fr.unice.polytech.citadelle.game_character;

import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 *  You receive one gold for each trade (green) district in your city. After you take an action, you receive one additional gold.
 *  @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
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
