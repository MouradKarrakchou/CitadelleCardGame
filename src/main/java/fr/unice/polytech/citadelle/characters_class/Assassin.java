package fr.unice.polytech.citadelle.characters_class;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;

public class Assassin extends Character{
	Player target;
	
    public Assassin(){
        super("Assassin", 1);
    }

    @Override
    public void spellOfTurn(Bot bot, LinkedHashMap<Character, Bot> hashOfCharacters, PrintCitadels printC){
        Character characterToDie= bot.selectCharacterForAssassin(hashOfCharacters);
        hashOfCharacters.get(characterToDie).setBotIsAlive(false);
        printC.killCharacter(characterToDie);
    }
}
