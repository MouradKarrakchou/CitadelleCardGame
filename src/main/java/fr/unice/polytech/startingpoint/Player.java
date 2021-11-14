package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class Player {
    private String name;
    private Character character;
    private ArrayList<District> districtCards;
    private City city;
    private int score;
    private int rank;

    public Player(String name){
        city=new City();
        this.name=name;
        this.score=0;
        districtCards=new ArrayList<>();
    }
    public void setRole(Character role) {
        this.character = role;
    }

    public ArrayList<District> getDistrictCards() {return districtCards;}
    public void addDistrict(District district) {this.districtCards.add(district);}
    public Character getCharacter() {
        return character;
    }
    public String getName() {
        return name;
    }
    public void updateValue(int number){score+=number;}
    public void buildDistrict(int numberDistrict){
        city.buildDistrict(districtCards.get(numberDistrict));
    }
    public int getScore() {return score;}
    public void setRank(int rank){this.rank=rank;}
    public int getRank() {return rank;}
}
