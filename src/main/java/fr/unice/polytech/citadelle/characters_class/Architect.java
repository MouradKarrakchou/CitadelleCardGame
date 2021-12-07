package fr.unice.polytech.citadelle.characters_class;

import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

public class Architect extends Character {
	
    public Architect(){
        super("Architect", 7);
    }
    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters, PrintCitadels printC){
        //Giving 2 Cards to the Player with Architect character
        bot.addDistrict(bot.pickCard());
        bot.addDistrict(bot.pickCard());
        bot.setBotIsArchitect(true);

    }
}
