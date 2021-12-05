package fr.unice.polytech.citadelle.game;

public class BonusDistrict extends District {
    public BonusDistrict(String name, int value, String color, String nameOfFamily) {
        super(name, value, color, nameOfFamily);
    }

    public void bonusDistrict(Player player) {
        player.updateScore(2);
    }

}
