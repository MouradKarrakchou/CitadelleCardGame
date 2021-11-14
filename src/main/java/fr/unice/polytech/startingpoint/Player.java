package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Comparator;


public class Player implements Comparable<Player> {
	private String name;
	private Character character;
	private ArrayList<District> districtCards;
	private City city;
	private int score;
	private int rank;

	public Player(String name) {
		city = new City();
		this.name = name;
		this.score = 0;
		districtCards = new ArrayList<>();
	}

	
	public void buildDistrict(int numberDistrict) {
		city.buildDistrict(districtCards.get(numberDistrict));
	}

	public ArrayList<District> getDistrictCards() {
		return districtCards;
	}
	
	public void addDistrict(District district) {
		this.districtCards.add(district);
	}
	
	public void updateScore(int number) {
		score += number;
	}
	


	public Character getCharacter() {
		return character;
	}
	
	public int getCharacterValue() {
		return character.getValue();
	}

	public String getName() {
		return name;
	}


	public int getScore() {
		return score;
	}


	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public void setRole(Character role) {
		this.character = role;
	}

	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String toString() {
		return "name="+this.name + " score="+this.score+ " role="+this.getCharacterValue();
	}

	public int compareTo(Player p){
	    return Comparator.comparing(Player::getScore).reversed()
	              .thenComparing(Player::getCharacterValue)
	              .compare(this, p);
	}

	

}
