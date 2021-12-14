package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game_engine.Controller;

public class Main {

	public static void main(String... args) {
			Controller controller = new Controller();

				controller.initGame();
				controller.runGame();
	}
}