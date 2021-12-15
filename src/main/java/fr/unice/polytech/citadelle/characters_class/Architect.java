package fr.unice.polytech.citadelle.characters_class;

import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * The architect automatically draws two cards.
 * In addition of the tour collection (cards or coins), the architect can build up to 3 districts in one round.
 */
public class Architect extends Character {
	
    public Architect(){
        super("Architect", 7);
    }

    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters, PrintCitadels printC){
        //Giving 2 Cards to the Player with Architect character
        printC.printArchitectSpell();
        bot.addDistrict(bot.pickCard());
        bot.addDistrict(bot.pickCard());
    }
}
