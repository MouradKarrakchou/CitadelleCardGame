package fr.unice.polytech.citadelle.characters_class;

import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

public class Magician extends Character {
    public Magician(){
        super("Magician", 3);
    }
    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters, PrintCitadels printC){
        bot.chooseMagicianAction();
    }
}
