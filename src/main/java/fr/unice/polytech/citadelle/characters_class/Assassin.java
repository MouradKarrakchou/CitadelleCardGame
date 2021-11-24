package fr.unice.polytech.citadelle.characters_class;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.Game;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class Assassin extends Character{
	Player target;
	
    public Assassin(){
        super("Assassin", 1);
    }

    @Override
    public void spellOfTurn(Bot bot, Game game, PrintCitadels printC){
        Character characterToDie= bot.selectCharacterForAssassin(game.getHashOfCharacters());
        game.getHashOfCharacters().get(characterToDie).setBotIsAlive(false);
        printC.killCharacter(characterToDie);
    }
}
