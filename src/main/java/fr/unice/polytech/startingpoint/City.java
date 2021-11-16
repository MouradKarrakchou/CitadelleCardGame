package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class City {
    ArrayList<District> builtDistrict;
    int cityComplete = 8;
    public City(){
        builtDistrict=new ArrayList<>();
    }
    public void buildDistrict(District district){
        builtDistrict.add(district);
    }
	public boolean isComplete() {
		if(builtDistrict.size() == cityComplete)
			return true;
		return false;
	}
}
