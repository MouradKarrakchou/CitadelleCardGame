package fr.unice.polytech.citadelle.game.purple_districts;

import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.DistrictSpell;
import fr.unice.polytech.citadelle.game.Player;

public class University  extends District implements DistrictSpell {

    public University(String name, int value, String color, String nameOfFamily) {
        super(name, value, color, nameOfFamily);
    }

    @Override
    public void spell(Player player) {
        //fill here
    }

    @Override
    public void joker(Player player) {
        //nothing to fill here
    }
}
