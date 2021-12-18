package fr.unice.polytech.citadelle.game;

import java.util.ArrayList;

import fr.unice.polytech.citadelle.game_character.Character;

/**
 * A Player represent a person in the citadel game, with his name, city and gold.
 *
 * @author BONNET Kilian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Player implements Comparable<Player> {
	private final String name;
	private Character character;
	private final ArrayList<District> districtCards;
	private final City city;
	private int score;
	private int rank;
	public int golds;

	public Player(String name) {
		this.city = new City();
		this.districtCards = new ArrayList<>();
		this.name = name;
		this.score = 0;
		this.golds = 2;
	}

	public void addDistrict(District district) {
		this.districtCards.add(district);
	}

	public void chooseCharacterCard(Character card) {
		character = card;
	}

	public void buildDistrict(District districtToBuild) {
		city.buildDistrict(districtToBuild);
		districtCards.remove(districtToBuild);
		golds -= districtToBuild.getValue();
	}

	public void updateScore(int number) {
		score += number;
	}

	public boolean hasDistrict(District district) {
		for (District districtCard : districtCards) {
			if (districtCard.equals(district))
				return true;
		}
		return false;
	}

	//getter
	public int getDistrictCardsSize() {
		return districtCards.size();
	}

	public int getGolds() {
		return golds;
	}

	public ArrayList<District> getDistrictCards() {
		return districtCards;
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

	public void addGold() {
		golds += 2;
	}

	public void addOneGold() { golds += 1; }

	// setter
	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setRole(Character role) {
		this.character = role;
	}

	public String toString() {
		return "name=" + this.name + " score=" + this.score + " role=" + this.getCharacterValue();
	}

	@Override
	public int compareTo(Player p) {
		if (p.getScore() > this.score)
			return (-1);
		if (p.getScore() < this.score)
			return (1);

		else {
			if (p.getCharacterValue() > this.getCharacterValue())
				return (-1);
			else
				return (1);
		}
	}

	public void setGolds(int golds) {
		this.golds = golds;
	}

	public City getCity() {
		return city;
	}

	/**
	 * When called the player will give all its money to the other player
	 * @param player The player who steal this player.
	 * @return The amount of gold this player loose.
	 */
    public int stealGoldOfThePlayer(Player player) {
		int goldStolen = this.golds;
		player.golds += goldStolen;
		this.golds = 0;
		return(goldStolen);
	}
}
