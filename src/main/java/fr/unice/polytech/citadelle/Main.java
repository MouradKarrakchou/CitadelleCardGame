package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game_engine.Controller;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 * Initialize and run a Game
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Main {

	public static void main(String... args) {
		PrintCitadels.activateLevelInfo();
		Controller controller = new Controller();
		controller.initGame();
		controller.runGame();
	}
}