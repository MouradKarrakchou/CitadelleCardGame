package fr.unice.polytech.startingpoint.characters_class;

import fr.unice.polytech.startingpoint.Game;
import fr.unice.polytech.startingpoint.Player;

public class Assassin extends Character{
	Player target;
	
    public Assassin(){
        super("Assassin", 1);
    }
    
    @Override
    public void spellOfBeginningOfRound(Player robot, Game game){
    	killAPlayer(robot, game);
    }
    
    private void killAPlayer(Player robot, Game game) {
    	target = chooseTarget(game);
    }
    
	private Player chooseTarget(Game game) {
		//Always select the first player of the game
		//par la suite je pense que le bot doit choisir pas la carte
		Player target = game.getListOfPlayer().get(0);
		return target;
	}
}
