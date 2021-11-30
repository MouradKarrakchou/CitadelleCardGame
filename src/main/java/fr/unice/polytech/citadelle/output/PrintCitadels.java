package fr.unice.polytech.citadelle.output;

import java.util.ArrayList;

import com.diogonunes.jcolor.Attribute;
import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.Character;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class PrintCitadels {
    public PrintCitadels() {
    }

    public void chooseRole(Player player, Character role) {
        System.out.println("    - The robot " + player.getName() + " choose the character " + role.getName() + " " + role.getValue());
    }

    public void chooseDistrict(Player player, District district) {
        System.out.println("The robot " + player.getName() + " choose the district " + district.getName() + " " + district.getValue());
    }

    public void printRanking(ArrayList<Player> listOfPlayer) {
        System.out.println("- Game Results -");
        System.out.println("================");
        listOfPlayer.forEach(player ->
                System.out.println("["
                        + player.getRank() + "] "
                        + player.getName() + " with a score of "
                        + player.getScore() + " (" + player.getGolds()
                        + " golds)"));
    }

    public void dropALine() {
        System.out.println("");
    }

    public void printFirstPlayerToComplete(Player firstPlayerToComplete) {
        String output = "*** The player " + firstPlayerToComplete.getName() + " is the first player to complete his city ! ***";
        System.out.println(colorize(output, YELLOW_BACK(), BLACK_TEXT()));
    }

    public void printPlayerToCompleteCity(Player player) {
        String output = player.getName() + " has completed his city !";
        System.out.println(colorize(output, YELLOW_BACK(), BLACK_TEXT()));
    }

    public void printLayer() {
        System.out.println("=============================================================================================================================");
    }
    public void printKingSpell(Player player){
        System.out.println("The robot "+player.getName()+" has the king, he will play first next round.");
    }

    /*
    public void winByCharacter(Player winner){
        Character winnerCharacter=winner.getCharacter();
        System.out.println("The robot " +winner.getName()+" won because his role "+winnerCharacter.getName()+" had a value of "+winnerCharacter.getValue()+" which is the highest value.");
    }*/

    public void printTakeGold(Player player){
        String coloredOutput = colorize("    [+] ", GREEN_TEXT());
        System.out.println(coloredOutput + player.getName() + " takes two golds.");
    }

    public void printTakeDistrictCard(Player player){
        String coloredOutput = colorize("    [+] ", GREEN_TEXT());
        System.out.println(coloredOutput + player.getName() + " takes a district card.");
    }

    public void printBuildDistrict(Player player, District district) {
        String coloredOutput = colorize("    [+] ", GREEN_TEXT());
        System.out.print(coloredOutput + player.getName() + " built ");
        coloredOutput = colorize(district.toString(), getDistrictColor(district));
        System.out.println(coloredOutput);
    }

    public void printBoard(Board game){
        System.out.println("[?] City of all the Players:");
        game.getListOfPlayer().forEach(player -> {
            System.out.print("    City of "+player.getName()+": ");
            printDistrictWithColor(player);
        });
        System.out.println("");
    }

    public void printBoardOfPlayer(Player player){
        System.out.println("[?] City of " + player.getName() + ": " + player.getCity());
        System.out.println();
    }

    public void printNumberRound(int roundNumber) {
        String output = colorize("Round number " + roundNumber + ".", BRIGHT_BLUE_TEXT());
        System.out.println(output);
    }

    public void botIsDead(Player player) {
        System.out.println(player.getName()+" was the "+player.getCharacter()+". He has been killed he will not play this turn");
    }

    public void killCharacter(Character characterToDie) {
        String output = "[!] The Assassin chooses to kill " + characterToDie;
        System.out.println(colorize(output, RED_TEXT()));
    }

    public void printPhase(String phase, Player player) {
        String output = "*** " + player.getName() + " is in " + phase + " mode ***";
        System.out.println(colorize(output, MAGENTA_BACK(), BLACK_TEXT()));
    }

    public void stealCharacter(Character characterToSteal, int golds) {
        String output = "[!] The Thief chooses to steal from " + characterToSteal + " (+" + golds + " golds).";
        System.out.println(colorize(output, BLUE_TEXT()));
    }

    public void failedToStealCharacter(Character characterToSteal) {
        String output = "[?] The Thief chooses to steal from "+characterToSteal+" but no one has chosen this character.";
        System.out.println(colorize(output, BLUE_TEXT()));
    }

    public void printKingEarnedMoney(int collectGold) {
        System.out.println("The king has "+collectGold+" noble district.(+"+collectGold+" golds)." );
    }

    public void printMerchantEarnedMoney(int collectGold) {
        System.out.println("The merchant has "+collectGold+" trade and handicrafts district.(+"+collectGold+" golds)." );
    }

    public Attribute getDistrictColor(District district){
        return switch (district.getColor()) {
            case "blue" -> BLUE_TEXT();
            case "red" -> RED_TEXT();
            case "yellow" -> YELLOW_TEXT();
            case "green" -> GREEN_TEXT();
            case "purple" -> BRIGHT_MAGENTA_TEXT();
            default -> WHITE_TEXT();
        };
    }

    public void printDistrictWithColor(Player player){
        City playerCity = player.getCity();
        for (District district : playerCity.getBuiltDistrict()){
            System.out.print("| ");
            System.out.print(colorize(district.toString(), getDistrictColor(district)));
            System.out.print(" | ");
        }
        System.out.println();
    }
}
