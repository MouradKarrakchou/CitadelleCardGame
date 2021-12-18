package fr.unice.polytech.citadelle.game_character;

import java.util.LinkedHashMap;
import java.util.Optional;

import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class King extends Character {
    public King(){
        super("King", 4 );
    }

    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters){
        PrintCitadels.printKingSpell(bot.getPlayer());
        super.spellOfTurnDistrictFamily(bot,"King","Nobility");
    }}
