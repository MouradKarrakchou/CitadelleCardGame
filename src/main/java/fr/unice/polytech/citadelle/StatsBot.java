package fr.unice.polytech.citadelle;

public class StatsBot implements Comparable {
	private final String botName;
	private int numberOfGamePlay = 0;
	private int numberOfGameWin = 0;

	public StatsBot(String botName) {
		this.botName = botName;
	}

	public String getName() {
		return botName;
	}

	public int getNumberOfGamePlay() {
		return numberOfGamePlay;
	}

	public void setNumberOfGameWin(int numberOfGameWin) {
		this.numberOfGameWin = numberOfGameWin;
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
		return (int) (((StatsBot) o).getWinrate() - getWinrate());
	}
}
