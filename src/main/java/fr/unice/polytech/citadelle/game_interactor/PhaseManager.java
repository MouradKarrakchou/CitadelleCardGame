package fr.unice.polytech.citadelle.game_interactor;

import java.util.ArrayList;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.City;
import fr.unice.polytech.citadelle.game.Player;

/**
 * The PhaseManager analyze the Game to deduct a particular phase
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class PhaseManager {
	public final static String MID_GAME_PHASE = "MID_GAME_PHASE";
	public final static String END_GAME_PHASE = "EsND_GAME_PHASE";
	public final static String LAST_TURN_PHASE = "LAST_TURN_PHASE";
	
	private Player player;
	private Board board;
	
	public PhaseManager(Player player, Board board) {
		this.player = player;
		this.board = board;
	}

	/**
	 * Analyse the game phase according to PhaseManager class parameters.
	 * @return If the game is in the last round, close to be finished or in process.
	 */
	public String analyseGame() {		
		if(isLastTurnPhase()) return LAST_TURN_PHASE;
		if(isEndGamePhase()) return END_GAME_PHASE;
		return MID_GAME_PHASE;
	}

	/**
	 * Check if the game is in the last turn according to the number of districts in each players' city.
	 * @return true/false : the game is in the last turn.
	 */
	boolean isLastTurnPhase() {
		ArrayList<City> listOfCity = board.getListOfCity();
		for(City c : listOfCity)
			if(c.isComplete())
				return true;
		return false;
	}

	/**
	 * Check if the player is in the end game according to the number of districts of his city.
=	 * @return true/false : it's the end game.
	 */
	boolean isEndGamePhase() {
		if(player.getCity().getSizeOfCity() >= 6)
			return true;
		return false;
	}
}
