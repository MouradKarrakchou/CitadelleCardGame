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

    
    public Situation getBestSituation(int orderOfPlay, ArrayList<Character> listOfRichardCharacterPickable){
    	ArrayList<Situation> searchSituation = librairie.filterByOrderOfPlay(librairie.getLibrairieContent(), orderOfPlay);
    	searchSituation = librairie.filterByOrderOfPlay(searchSituation, orderOfPlay);
    	searchSituation = librairie.filterByListOfCharacterPickable(searchSituation, listOfRichardCharacterPickable);
    	searchSituation = librairie.filterByPlayerCloseWinPlayFirst(searchSituation, calculPlayerCloseWinPlayFirst());
    	
    	Collections.sort(searchSituation);
    	Situation bestSituation =  searchSituation.get(0);
    	return bestSituation;
    }
    
    
    private boolean calculPlayerCloseWinPlayFirst() {
    	if(!aPlayerIsCloseToWin()) return false;
    	Player mostAdvancedPlayer = getPlayerIsCloseToWin();
		Player firstPlayerToPlay = getFirstPlayerToPlay();

		if(mostAdvancedPlayer.equals(firstPlayerToPlay)) return true;
		return false;
    }
    
    	
	private Player getPlayerIsCloseToWin() {
		Player playerIsCloseToWin= board.getListOfPlayer().get(0);
		for(Player player: board.getListOfPlayer()){
			if(playerIsCloseToWin.getCity().getSizeOfCity() > 6)
				playerIsCloseToWin = player;
		}
		return playerIsCloseToWin;
	}


	private boolean aPlayerIsCloseToWin() {
		for(Player player: board.getListOfPlayer())
			if(player.getCity().getSizeOfCity() >6) return true;
		return false;
	}


	private boolean mostAdvancedPlayerPlayFirst() {
		Player mostAdvancedPlayer = getMostAdvancedPlayer();
		Player firstPlayerToPlay = getFirstPlayerToPlay();

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
	
	
	private Player getFirstPlayerToPlay() {
		return board.getListOfPlayerWhoPlayed().get(0);
	}
	
	
	
	
	private Player getMostAdvancedPlayer() {
		Player mostAdvancedPlayer= board.getListOfPlayer().get(0);
		for(Player player: board.getListOfPlayer()){
			if(mostAdvancedPlayer.getCity().getSizeOfCity() < player.getCity().getSizeOfCity())
				mostAdvancedPlayer = player;
		}
		return mostAdvancedPlayer;	
	}
	
	
    
}
