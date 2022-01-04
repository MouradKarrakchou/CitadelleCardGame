package fr.unice.polytech.citadelle;

import java.util.ArrayList;
import java.util.Optional;

import fr.unice.polytech.citadelle.game.Player;

public class Statisticator {
	
	private ArrayList<StatsBot> listOfStats;
	
	public Statisticator() {
		listOfStats = new ArrayList<>();
	}
	
	public ArrayList<StatsBot> addStatsBot(ArrayList<Player> listOfPlayer){
		ArrayList<StatsBot> stats = new ArrayList<StatsBot>();
		for(Player player: listOfPlayer){
			Optional<StatsBot> optionalStats;				
		}
		
		return listOfStats;	
	}
	
	private Optional<StatsBot> containStatsForThisPlayer(Player player) {
		for(StatsBot statsBot: listOfStats) {
			if(statsBot.getName() == player.getName())
				return Optional.of(statsBot);
		}
		return Optional.empty();
	}
}
