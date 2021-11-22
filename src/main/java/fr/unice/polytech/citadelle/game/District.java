package fr.unice.polytech.citadelle.game;

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

    public boolean equals(Object o) {
        if (o instanceof District) {
            return (((District) o).name.equals(name));
        }
        return false;
    }
    @Override
    public String toString(){
        return("District "+name+" ("+value+")");
    }
}
