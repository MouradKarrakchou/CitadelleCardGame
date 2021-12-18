package fr.unice.polytech.citadelle.game_character;

import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * The architect automatically draws two cards.
 * In addition of the tour collection (cards or coins), the architect can build up to 3 districts in one round.
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Architect extends Character {
	
    public Architect(){
        super("Architect", 7);
    }

    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters){
        //Giving 2 Cards to the Player with Architect character
        PrintCitadels.printArchitectSpell();
        bot.addDistrict(bot.pickCard());
        bot.addDistrict(bot.pickCard());
    }
}
