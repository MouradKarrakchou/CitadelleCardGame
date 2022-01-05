package fr.unice.polytech.citadelle;

public class StatsBot implements Comparable {
	private final String botName;
	private int numberOfGamePlay = 0;
	private int numberOfGameWin = 0;

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
	 * @param numberOfGamePlay The number of game played by the bot.
	 * @param numberOfGameWin The number of game won by the bot.
	 */
	public StatsBot(String botName, int numberOfGamePlay, int numberOfGameWin) {
		this.botName = botName;
		this.numberOfGamePlay = numberOfGamePlay;
		this.numberOfGameWin = numberOfGameWin;
	}

	public String getName() {
		return botName;
	}

	public double getWinrate() {
		return numberOfGamePlay == 0 ? 0 : (numberOfGameWin / (float) numberOfGamePlay) * 100;
	}

	public void increaseNumberOfGamePlay() {
		this.numberOfGamePlay++;
	}

	public void increaseNumberOfGameWin() {
		this.numberOfGameWin++;
	}

	@Override
	public int compareTo(Object o) {
		return (int) (((StatsBot) o).getWinrate() * 100 - getWinrate() * 100);
	}
}
