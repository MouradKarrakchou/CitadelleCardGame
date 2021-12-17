package fr.unice.polytech.citadelle.characters_class;

import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

public class Warlord extends Character {
    public Warlord(){
        super("Warlord", 8);
    }
    private void destroyDistrict(Player player, District districtToDestroy) {
        player.getCity().getBuiltDistrict().remove(districtToDestroy);
    }
    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters){
        Character characterToDestroy= bot.selectCharacterForSpell(hashOfCharacters);
        if (characterToDestroy==null)
            PrintCitadels.warlordDontUseSpell(bot.getPlayer());
        else{
            if (hashOfCharacters.get(characterToDestroy).isPresent()) {
                Player playerToDetroy=hashOfCharacters.get(characterToDestroy).get().getPlayer();
                District districtToDestroy=bot.chooseDistrictToDestroy(playerToDetroy);
                if (districtToDestroy==null)
                    PrintCitadels.warlordDontUseSpell(bot.getPlayer());
                else
                {destroyDistrict(playerToDetroy,districtToDestroy);
                    PrintCitadels.warlordDoHisSpell(bot.getPlayer(),characterToDestroy, districtToDestroy);}
            }
            else
                PrintCitadels.warlordTargetedACharacterUnpicked(bot.getPlayer(),characterToDestroy);}
    }

}
