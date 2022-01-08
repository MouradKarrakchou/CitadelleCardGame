package fr.unice.polytech.citadelle;

import com.diogonunes.jcolor.Attribute;

import java.text.DecimalFormat;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class StatsBot implements Comparable {
	private final String botName;
	private int numberOfGamePlayed = 0;
	private int numberOfGameWon = 0;
	private int averageScore = -1;
	private int score = 0;


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

	public float getLoseRate() {
		if (numberOfGamePlayed == 0)
			return 0;

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		return Float.parseFloat(df.format((numberOfGamePlayed - numberOfGameWon) / (float) numberOfGamePlayed * 100).replace(",", "."));
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
		String output = colorize(botName + " | "
				+ colorize(numberOfGameWon + " (" + getWinrate() + "%)", GREEN_TEXT())
				+ colorize(" | ", BRIGHT_WHITE_TEXT())
				+ (numberOfGamePlayed - numberOfGameWon) + " (" + getLoseRate() + "%) | "
				, BRIGHT_WHITE_TEXT());

		if (averageScore != -1)
			output += colorize("" + numberOfGamePlayed, BRIGHT_WHITE_TEXT());
		else
			output += colorize( "" + (score/1000), BRIGHT_WHITE_TEXT());

		return output;
	}

	public void increaseTotalScore(int score) {
		this.score += score;
	}

	public int getScore() {
		return score;
	}
}
