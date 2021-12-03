package fr.unice.polytech.citadelle.game.purple_districts;

import fr.unice.polytech.citadelle.game.DistrictSpell;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.PurpleDistrict;

public class DragonGate extends PurpleDistrict implements DistrictSpell {

    @Override
    public void spell(Player player) {
        player.updateScore(2);
    }
}

