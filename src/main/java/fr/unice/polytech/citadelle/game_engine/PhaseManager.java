package fr.unice.polytech.citadelle.game_engine;

import java.util.ArrayList;

import fr.unice.polytech.citadelle.game.City;

/**
 * The PhaseManager analyze the Game to deduct a particular phase
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class PhaseManager {
	public final static String MID_GAME_PHASE = "MID_GAME_PHASE";
	public final static String END_GAME_PHASE = "END_GAME_PHASE";
	public final static String LAST_TURN_PHASE = "LAST_TURN_PHASE";

	
	public PhaseManager() {}
	
	public String analyseGame(ArrayList<City> listCityOfPlayers) {		
		if(isLastTurnPhase(listCityOfPlayers)) return LAST_TURN_PHASE;
		if(isEndGamePhase(listCityOfPlayers)) return END_GAME_PHASE;
		
		return MID_GAME_PHASE;
	}
	
	private boolean isLastTurnPhase(ArrayList<City> listCityOfPlayers) {
		boolean isLastTurn = false;
		
		for(City c : listCityOfPlayers)
			if(c.isComplete()) isLastTurn = true;
		
		return isLastTurn;
	}
	
	private boolean isEndGamePhase(ArrayList<City> listCityOfPlayers) {
		boolean isLastTurn = false;
		
		for(City c : listCityOfPlayers)
			if(c.getSizeOfCity() >= 6) isLastTurn = true;
		
		return isLastTurn;
	}
	
	
}
