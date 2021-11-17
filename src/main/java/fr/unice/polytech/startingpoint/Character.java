package fr.unice.polytech.startingpoint;

public class Character {
    private final String name;
    private final int value;

    public Character(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
