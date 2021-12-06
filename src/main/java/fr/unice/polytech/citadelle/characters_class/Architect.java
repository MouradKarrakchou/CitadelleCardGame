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
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters, PrintCitadels printC){
        //Giving 2 Cards to the Player with Architect character
    	/*bot.takeCard(bot.pickCard());
    	bot.getPlayer().getDistrictCards().add(bot.pickCard());
        bot.getPlayer().getDistrictCards().add(bot.pickCard());
        bot.setBotIsArchitect(true);*/
    }
}
