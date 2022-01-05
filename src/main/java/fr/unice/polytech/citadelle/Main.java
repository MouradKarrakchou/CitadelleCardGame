package fr.unice.polytech.citadelle;

import java.util.ArrayList;

import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Controller;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 * Initialize and run a Game
 *
 * @author BONNET Kilian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Main {


	public static void main(String... args) throws Exception {
		// Running a game
		PrintCitadels.activateLevelInfo();
		Controller controller = new Controller();
		controller.initGame();

		ArrayList<Player> leaderboard = controller.runGame();

		// Running 1000 game and displaying bot win ratio
		Statisticator stats = new Statisticator();
		stats.runAThousandGames();

		// Reading CSV file
		PrintCitadels.activateLevelInfo();
		CsvManager csv = new CsvManager();
		csv.printCSV();
	}
}