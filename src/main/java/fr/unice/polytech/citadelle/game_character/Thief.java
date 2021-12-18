package fr.unice.polytech.citadelle.game_character;

import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 * Announce the title of a character from whom you wish to steal.
 * When the player who has that character is called upon to take his turn, you first take all of his gold.
 * You may not steal from the Assassin or the Assassin's target.
 */
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
