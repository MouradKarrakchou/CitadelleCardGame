package fr.unice.polytech.citadelle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;

import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Controller;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class Statisticator {
	private static final DecimalFormat dfZero = new DecimalFormat("0.00");
	private final ArrayList<StatsBot> listOfBotStats;
	
	public Statisticator() {
		listOfBotStats = new ArrayList<>();
	}

	/**
	 * Run a Thousand game.
	 * Save the stats of the 1000 game on the listOfBotStats.
	 */
	public void runAThousandGames() {
		// Only read the logs with a Warning level
		PrintCitadels.activateLevelWarning();

		for(int i=0; i<100; i++){
			// Run one game
			Controller controller = new Controller();
			controller.initGame();
			ArrayList<Player> leaderboard = controller.runGame();

			// Update all bots stats with the game leaderboards
			updateBotStats(leaderboard);
		}

		System.out.println();
		System.out.println("================  Win-rate after 1000 games================");
		for (StatsBot statsBot : listOfBotStats)
			System.out.println(statsBot.getName() + " : " + dfZero.format(statsBot.getWinrate()) + "%");
	}

	/**
	 * For a given bot name, return - if exist - the associated StatsBot object.
	 * @param name The name of the bot
	 * @return If exists, the associated statistic object.
	 */
	public Optional<StatsBot> getBotStats(String name){
		for (StatsBot statsBot : listOfBotStats)
			if (statsBot.getName().equals(name))
				return Optional.of(statsBot);
		return Optional.empty();
	}

	/**
	 * Update the list of BotStats according to the leaderboard results.
	 * @param leaderboard The leaderboard of a game.
	 */
	public void updateBotStats(ArrayList<Player> leaderboard){
		for(int i=0; i<leaderboard.size(); i++){
			String currentBotName = leaderboard.get(i).getName();
			StatsBot currentBotStats;

			// Checking if the bot is already in the listOfBotStats
			if (getBotStats(currentBotName).isPresent())
				currentBotStats = getBotStats(currentBotName).get();
			else {
				currentBotStats = new StatsBot(currentBotName);
				listOfBotStats.add(currentBotStats);
			}

			currentBotStats.increaseNumberOfGamePlay();
			if (i == 0)
				currentBotStats.increaseNumberOfGameWin();
		}
	}
}
