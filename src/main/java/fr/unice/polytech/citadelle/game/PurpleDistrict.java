package fr.unice.polytech.citadelle.game;

public abstract class PurpleDistrict extends District {

    public PurpleDistrict(String name, int value, String color, String nameOfFamily) {
        super(name, value, color, nameOfFamily);
    }

    public abstract void spell(Player player);
}
