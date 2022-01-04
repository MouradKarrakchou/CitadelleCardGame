package fr.unice.polytech.citadelle;

public class StatsBot {
	private String name;
	private int numberOfGamePlay;
	private int numberOfGameWin;
	private int numberOfGameLoose;
	private double percentageOfGameWin;
	private double percentageOfGameLoose;

	public StatsBot(String name, int numberOfGamePlay,int numberOfGameWin, int numberOfGameLoose, int percentageOfGameWin, int percentageOfGameLoose) {
		this.name = name;
		this.numberOfGamePlay = numberOfGamePlay;
		this.numberOfGamePlay = numberOfGameWin;
		this.numberOfGamePlay = numberOfGameLoose;
		this.numberOfGamePlay = percentageOfGameWin;
		this.numberOfGamePlay = percentageOfGameLoose;
	}

	public String getName() {
		return name;
	}
	
	public int getNumberOfGamePlay() {
		return numberOfGamePlay;
	}

	public void setNumberOfGameWin(int numberOfGameWin) {
		this.numberOfGameWin = numberOfGameWin;
	}

	public int getNumberOfGameLoose() {
		return numberOfGameLoose;
	}

	public double getPercentageOfGameWin() {
		return percentageOfGameWin;
	}

	public void setPercentageOfGameWin(double percentageOfGameWin) {
		this.percentageOfGameWin = percentageOfGameWin;
	}

	public double getPercentageOfGameLoose() {
		return percentageOfGameLoose;
	}

	public void setPercentageOfGameLoose(double percentageOfGameLoose) {
		this.percentageOfGameLoose = percentageOfGameLoose;
	}

	public void IncreaseNumberOfGamePlay() {
		this.numberOfGamePlay++;
	}

	public void IncreaseNumberOfGameWin() {
		this.numberOfGameWin++;
	}
	
	public void IncreasenumberOfGameLoose() {
		this.numberOfGameLoose++;
	}

}
