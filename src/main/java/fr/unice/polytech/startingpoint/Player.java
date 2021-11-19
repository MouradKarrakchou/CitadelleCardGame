package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Player implements Comparable<Player> {
	private final String name;
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

    public void addDistrict(District district) {
        this.districtCards.add(district);
    }

    public int getDistrictCardsSize(){
        return districtCards.size();
    }

    public void chooseCharacterCard(Character card) {
        character = card;
    }

    public boolean play() {
        ArrayList<District> districtWeCanBuild = districtWeCanBuild(districtWeHaveEnoughMoneyToBuild());
        if (!districtWeCanBuild.isEmpty()) buildDistrict(districtWeCanBuild.get(0));
        return (city.isComplete());
    }

    public void buildDistrict(District districtToBuild) {
        city.buildDistrict(districtToBuild);
        districtCards.remove(districtToBuild);
        golds -= districtToBuild.getValue();
        score += districtToBuild.getValue();
    }


    public void updateScore(int number) {
        score += number;
    }

    private boolean isAlreadyBuilt(String nameOfDistrict){
        ArrayList<District> districtIsBuilt;
        districtIsBuilt = city.getBuiltDistrict()
                              .stream()
                              .filter(builtDistrict -> builtDistrict.getName().equals(nameOfDistrict))
                              .collect(Collectors.toCollection(ArrayList::new));
        if (districtIsBuilt.size() != 1) return false;

        return true;
    }

    public boolean hasDistrict(District district) {
        for (District districtCard : districtCards) {
            if (districtCard.equals(district)) return true;
        }
        return false;
    }

    public boolean cityHasDistrict(District district){
        return city.hasDistrict(district);
    }

    public boolean canPlaceADistrictInTheCity(){
        for (District district : districtCards){
            if (!city.hasDistrictValue(district)) return true;
        }
        return false;
    }

    private ArrayList<District> districtWeHaveEnoughMoneyToBuild(){
        return districtCards.stream()
                            .filter(district  -> district.getValue() <= getGolds())
                            .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<District> districtWeCanBuild(ArrayList<District> districtCheapEnough){
        return districtCheapEnough.stream()
                                  .filter(district -> !(isAlreadyBuilt(district.getName())))
                                  .collect(Collectors.toCollection(ArrayList::new));
    }

    //getter

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

    public void addGold() {golds+=2;}

    //setter

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
        if (p.getScore() > this.score) return (-1);
        if (p.getScore() < this.score) return (1);

        else {
            if (p.getCharacterValue() > this.getCharacterValue()) return (-1);
            else return (1);
        }
    }

}
