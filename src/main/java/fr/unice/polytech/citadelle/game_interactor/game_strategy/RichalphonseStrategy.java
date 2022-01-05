package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import java.util.ArrayList;
import java.util.Collections;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.output.PrintCitadels;


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
    	searchSituation = librairie.filterByListOfCharacterPickable(searchSituation, listOfRichardCharacterPickable);
    	searchSituation = librairie.filterByListOfCharacterNotPickable(searchSituation, getNonPickableCharacter());
    	searchSituation = librairie.filterByPositionOfPotentialWinner(searchSituation, getPositionOfPotentialWinner());
    	searchSituation = librairie.filterByOrderOfPlay(searchSituation, getRichardPlayOrder());
    	searchSituation = librairie.filterByPlayerCloseToWinPlayFirst(searchSituation, calculIsAPlayerCloseWinPlayFirst());
    	searchSituation = librairie.filterByDistanceBetweenRichardAndAPlayerCloseToWin(searchSituation, getDistanceBetweenRichardAndAPlayerCloseToWin());
    	searchSituation = librairie.filterByIHave6Districts(searchSituation, getDoIHave6District());
    	searchSituation = librairie.filterByIHave7Districts(searchSituation, getDoIHave7District());
    	searchSituation = librairie.filterByAPlayerHas7Districts(searchSituation, doAPlayerInTheCurrentGameHas7Districts());
    	searchSituation = librairie.filterByAPlayerCouldPlayArchitect(searchSituation, doesAPlayerInTheCurrentGameCouldPlayArchitect());
    	searchSituation = librairie.filterByRichardCouldPlayArchitect(searchSituation, doRichardCouldPlayArchitect());
    	searchSituation = librairie.filterByExistRichPlayer(searchSituation, doesExistARichPlayer());
    	searchSituation = librairie.filterByIHaveFewCard(searchSituation, doRichardAsHaveFewCard());
    	searchSituation = librairie.filterByIHaveLotOfCard(searchSituation, doIHaveLotOfCard());
    	searchSituation = librairie.filterByAPlayerHasLotOfCard(searchSituation, doAPlayerHasLotOfCard());
    	searchSituation = librairie.filterByAPlayerCloseToWinHasFewCard(searchSituation, doAPlayerCloseToWinHasFewCard());
    	searchSituation = librairie.filterByRoundNumberLess5(searchSituation, isRoundNumberLess5());
    	searchSituation = librairie.filterByNumberOfPlayerLess5(searchSituation, isNumberOfPlayerLess5());
    	searchSituation = librairie.filterByRichardDontPlayFirst(searchSituation, doRichardDontPlayFirst());
    	searchSituation = librairie.filterRichardHasMoreOf6Golds(searchSituation, doRichardHasMoreOf6Golds());
   
    	Collections.sort(searchSituation);
       // PrintCitadels.printAllSituations(searchSituation);

    	Situation bestSituation =  searchSituation.get(0);
    	return bestSituation;
    }
    
    private ArrayList<Character> getNonPickableCharacter() {
    	ArrayList<Character> nonPickable = new ArrayList<>();
    	for(Character character: board.getListOfCharacter()){
    		if(!(board.getDeckCharacter()).getDeckCharacter().contains(character))
    			nonPickable.add(character);
    	}
		return nonPickable;
	}


	/**
     * @return index of playOrder of currentPlayer
     */
    private int getRichardPlayOrder() {
		return getIndexOfPlayer(currentPlayer)+1;
	}

    /**
     * @return index of playOrder of the most advanced player of the galme
     */
	private int getPositionOfPotentialWinner() {
		if(!aPlayerIsCloseToWin()) return 0;
		int indexPotentialWinner = getIndexOfPlayer(getPlayerCloseToWin());
		int indexCurrentPlayer = getIndexOfPlayer(currentPlayer);

		if(indexPotentialWinner - indexCurrentPlayer < 0) return -1;
		if(indexPotentialWinner - indexCurrentPlayer > 0) return 1;
		return 0;
	}


	/**
     * @return True if current player has more than 6 golds
     */
    private boolean doRichardHasMoreOf6Golds() {
		if(currentPlayer.getGolds() > 6) return true;
		return false;
	}


	/**
     * @return True if a currentPlayer don't play first
     */
    private boolean doRichardDontPlayFirst() {
		if(board.getListOfPlayer().get(0).equals(currentPlayer)) return false;
    	return true;
	}


	/**
     * @return True if the number of player is les than 5
     */
    private boolean isNumberOfPlayerLess5() {
		if(board.getListOfPlayer().size() < 5) return true;
		return false;
	}
    
    /**
     * @return True if a current number value is less than 5
     */
    private boolean isRoundNumberLess5() {
		if(board.getRoundNumber() < 5) return true;
		return false;
	}


	/**
     * @return True if a player in the current game has twice more Districts cards than other bots
     */
    private boolean doAPlayerCloseToWinHasFewCard() {
		Player playerCloseToWin = getPlayerCloseToWin();
		if(playerCloseToWin.getDistrictCardsSize() <3 && !(playerCloseToWin.equals(currentPlayer))) return true;
		return false;
	}
    
    /**
     * @return True if a player in the current game has twice more Districts cards than other bots
     */
    private boolean doAPlayerHasLotOfCard() {
		int currentNumberOfCards = currentPlayer.getDistrictCardsSize();
    	for(Player player: board.getListOfPlayer()){
    		if(player.getDistrictCardsSize()*2 > currentNumberOfCards && !(player.equals(currentPlayer))) return false;
    	}
		return true;
	}
    
    /**
     * @return True if current Player has  twice more Districts cards than other bots
     */
    private boolean doIHaveLotOfCard() {
		int currentNumberOfCards = currentPlayer.getDistrictCardsSize();
    	for(Player player: board.getListOfPlayer()){
    		if(player.getDistrictCardsSize()*2 > currentNumberOfCards) return false;
    	}
		return true;
	}

    /**
     * @return True if current Player has 7 Districts
     */
	private boolean doRichardAsHaveFewCard() {
		if(currentPlayer.getDistrictCardsSize() < 3) return true;
		return false;
	}

	/**
     * @return True if a player in the current game has more than 5 golds
     */
	private boolean doesExistARichPlayer() {
		for(Player player: board.getListOfPlayer() ){
			if(player.getGolds() > 5 && !(player.equals(currentPlayer)))
				return true;
		}
		return false;
	}


	/**
     * @return True if current Player has a nice config to take Architect
     */
    private boolean doRichardCouldPlayArchitect() {
    	if(currentPlayer.getGolds() > 3)
				if(currentPlayer.getDistrictCardsSize() > 1)
					if(currentPlayer.getCity().getSizeOfCity() > 5)
						return true;
		return false;
	}
    
    /**
     * @return True if do a player in the current game  has a nice config to take Architect
     */
    private boolean doesAPlayerInTheCurrentGameCouldPlayArchitect() {
    	for(Player player: board.getListOfPlayer()){
			if(player.getGolds() > 3)
				if(player.getDistrictCardsSize() > 1)
					if(player.getCity().getSizeOfCity() > 5 && !(player.equals(currentPlayer)))
						return true;
		}
		return false;
	}


	/**
     * @return True if do a player in the current game has 7 Districts
     */
	private boolean doAPlayerInTheCurrentGameHas7Districts() {
		for(Player player: board.getListOfPlayer()){
			if(player.getCity().getSizeOfCity() == 7 && !(player.equals(currentPlayer)))
				return true;
		}
		return false;
	}
    
    
    
    /**
     * @return True if Richard has 6 districts
     */
	private boolean getDoIHave6District() {
		return currentPlayer.getCity().getSizeOfCity() == 6;
	}
	
	/**
     * @return True if Richard has 7 districts
     */
	private boolean getDoIHave7District() {
		return currentPlayer.getCity().getSizeOfCity() == 7;
	}
    
    
    /**
     * @return True if a Player is close to win (7/8 Districts) and he is the first player to play, False in the others cases
     */
    private boolean calculIsAPlayerCloseWinPlayFirst() {
    	if(!aPlayerIsCloseToWin()) return false;
    	Player playerIsCloseToWin = getPlayerCloseToWin();
		Player firstPlayerToPlay = getFirstPlayerToPlay();

		if(playerIsCloseToWin.equals(firstPlayerToPlay)) return true;
		return false;
    }
    
    /**
     * @return A Player close to win (7/8 Districts)
     */	
	public Player getPlayerCloseToWin() {
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
			if(playerHasMostCard.getDistrictCardsSize() < player.getDistrictCardsSize())
				playerHasMostCard = player;
		}
		return playerHasMostCard;
	}
	

	/**
     * @return The player who has most District card
     */	
	private int getDistanceBetweenRichardAndAPlayerCloseToWin() {
		Player playerCloseToWin= getPlayerCloseToWin();		
		int indexOfPlayerCloseToWin = getIndexOfPlayer(playerCloseToWin);
		int currentPlayerIndex = getIndexOfPlayer(currentPlayer);
		return indexOfPlayerCloseToWin - currentPlayerIndex;
	}
	
	/**
     * @return The index of the player in the list of Player of the board
     */	
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
			if(player.getCity().getSizeOfCity() >6 && !(player.equals(currentPlayer))) return true;
		return false;
	}


	/**
     * @return True if a Player is close to win (7/8 Districts), False in the others cases
     */
	private boolean mostAdvancedPlayerPlayFirst() {
		Player mostAdvancedPlayer = getMostAdvancedPlayer();
		Player firstPlayerToPlay = getFirstPlayerToPlay();
		if(firstPlayerToPlay.equals(currentPlayer)) return false;
		if(mostAdvancedPlayer.equals(firstPlayerToPlay)) return true;
		return false;
	}

	/**
     * @return True if a Player is close to win (7/8 Districts), False in the others cases
     */
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
