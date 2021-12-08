package fr.unice.polytech.citadelle.basic_actions;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;

import fr.unice.polytech.citadelle.game.Player;

public class TakeGoldAction extends BasicActions{
	Player player;
	
	public TakeGoldAction(Player player) {
		super();
		this.player = player;
	}
	
	@Override
	public String toString() {
		String coloredOutput = colorize("    [+] ", GREEN_TEXT());
		return coloredOutput + player.getName() + " takes two golds.";
	}

}
