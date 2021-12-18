package fr.unice.polytech.citadelle.game_character;

import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 * You receive one gold for each military (red) district in your city.
 * You may destroy one district of your choice by paying a number of gold equal to one less than the cost of the district.
 * Thus, you may destroy a cost one district for free, a cost two district for one gold, or a cost six district for five gold, etc.
 * You may destroy one of your own districts.
 * You may not, however, destroy a district in a city that is already completed by having eight districts (or seven districts when the Bell Tower is in play).
 */
public class Warlord extends Character {

    public Warlord(){
        super("Warlord", 8);
    }

    private void destroyDistrict(Player playerToDestroy, District districtToDestroy,Player walordPlayer) {
        playerToDestroy.getCity().getBuiltDistrict().remove(districtToDestroy);
        walordPlayer.setGolds(walordPlayer.getGolds()-(districtToDestroy.getValue()-1));
    }
    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters){
        super.spellOfTurnDistrictFamily(bot,"Warlord","Soldiery");
        Player playerToDestroy= bot.selectPlayerForWarlord();
        if (playerToDestroy==null)
            PrintCitadels.warlordDontUseSpell(bot.getPlayer());
        else{
            District districtToDestroy=bot.chooseDistrictToDestroy(playerToDestroy);

            if (districtToDestroy==null)
                PrintCitadels.warlordDontUseSpell(bot.getPlayer());
            else {
                    destroyDistrict(playerToDestroy,districtToDestroy,bot.getPlayer());
                    PrintCitadels.warlordDoHisSpell(bot.getPlayer(),playerToDestroy, districtToDestroy);}}
    }

}
