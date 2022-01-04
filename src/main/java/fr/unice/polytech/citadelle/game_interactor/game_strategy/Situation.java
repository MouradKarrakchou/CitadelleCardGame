package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import java.util.ArrayList;
import java.util.Optional;

import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Character;


public class Situation implements Comparable<Situation>{
	private Integer rang;
	private Integer nbrDistrictBuild;
	private ArrayList<Character> listOfCharacterPickable;
	private ArrayList<Character> listOfCharacterNotHere;

	private Character characterToChoose;
	private Character targetCharacter;
	private Player targetPlayer;
	private Integer priority;
	

	public Situation(Integer rang, Integer nbrDistrictBuild, ArrayList<Character> listOfCharacterPickable, Character characterToChoose, Integer priority) {
		this.rang = rang;
		this.nbrDistrictBuild = nbrDistrictBuild;
		this.listOfCharacterPickable = listOfCharacterPickable;
		this.characterToChoose = characterToChoose;
		this.priority = priority;

	}


	public Optional<Integer> getRang() {
		if (rang==null)
			return Optional.empty();
		return Optional.of(rang);
	}


	public Optional<Integer> getNbrDistrictBuild() {
		if (nbrDistrictBuild==null)
			return Optional.empty();
		return Optional.of(nbrDistrictBuild);
	}


	public Optional<ArrayList<Character>> getListOfCharacterPickable() {
		if (listOfCharacterPickable==null)
			return Optional.empty();
		return Optional.of(listOfCharacterPickable);
	}


	public Optional<Character> getCharacterToChoose() {
		if (characterToChoose==null) return(Optional.empty());
		return Optional.of(characterToChoose);
	}


	public Optional<Character> getTargetCharacter() {
		if (targetCharacter==null) return(Optional.empty());
		return Optional.of(targetCharacter);
	}


	public Optional<Player> getTargetPlayer() {
		if (targetPlayer==null) return(Optional.empty());
		return Optional.of(targetPlayer);
	}


	@Override
	public int compareTo(Situation other) {
        return this.priority - other.priority;

	}
	
	
	
}
