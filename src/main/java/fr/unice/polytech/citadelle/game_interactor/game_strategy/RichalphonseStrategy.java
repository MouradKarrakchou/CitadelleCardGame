package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.function.Function;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.purple_districts.Library;
import fr.unice.polytech.citadelle.game_character.Character;


/**
 * @author leolb
 *
 */
public class RichalphonseStrategy {
    Board board;
    Player currentPlayer;    
    //---------------
    SituationLibrairie librairie;



    public RichalphonseStrategy(Board board, Player player) {
        this.board = board;
        this.currentPlayer = player;
        librairie=new SituationLibrairie(board);
    }

    
    /**
     * @param listOfRichardCharacterPickable
     * @return the best Situation according to actual game
     */
    public Situation getBestSituation(ArrayList<Character> listOfRichardCharacterPickable){
    	ArrayList<Situation> searchSituation = librairie.getLibrairieContent();
    	//searchSituation = librairie.filterByListOfCharacterNotPickable(searchSituation, );
    	searchSituation = librairie.filterByListOfCharacterPickable(searchSituation, listOfRichardCharacterPickable);
    	searchSituation = librairie.filterByPlayerCloseToWinPlayFirst(searchSituation, calculIsAPlayerCloseWinPlayFirst());
    	searchSituation = librairie.filterByDistanceBetweenRichardAndAPlayerCloseToWin(searchSituation, getDistanceBetweenRichardAndAPlayerCloseToWin());


    	//
    	Collections.sort(searchSituation);
    	Situation bestSituation =  searchSituation.get(0);
    	return bestSituation;
    }
    
    
    /**
     * @return True if a Player is close to win (7/8 Districts) and he is the first player to play, False in the others cases
     */
    private boolean calculIsAPlayerCloseWinPlayFirst() {
    	if(!aPlayerIsCloseToWin()) return false;
    	Player playerIsCloseToWin = getPlayerIsCloseToWin();
		Player firstPlayerToPlay = getFirstPlayerToPlay();

		if(playerIsCloseToWin.equals(firstPlayerToPlay)) return true;
		return false;
    }
    
    /**
     * @return A Player close to win (7/8 Districts)
     */	
	public Player getPlayerIsCloseToWin() {
		Player playerIsCloseToWin= board.getListOfPlayer().get(0);
		for(Player player: board.getListOfPlayer()){
			if(playerIsCloseToWin.getCity().getSizeOfCity() > 6)
				playerIsCloseToWin = player;
		}
		return playerIsCloseToWin;
	}
	
	/**
     * @return The player who has most District card
     */	
	 public Player getTargetPlayerHasMostCard() {
		Player playerHasMostCard= board.getListOfPlayer().get(0);
		for(Player player: board.getListOfPlayer()){
			if(playerHasMostCard.getDistrictCards().size() < player.getDistrictCards().size())
				playerHasMostCard = player;
		}
		return playerHasMostCard;
	}
	
	//DistanceBetweenRichardAndAPlayerCloseToWin

	/**
     * @return The player who has most District card
     */	
	private int getDistanceBetweenRichardAndAPlayerCloseToWin() {
		Player playerCloseToWin= getPlayerIsCloseToWin();		
		int indexOfPlayerCloseToWin = getIndexOfPlayer(playerCloseToWin);
		int currentPlayerIndex = getIndexOfPlayer(currentPlayer);
		return indexOfPlayerCloseToWin - currentPlayerIndex;
	}
	
	private int getIndexOfPlayer(Player player) {
		for(int i = 0 ; i < board.getListOfPlayer().size() ; i++) 
			if(board.getListOfPlayer().get(i).equals(currentPlayer)) 
				return i;
		return -1;
	}

	/**
     * @return True if a Player is close to win (7/8 Districts), False in the others cases
     */
	private boolean aPlayerIsCloseToWin() {
		for(Player player: board.getListOfPlayer())
			if(player.getCity().getSizeOfCity() >6) return true;
		return false;
	}


	private boolean mostAdvancedPlayerPlayFirst() {
		Player mostAdvancedPlayer = getMostAdvancedPlayer();
		Player firstPlayerToPlay = getFirstPlayerToPlay();
		if(firstPlayerToPlay.equals(currentPlayer)) return false;
		if(mostAdvancedPlayer.equals(firstPlayerToPlay)) return true;
		return false;
	}

	
	private Player getRichestPlayer() {
		Player richestPlayer= board.getListOfPlayer().get(0);
		for(Player player: board.getListOfPlayer()){
			if(richestPlayer.getGolds() < player.getGolds())
				richestPlayer = player;
		}
		return richestPlayer;
	}
	
	/**
     * @return The first Player who can choose his Character card
     */
	private Player getFirstPlayerToPlay() {
		if(board.getListOfPlayerWhoPlayed().size()==0) return currentPlayer;
		return board.getListOfPlayerWhoPlayed().get(0);
	}
	
	
	/**
	 * @return a player that has 6 or more district, and this player has the biggest city of all the player
	 */
	private Player getPotentialWinner() {
		Player potentialWinner= board.getListOfPlayer().get(0);
		for(Player player: board.getListOfPlayer()){
			if(player.getCity().getSizeOfCity() > 6 && potentialWinner.getCity().getSizeOfCity() < player.getCity().getSizeOfCity())
				potentialWinner = player;
		}
		return potentialWinner;	
	}

	
	/**
     * @return The most advanced Player of the game
     */
	private Player getMostAdvancedPlayer() {
		Player mostAdvancedPlayer= board.getListOfPlayer().get(0);
		for(Player player: board.getListOfPlayer()){
			if(mostAdvancedPlayer.getCity().getSizeOfCity() < player.getCity().getSizeOfCity())
				mostAdvancedPlayer = player;
		}
		return mostAdvancedPlayer;	
	}
	
	
    
}
