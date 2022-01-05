package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import java.util.ArrayList;
import java.util.Optional;

import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Character;


public class Situation implements Comparable<Situation>{
	
	//--------------------Setter
	private Optional<Integer> playOrder = Optional.empty();
	//a player different of us
	private Optional<Boolean> aPlayerCloseToWinPlayFirst = Optional.empty();
	//if their is not a play close to win, -1, closeToWin = 7/8 District
	private Optional<Integer> distanceBetweenRichardAndAPlayerCloseToWin = Optional.empty();
	private Optional<Boolean> iHave6Districts = Optional.empty();
	private Optional<Boolean> iHave7Districts = Optional.empty();
	private Optional<Boolean> aPlayerCouldPlayArchitect = Optional.empty();
	private Optional<Boolean> RichardCouldPlayArchitect = Optional.empty();
	//rich = 6 golds
	private Optional<Boolean> existRichPlayer = Optional.empty();
	// few Card <3
	private Optional<Boolean> iHaveFewCard = Optional.empty();
	// lot of card = double of all ( 2* plus que les autres)
	private Optional<Boolean> iHaveLotOfCard = Optional.empty();
	private Optional<Boolean> aPlayerHasLotOfCard = Optional.empty();
	//if no player close to win = FALSE
	private Optional<Boolean> aPlayerCloseToWinHasFewCard = Optional.empty();
	private Optional<Boolean> roundNumberLess5 = Optional.empty();
	private Optional<Boolean> numberOfPlayerLess5 = Optional.empty();
	private Optional<Boolean> RichardDontPlayFirst = Optional.empty();
	private Optional<Boolean> RichardHasMoreOf6Golds = Optional.empty();


	
	//--------------------CONSTRUCTEUR
	private String description;
	private Optional<ArrayList<Character>> listOfCharacterPickable;
	private Optional<ArrayList<Character>> listOfCharacterNotHere;
	//-1 if he play before 1 if play after me and  0 if nobody is close to end the game
	private Optional<Integer> positionOfPotentialWinner;
	private Optional<Character> characterToChoose;
	private Optional<Character> targetCharacter;
	private Optional<Player> targetPlayer;
	private int priority;



	

	public Situation(String description, Optional<ArrayList<Character>> listOfCharacterPickable, Optional<ArrayList<Character>> listOfCharacterNotHere, Optional<Integer> positionOfPotentialWinner, Optional<Character> characterToChoose, Optional<Character> targetCharacter, Optional<Player> targetPlayer ,int priority) {
		this.description = description;
		this.priority = priority;
		this.listOfCharacterPickable = listOfCharacterPickable;
		this.listOfCharacterNotHere = listOfCharacterNotHere;
		this.positionOfPotentialWinner =positionOfPotentialWinner;
		this.characterToChoose = characterToChoose;
		this.targetCharacter = targetCharacter;
		this.targetPlayer = targetPlayer;
		this.priority = priority;
	}


	public Optional<Integer> getplayOrder() {
		return playOrder;
	}

	public Optional<ArrayList<Character>> getListOfCharacterPickable() {
		return listOfCharacterPickable;
	}


	public Optional<Character> getCharacterToChoose() {
		return characterToChoose;
	}


	public Optional<Character> getTargetCharacter() {
		return targetCharacter;
	}


	public Optional<Player> getTargetPlayer() {
		return targetPlayer;
	}
	
	public Optional<ArrayList<Character>> getListOfCharacterNotHere() {
		return listOfCharacterNotHere;
	}
	
	
	
	

	public Optional<Integer> getPlayOrder() {
		return playOrder;
	}


	public void setPlayOrder(int playOrder) {
		this.playOrder = Optional.of(playOrder);
	}


	public Optional<Boolean> getaPlayerCloseToWinPlayFirst() {
		return aPlayerCloseToWinPlayFirst;
	}


	public void setaPlayerCloseToWinPlayFirst(boolean aPlayerCloseToWinPlayFirst) {
		this.aPlayerCloseToWinPlayFirst = Optional.of(aPlayerCloseToWinPlayFirst);
	}


	public Optional<Boolean> getiHave6Districts() {
		return iHave6Districts;
	}


	public void setiHave6Districts(boolean iHave6Districts) {
		this.iHave6Districts = Optional.of(iHave6Districts);
	}


	public Optional<Boolean> getiHave7Districts() {
		return iHave7Districts;
	}


	public void setiHave7Districts(boolean iHave7Districts) {
		this.iHave7Districts = Optional.of(iHave7Districts);
	}


	public Optional<Boolean> getaPlayerCouldPlayArchitect() {
		return aPlayerCouldPlayArchitect;
	}


	public void setaPlayerCouldPlayArchitect(boolean aPlayerCouldPlayArchitect) {
		this.aPlayerCouldPlayArchitect = Optional.of(aPlayerCouldPlayArchitect);
	}


	public Optional<Boolean> getRichardCouldPlayArchitect() {
		return RichardCouldPlayArchitect;
	}


	public void setRichardCouldPlayArchitect(boolean richardCouldPlayArchitect) {
		RichardCouldPlayArchitect = Optional.of(richardCouldPlayArchitect);
	}


	public Optional<Boolean> getExistRichPlayer() {
		return existRichPlayer;
	}


	public void setExistRichPlayer(boolean existRichPlayer) {
		this.existRichPlayer = Optional.of(existRichPlayer);
	}


	public Optional<Boolean> getiHaveFewCard() {
		return iHaveFewCard;
	}


	public void setiHaveFewCard(boolean iHaveFewCard) {
		this.iHaveFewCard = Optional.of(iHaveFewCard);
	}


	public Optional<Boolean> getiHaveLotOfCard() {
		return iHaveLotOfCard;
	}


	public void setiHaveLotOfCard(boolean iHaveLotOfCard) {
		this.iHaveLotOfCard = Optional.of(iHaveLotOfCard);
	}


	public Optional<Boolean> getaPlayerHasLotOfCard() {
		return aPlayerHasLotOfCard;
	}


	public void setaPlayerHasLotOfCard(boolean aPlayerHasLotOfCard) {
		this.aPlayerHasLotOfCard = Optional.of(aPlayerHasLotOfCard);
	}


	public Optional<Boolean> getaPlayerCloseToWinHasFewCard() {
		return aPlayerCloseToWinHasFewCard;
	}


	public void setaPlayerCloseToWinHasFewCard(boolean aPlayerCloseToWinHasFewCard) {
		this.aPlayerCloseToWinHasFewCard = Optional.of(aPlayerCloseToWinHasFewCard);
	}


	public Optional<Boolean> getRoundNumberLess5() {
		return roundNumberLess5;
	}


	public void setRoundNumberLess5(boolean roundNumberLess5) {
		this.roundNumberLess5 = Optional.of(roundNumberLess5);
	}


	public Optional<Boolean> getNumberOfPlayerLess5() {
		return numberOfPlayerLess5;
	}


	public void setNumberOfPlayerLess5(boolean numberOfPlayerLess5) {
		this.numberOfPlayerLess5 = Optional.of(numberOfPlayerLess5);
	}


	public Optional<Boolean> getRichardDontPlayFirst() {
		return RichardDontPlayFirst;
	}


	public void setRichardDontPlayFirst(boolean richardDontPlayFirst) {
		RichardDontPlayFirst = Optional.of(richardDontPlayFirst);
	}


	public Optional<Boolean> getRichardHasMoreOf6Golds() {
		return RichardHasMoreOf6Golds;
	}


	public void setRichardHasMoreOf6Golds(boolean richardHasMoreOf6Golds) {
		RichardHasMoreOf6Golds = Optional.of(richardHasMoreOf6Golds);
	}


	public Optional<Integer> getPositionOfPotentialWinner() {
		return positionOfPotentialWinner;
	}


	public void setPositionOfPotentialWinner(int positionOfPotentialWinner) {
		this.positionOfPotentialWinner = Optional.of(positionOfPotentialWinner);
	}


	public Optional<Integer> getDistanceBetweenRichardAndAPlayerCloseToWin() {
		return distanceBetweenRichardAndAPlayerCloseToWin;
	}


	public String getDescription() {
		return description;
	}


	public int getPriority() {
		return priority;
	}


	@Override
	public int compareTo(Situation other) {
        return other.priority-this.priority;

	}
	
	
	
}
