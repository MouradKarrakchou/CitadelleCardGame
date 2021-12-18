package fr.unice.polytech.citadelle.game_character;

import java.util.LinkedHashMap;
import java.util.Optional;

import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;

/**
 * Bishop districts cannot be destroyed by Warlord.
 * Each religious districts bring one gold to the Bishop.
 */
public class Bishop extends Character {
    public Bishop(){
        super("Bishop", 5);
    }

    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters){
        super.spellOfTurnDistrictFamily(bot,"Bishop","Religion");
    }
}
