package fr.unice.polytech.citadelle.game.purple_districts;

import fr.unice.polytech.citadelle.game.ColorDistrict;

/**
 * HauntedCity is a purple District which has a spell
 */
public class HauntedCity extends ColorDistrict {
    private int roundBuilt;

    public HauntedCity(String name, int value, String color, String nameOfFamily, Integer roundBuilt) {
        super(name, value, color, nameOfFamily);
        this.roundBuilt = roundBuilt;
    }

    public int getRoundBuilt() {
        return roundBuilt;
    }

    public void setRoundBuilt(Integer round) { roundBuilt = round; }

}
