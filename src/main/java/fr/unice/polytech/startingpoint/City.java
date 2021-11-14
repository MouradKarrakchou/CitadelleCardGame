package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class City {
    ArrayList<District> builtDistrict;
    public City(){
        builtDistrict=new ArrayList<>();
    }
    public void buildDistrict(District district){
        builtDistrict.add(district);
    }
}
