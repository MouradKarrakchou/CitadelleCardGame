package fr.unice.polytech.citadelle.game_character;

import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

public class Thief extends Character {
    public Thief(){
        super("Thief", 2);
    }

    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters){
        Character characterToSteal= bot.selectCharacterForSpell(hashOfCharacters);
        if (hashOfCharacters.get(characterToSteal).isPresent()) {
            int goldStolen = hashOfCharacters.get(characterToSteal).get().getPlayer().stealGoldOfThePlayer(bot.getPlayer());
            PrintCitadels.stealCharacter(characterToSteal, goldStolen);
        }
        else
            PrintCitadels.failedToStealCharacter(characterToSteal);
    }
}
