package fr.unice.polytech.startingpoint;

public class District {
    private final String name;
    private final int value;

    public District(String name, int value) {
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
