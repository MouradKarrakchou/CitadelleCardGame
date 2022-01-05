package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import java.util.ArrayList;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Character;


public class SituationLibrairie {
	private ArrayList<Situation> librairieContent;
	private Board board;

	//SituationInitializer.generateAllSituations(board);

	 
	public SituationLibrairie(Board board) {
		this.librairieContent = new  ArrayList<Situation>();
		this.board = board;
	}
	
	
	
	public ArrayList<Situation> filterByListOfCharacterPickable(ArrayList<Situation> listOfSituation, ArrayList<Character> listOfRichardCharacterPickable) {
		return listOfSituation.stream()
								.filter(situation ->situation.getListOfCharacterPickable().isEmpty() || listOfRichardCharacterPickable.containsAll(situation.getListOfCharacterPickable().get())										)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByListOfCharacterNotPickable(ArrayList<Situation> listOfSituation, ArrayList<Character> listOfRichardCharacterPickable) {
		return listOfSituation.stream()
								.filter(situation -> situation.getListOfCharacterNotHere().isEmpty() || doNotContain(situation.getListOfCharacterNotHere().get(), listOfRichardCharacterPickable))
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByPositionOfPotentialWinner(ArrayList<Situation> listOfSituation, int positionOfPotentialWinner ) {
		return listOfSituation.stream()
								.filter(situation -> situation.getPositionOfPotentialWinner().isEmpty() || situation.getPositionOfPotentialWinner().get() == positionOfPotentialWinner)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByOrderOfPlay(ArrayList<Situation> listOfSituation, int orderOfPlay){
		return listOfSituation.stream().
								filter(situation -> situation.getPlayOrder().isEmpty() || situation.getPlayOrder().get() == orderOfPlay).
								collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByPlayerCloseToWinPlayFirst(ArrayList<Situation> listOfSituation, boolean currentBestPlayerPlayFirst) {
		return listOfSituation.stream()
								.filter(situation -> situation.getAPlayerCloseToWinPlayFirst().isEmpty() || situation.getAPlayerCloseToWinPlayFirst().get() == currentBestPlayerPlayFirst)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByDistanceBetweenRichardAndAPlayerCloseToWin(ArrayList<Situation> listOfSituation, int currentDdistanceBetweenRichardAndAPlayerCloseToWin) {
		return listOfSituation.stream()
								.filter(situation -> situation.getDistanceBetweenRichardAndAPlayerCloseToWin().isEmpty() || situation.getDistanceBetweenRichardAndAPlayerCloseToWin().get() == currentDdistanceBetweenRichardAndAPlayerCloseToWin)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByIHave6Districts(ArrayList<Situation> listOfSituation, boolean richardHas6Districts) {
		return listOfSituation.stream()
								.filter(situation -> situation.getiHave6Districts().isEmpty() || situation.getiHave6Districts().get() == richardHas6Districts)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByIHave7Districts(ArrayList<Situation> listOfSituation, boolean richardHas7Districts) {
		return listOfSituation.stream()
								.filter(situation -> situation.getiHave7Districts().isEmpty() || situation.getiHave7Districts().get() == richardHas7Districts)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByAPlayerHas7Districts(ArrayList<Situation> listOfSituation, boolean aPlayerInTheCurrentGameHas7Districts) {
		return listOfSituation.stream()
								.filter(situation -> situation.getAPlayerHas7Districts().isEmpty() || situation.getAPlayerHas7Districts().get() == aPlayerInTheCurrentGameHas7Districts)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByAPlayerCouldPlayArchitect(ArrayList<Situation> listOfSituation, boolean aPlayerInTheCurrentGameCouldPlayArchitect) {
		return listOfSituation.stream()
								.filter(situation -> situation.getAPlayerCouldPlayArchitect().isEmpty() || situation.getAPlayerCouldPlayArchitect().get() == aPlayerInTheCurrentGameCouldPlayArchitect)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByRichardCouldPlayArchitect(ArrayList<Situation> listOfSituation, boolean richarCouldPlayArchitect) {
		return listOfSituation.stream()
								.filter(situation -> situation.getRichardCouldPlayArchitect().isEmpty() || situation.getRichardCouldPlayArchitect().get() == richarCouldPlayArchitect)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByExistRichPlayer(ArrayList<Situation> listOfSituation, boolean doesExistARichPlayerInGame) {
		return listOfSituation.stream()
								.filter(situation -> situation.getExistRichPlayer().isEmpty() || situation.getExistRichPlayer().get() == doesExistARichPlayerInGame)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByIHaveFewCard(ArrayList<Situation> listOfSituation, boolean doesRichardHasAFewCard) {
		return listOfSituation.stream()
								.filter(situation -> situation.getIHaveFewCard().isEmpty() || situation.getIHaveFewCard().get() == doesRichardHasAFewCard)
								.collect(Collectors.toCollection(ArrayList::new));
	}

	public ArrayList<Situation> filterByIHaveLotOfCard(ArrayList<Situation> listOfSituation, boolean doesRichardHasALotOfCard) {
		return listOfSituation.stream()
								.filter(situation -> situation.getIHaveLotOfCard().isEmpty() || situation.getIHaveLotOfCard().get() == doesRichardHasALotOfCard)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByAPlayerHasLotOfCard(ArrayList<Situation> listOfSituation, boolean aPlayerInTheCurrentGameHasALotOfCard) {
		return listOfSituation.stream()
								.filter(situation -> situation.getAPlayerHasLotOfCard().isEmpty() || situation.getAPlayerHasLotOfCard().get() == aPlayerInTheCurrentGameHasALotOfCard)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByAPlayerCloseToWinHasFewCard(ArrayList<Situation> listOfSituation, boolean aPlayerInTheGameIsCloseToWinAndHasFewCard) {
		return listOfSituation.stream()
								.filter(situation -> situation.getAPlayerCloseToWinHasFewCard().isEmpty() || situation.getAPlayerCloseToWinHasFewCard().get() == aPlayerInTheGameIsCloseToWinAndHasFewCard)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByRoundNumberLess5(ArrayList<Situation> listOfSituation, boolean currentRoundNumberIsLess5) {
		return listOfSituation.stream()
								.filter(situation -> situation.getRoundNumberLess5().isEmpty() || situation.getRoundNumberLess5().get() == currentRoundNumberIsLess5)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByNumberOfPlayerLess5(ArrayList<Situation> listOfSituation, boolean currentNumberOfTotalPlayerIsLess5) {
		return listOfSituation.stream()
								.filter(situation -> situation.getNumberOfPlayerLess5().isEmpty() || situation.getNumberOfPlayerLess5().get() == currentNumberOfTotalPlayerIsLess5)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByRichardDontPlayFirst(ArrayList<Situation> listOfSituation, boolean richardDontPlayFirst) {
		return listOfSituation.stream()
								.filter(situation -> situation.getRichardDontPlayFirst().isEmpty() || situation.getRichardDontPlayFirst().get() == richardDontPlayFirst)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterRichardHasMoreOf6Golds(ArrayList<Situation> listOfSituation, boolean richardHasMoreOf6Golds) {
		return listOfSituation.stream()
								.filter(situation -> situation.getRichardHasMoreOf6Golds().isEmpty() || situation.getRichardHasMoreOf6Golds().get() == richardHasMoreOf6Golds)
								.collect(Collectors.toCollection(ArrayList::new));
	}
		
	private boolean doNotContain(ArrayList<Character> list1, ArrayList<Character> list2) {
		for(Character character : list1){
			if(list2.contains(character)) return false;
		}
		return true;
	}


	public ArrayList<Situation> getLibrairieContent() {
		if(librairyEmpty()) this.librairieContent = SituationInitializer.generateAllSituations(board);
		return librairieContent;
	}
	
	private boolean librairyEmpty() {
		if(librairieContent.size() == 0) return true;
		return false;
	}



	
	
	
	
}
