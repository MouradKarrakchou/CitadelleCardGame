package fr.unice.polytech.citadelle.characters_class;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class King extends Character {
    public King(){
        super("King", 4 );
    }

    @Override
    public void spellOfTurn(Bot bot, LinkedHashMap<Character, Bot> hashOfCharacters, PrintCitadels printC){
        List<Bot> listOfBot=hashOfCharacters.values().stream().toList();
        for (int i=0;i<listOfBot.size();i++){
            if (bot.equals(listOfBot.get(i)))
                bot.setBotIsKing(true);
        }
        printC.printKingSpell(bot.getPlayer());
    }
}
