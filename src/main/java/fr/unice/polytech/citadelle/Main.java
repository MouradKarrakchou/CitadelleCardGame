package fr.unice.polytech.citadelle;

import java.util.ArrayList;

import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Controller;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 * Initialize and run a Game
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Main {

	public static void main(String... args) throws Exception {
		PrintCitadels.activateLevelInfo();
		Controller controller = new Controller();
		Statisticator statisticator = new Statisticator();
		ArrayList<Player> leaderboard = new ArrayList<>();
		
		controller.initGame();
		leaderboard = controller.runGame();

		CsvManager csv = new CsvManager(leaderboard);
		csv.write();

	}

}