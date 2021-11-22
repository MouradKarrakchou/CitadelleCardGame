package fr.unice.polytech.citadelle.game;

import java.util.Objects;

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

    
	@Override
	public boolean equals(Object obj) {
		Character other = (Character) obj;
		if(name.equals(other.getName())) return true;
		return  false;
	}

	public void spellOfBeginningOfRound(Player player, Game game){}

    public void printOfBeginningOfTurn(Player player, PrintCitadels printC){}
}
