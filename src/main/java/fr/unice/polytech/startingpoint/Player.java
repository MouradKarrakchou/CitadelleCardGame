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
	private int golds;

	public Player(String name) {
		this.city = new City();
		this.districtCards = new ArrayList<>();
		this.name = name;
		this.score = 0;
		this.golds = 2;
	}
	
	public void chooseDistictCard(District card) {
		districtCards.add(card);
	}
	
	public void chooseCharacterCard(Character card) {
		character = card;
	}
	
	
	public boolean play() {
		return buildDistrict(0);		
	}

	public boolean buildDistrict(int numberDistrict) {
		city.buildDistrict(districtCards.get(numberDistrict));
		golds -= districtCards.get(numberDistrict).value;
		if(city.isComplete())
			return true ; 
		else 
			return false;
	}
	

	public ArrayList<District> getDistrictCards() {
		return districtCards;
	}
	
	public void addDistrict(District district) {
		this.districtCards.add(district);
	}
	
	public int getGolds() {
		return golds;
	}

	public void decreaseGoldsBy(int amount) {
		this.golds -= amount;
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

	@Override
	public int compareTo(Player p){
		if (p.getScore()>this.score) return(-1);
		if (p.getScore()<this.score) return(1);

		else
		{if(p.getCharacterValue()>this.getCharacterValue()) return(-1);
		else return(1);}
	}




}
