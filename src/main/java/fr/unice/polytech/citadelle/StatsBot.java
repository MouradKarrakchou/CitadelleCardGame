package fr.unice.polytech.citadelle;

import com.diogonunes.jcolor.Attribute;

import java.text.DecimalFormat;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class StatsBot implements Comparable {
	private final String botName;
	public int numberOfGamePlayed = 0;
	public int numberOfGameWon = 0;
	public int averageScore = -1;

	/**
	 * Initialize a new StatsBot with no game played.
	 * @param botName The name of the bot.
	 */
	public StatsBot(String botName) {
		this.botName = botName;
	}

	/**
	 * Initialize a StatsBot with a name, and its current statistics.
	 * If the bot never play a game, please refer to the other StatsBot constructor.
	 * @param botName The name of the bot
	 * @param numberOfGamePlayed The number of game played by the bot.
	 * @param numberOfGameWon The number of game won by the bot.
	 */
	public StatsBot(String botName, int numberOfGamePlayed, int numberOfGameWon, int averageScore) {
		this.botName = botName;
		this.numberOfGamePlayed = numberOfGamePlayed;
		this.numberOfGameWon = numberOfGameWon;
		this.averageScore = averageScore;
	}

	public String getName() {
		return botName;
	}

	public float getWinrate() {
		if (numberOfGamePlayed == 0)
			return 0;

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		return Float.parseFloat(df.format(numberOfGameWon / (float) numberOfGamePlayed * 100).replace(",", "."));
	}

	public void increaseNumberOfGamePlay() {
		this.numberOfGamePlayed++;
	}

	public void increaseNumberOfGameWin() {
		this.numberOfGameWon++;
	}

	@Override
	public int compareTo(Object o) {
		return (int) (((StatsBot) o).getWinrate() * 100 - getWinrate() * 100);
	}

	@Override
	public String toString() {
		String output = colorize(botName + ": ", BRIGHT_WHITE_TEXT())
				+ colorize(getWinrate() +"%", GREEN_TEXT(), BOLD());


		if (averageScore != -1)
			output += colorize(", ", BRIGHT_WHITE_TEXT())
			 		+ numberOfGamePlayed
					+ " games played"
					+ colorize(" (average score: " + averageScore + ")", BRIGHT_WHITE_TEXT());

		return output;
	}
}
