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
	private static final int NUMBER_OF_RICHALPHONSE = 1;
	private static final int NUMBER_OF_INVESTOR =  1;
	private static final int NUMBER_OF_RUSHER = 1;
	private static final int NUMBER_OF_STRATEGATOR = 1;
	private static final boolean ENABLE_LOG = false;

	public static void main(String... args) throws Exception {
		// Running a game
		if (ENABLE_LOG)
			PrintCitadels.activateLevelInfo();
		else
			PrintCitadels.activateLevelWarning();

		Controller controller = new Controller();
		controller.initGame(NUMBER_OF_RICHALPHONSE, NUMBER_OF_INVESTOR, NUMBER_OF_RUSHER, NUMBER_OF_STRATEGATOR);

		ArrayList<Player> leaderboard = controller.runGame();

		// Running 1000 game and displaying bot win ratio
		Statisticator stats = new Statisticator();
		stats.runAThousandGames(NUMBER_OF_RICHALPHONSE, NUMBER_OF_INVESTOR, NUMBER_OF_RUSHER, NUMBER_OF_STRATEGATOR);

		// Reading CSV file
		PrintCitadels.activateLevelInfo();
		CsvManager csv = new CsvManager("save\\results.csv");
		csv.printCSV();

		//Battlefield (where the best bot is against the best bot)
		Battlefieldator battlefield = new Battlefieldator(csv);
		battlefield.runTheBattle();
		battlefield.runTheSecondBattle();
	}
}