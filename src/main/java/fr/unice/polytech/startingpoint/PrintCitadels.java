package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Optional;

public class PrintCitadels {
    public PrintCitadels() {
    }

    public void chooseRole(Player player, Character role) {
        System.out.println("The robot " + player.getName() + " choose the character " + role.getName() + " " + role.getValue());
    }

    public void chooseDistrict(Player player, District district) {
        System.out.println("The robot " + player.getName() + " choose the district " + district.getName() + " " + district.getValue());
    }

    public void printRanking(ArrayList<Player> listOfPlayer) {
        listOfPlayer.forEach(player -> System.out.println("[" + player.getRank() + "] " + player.getName() + " with a score of " + player.getScore() + " (" + player.getGolds() + " golds)"));
    }

    public void dropALine() {
        System.out.println("");
    }

    public void printFirstPlayerToComplete(Player firstPlayerToComplete) {
        System.out.println("The player " + firstPlayerToComplete.getName() + " is the first player to complete his city !");
    }

    public void printPlayerToCompleteCity(Player player) {
        System.out.println(player.getName() + " complete his city !");
    }

    public void printLayer() {
        System.out.println("======================");
    }


    /*
    public void winByCharacter(Player winner){
        Character winnerCharacter=winner.getCharacter();
        System.out.println("The robot " +winner.getName()+" won because his role "+winnerCharacter.getName()+" had a value of "+winnerCharacter.getValue()+" which is the highest value.");
    }*/

    public void printTakeGold(Player player){
        System.out.println(player.getName() + " takes two golds.");
    }

    public void printTakeDistrictCard(Player player){
        System.out.println(player.getName() + " takes a district card.");
    }
}
