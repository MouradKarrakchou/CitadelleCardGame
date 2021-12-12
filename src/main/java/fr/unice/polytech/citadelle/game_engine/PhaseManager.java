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

	/**
	 * Analyse the game phase according to PhaseManager class parameters.
	 * @param listCityOfPlayers The list of players' city.
	 * @return If the game is in the last round, close to be finished or in process.
	 */
	public String analyseGame(ArrayList<City> listCityOfPlayers) {		
		if(isLastTurnPhase(listCityOfPlayers)) return LAST_TURN_PHASE;
		if(isEndGamePhase(listCityOfPlayers)) return END_GAME_PHASE;
		return MID_GAME_PHASE;
	}

	/**
	 * Check if the game is in the last turn according to the number of districts in each players' city.
	 * @param listCityOfPlayers The list of players' city.
	 * @return true/false : the game is in the last turn.
	 */
	private boolean isLastTurnPhase(ArrayList<City> listCityOfPlayers) {
		for(City c : listCityOfPlayers)
			if(c.isComplete())
				return true;
		return false;
	}

	/**
	 * Check if the game is in the end game according to the number of districts in each players' city.
	 * @param listCityOfPlayers The list of players' city.
	 * @return true/false : it's the end game.
	 */
	private boolean isEndGamePhase(ArrayList<City> listCityOfPlayers) {
		for(City c : listCityOfPlayers)
			if(c.getSizeOfCity() >= 6)
				return true;
		return false;
	}
}
