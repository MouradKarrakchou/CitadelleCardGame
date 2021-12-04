package fr.unice.polytech.citadelle.game.purple_districts;

import fr.unice.polytech.citadelle.game.DistrictSpell;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.District;

public class DragonGate extends District implements DistrictSpell {

    public DragonGate(String name, int value, String color, String nameOfFamily) {
        super(name, value, color, nameOfFamily);
    }

    @Override
    public void spell(Player player) {
        player.updateScore(2);
    }

    @Override
    public void joker(Player player) {
        //nothing to fill here
    }

}

