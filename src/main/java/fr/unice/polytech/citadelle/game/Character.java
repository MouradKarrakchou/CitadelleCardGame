package fr.unice.polytech.citadelle.game;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 *A character is a class that gives the player special abilities.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Character {
    String name;
    int value;
    Player player;

    public Character(String name, int value) {
    	this.name = name;    	
    	this.value = value;    	
    }
    
    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }


    public void spellOfTurn(Bot bot, Board board, PrintCitadels printC){}

    @Override
    public boolean equals(Object obj) {
        Character other = (Character) obj;
        if(name.equals(other.getName())) return true;
        return  false;
    }

    @Override
    public String toString() {
        return name;
    }
}
