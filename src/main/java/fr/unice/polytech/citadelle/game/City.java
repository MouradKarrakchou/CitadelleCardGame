package fr.unice.polytech.citadelle.game;

import java.util.ArrayList;

/**
 *A city is composed of all the districts that the player has chosen to build.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class City {
    public ArrayList<District> builtDistrict;
    private final int cityComplete = 8;

    public City() {
        builtDistrict = new ArrayList<>();
    }
    
    public int getSizeOfCity() {
    	return builtDistrict.size();
    }

    public void buildDistrict(District district) {
        builtDistrict.add(district);
    }

    public boolean isComplete() {
        return (builtDistrict.size() >= cityComplete);
    }

    //getter

    ArrayList<District> getBuiltDistrict() {
        return builtDistrict;
    }

    @Override
    public String toString(){
        if (builtDistrict.size()==0) return("is empty.");
        return(builtDistrict.stream().map(district->district.getName()+"("+district.getValue()+")")
                .reduce("|",(string, district)-> string+" "+district+" | "));
    }
}
