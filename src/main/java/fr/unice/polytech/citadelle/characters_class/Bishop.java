package fr.unice.polytech.citadelle.characters_class;

import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

public class Bishop extends Character {
    public Bishop(){
        super("Bishop", 5);
    }
    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters, PrintCitadels printC){
        int moneyEarned=super.collectGold(bot,"Religion");
        super.spellOfTurnDistrictFamily(bot,"Bishop","Religion",printC);
    }
}
