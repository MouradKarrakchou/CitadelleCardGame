package fr.unice.polytech.citadelle.characters_class;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class King extends Character {
    public King(){
        super("King", 4 );
    }

    @Override
    public void spellOfTurn(Bot bot, LinkedHashMap<Character, Optional<Bot>> hashOfCharacters, PrintCitadels printC){
        List<Optional<Bot>> listOfBot=hashOfCharacters.values().stream().toList();
        for (int i=0;i<listOfBot.size();i++){
            if (listOfBot.get(i).isPresent()&&bot.equals(listOfBot.get(i).get()))
                bot.setBotIsKing(true);
        }
        printC.printKingSpell(bot.getPlayer());
    }
}
