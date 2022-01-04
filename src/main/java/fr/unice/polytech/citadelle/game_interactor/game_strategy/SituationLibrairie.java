package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import java.util.ArrayList;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game_character.Character;


public class SituationLibrairie {
	private ArrayList<Situation> librairieContent;

	
	 
	public SituationLibrairie(Board board) {
		this.librairieContent = SituationInitializer.generateAllSituations(board);
	}
	
	public ArrayList<Situation> filterByOrderOfPlay(ArrayList<Situation> listOfSituation, int orderOfPlay){
		return listOfSituation.stream().
								filter(situation -> situation.getRang().isEmpty() || situation.getRang().get() == orderOfPlay).
								collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByListOfCharacterPickable(ArrayList<Situation> listOfSituation, ArrayList<Character> listOfRichardCharacterPickable) {
		return listOfSituation.stream()
								.filter(situation ->situation.getListOfCharacterPickable().isEmpty() || listOfRichardCharacterPickable.containsAll(situation.getListOfCharacterPickable().get())										)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByListOfCharacterNotPickable(ArrayList<Situation> listOfSituation, ArrayList<Character> listOfRichardCharacterNotPickable) {
		return listOfSituation.stream()
								.filter(situation -> situation.getListOfCharacterPickable().isEmpty() || doNotContain(listOfRichardCharacterNotPickable, listOfRichardCharacterNotPickable))
								.collect(Collectors.toCollection(ArrayList::new));
	}
		
	private boolean doNotContain(ArrayList<Character> list1, ArrayList<Character> list2) {
		for(Character character : list1){
			if(list2.contains(character)) return false;
		}
		return true;
	}


	public ArrayList<Situation> getLibrairieContent() {
		return librairieContent;
	}
	
	
	
}
