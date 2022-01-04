package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import java.util.ArrayList;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.game.District;

public class SituationLibrairie {
	private ArrayList<Situation> librairieContent;
	 
	public SituationLibrairie(ArrayList<Situation> librairieContent) {
		this.librairieContent = librairieContent;
	}
	
	public ArrayList<Situation> filterByOrderOfPlay(ArrayList<Situation> listOfSituation, int orderOfPlay){
		return listOfSituation.stream().
								filter(situation -> situation.getRang() == orderOfPlay).
								collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByListOfCharacterPickable(ArrayList<Situation> listOfSituation, ArrayList<Character> listOfRichardCharacterPickable) {
		return listOfSituation.stream()
								.filter(situation -> listOfRichardCharacterPickable.containsAll(situation.getListOfCharacterPickable())										)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Situation> filterByListOfCharacterNotPickable(ArrayList<Situation> listOfSituation, ArrayList<Character> listOfRichardCharacterPickable) {
		return listOfSituation.stream()
								.filter(situation -> doNotContainAny(situation.getListOfCharacterPickable(), listOfRichardCharacterPickable)									)
								.collect(Collectors.toCollection(ArrayList::new));
	}
	

	private boolean doNotContainAny(ArrayList<Character> list1, ArrayList<Character> list2) {
		for(Character character : list1){
			if(list2.contains(character)) return false;
		}
		return true;
	}
}
