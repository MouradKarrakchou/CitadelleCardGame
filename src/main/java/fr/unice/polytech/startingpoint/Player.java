package fr.unice.polytech.startingpoint;

public class Player {
    private String name;
    private Character character;
    public Player(String name){
        this.name=name;
    }
    public void setRole(Character role) {
        this.character = role;
    }
    public Character getCharacter() {
        return character;
    }
    public String getName() {
        return name;
    }
}
