package fr.unice.polytech.citadelle.output;

import java.util.ArrayList;

import com.diogonunes.jcolor.Attribute;

import fr.unice.polytech.citadelle.basic_actions.BasicActions;
import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.Character;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class PrintCitadels {
	public PrintCitadels() {
	}

	public void chooseRole(Player player, Character role) {
		System.out.println("    - The robot " + player.getName() + " choose the character " + role.getName() + " "
				+ role.getValue());
	}

	public void chooseDistrict(Player player, District district) {
		System.out.println("The robot " + player.getName() + " choose the district " + district.getName() + " "
				+ district.getValue());
	}

	public void printRanking(ArrayList<Player> listOfPlayer) {
		System.out.println("- Game Results -");
		System.out.println("================");
		listOfPlayer.forEach(player -> System.out.println("[" + player.getRank() + "] " + player.getName()
				+ " with a score of " + player.getScore() + " (" + player.getGolds() + " golds)"));
	}

	public void dropALine() {
		System.out.println("");
	}

	public void printFirstPlayerToComplete(Player firstPlayerToComplete) {
		String output = "*** The player " + firstPlayerToComplete.getName()
				+ " is the first player to complete his city ! ***";
		System.out.println(colorize(output, YELLOW_BACK(), BLACK_TEXT()));
	}

	public void printPlayerToCompleteCity(Player player) {
		String output = player.getName() + " has completed his city !";
		System.out.println(colorize(output, YELLOW_BACK(), BLACK_TEXT()));
	}

	public void printLayer() {
		System.out.println(
				"=============================================================================================================================");
	}

	public void printKingSpell(Player player) {
		String output = "[!] The robot " + player.getName() + " has the king, he will play first next round.";
		System.out.println(colorize(output, YELLOW_TEXT()));
	}

	/*
	 * public void winByCharacter(Player winner){ Character
	 * winnerCharacter=winner.getCharacter(); System.out.println("The robot "
	 * +winner.getName()+" won because his role "+winnerCharacter.getName()
	 * +" had a value of "+winnerCharacter.getValue()+" which is the highest value."
	 * ); }
	 */

	public void printTakeGold(Player player) {
		String coloredOutput = colorize("    [+] ", GREEN_TEXT());
		System.out.println(coloredOutput + player.getName() + " takes two golds.");
	}

	public void printTakeDistrictCard(Player player, District district) {
		String coloredOutput2 = colorize(district.toString(), getDistrictColor(district));
		System.out.println(player.getName() + " takes a district card: " + coloredOutput2);
	}

	public void printBuildDistrict(Player player, District district) {
		String coloredOutput = colorize("    [⛏] ", GREEN_TEXT());
		System.out.print(coloredOutput + player.getName() + " builds ");
		coloredOutput = colorize(district.toString(), getDistrictColor(district));
		System.out.println(coloredOutput);
	}

	public void printBoard(Board game) {
		System.out.println();
		System.out.println("[?] City of all the Players:");
		game.getListOfPlayer().forEach(player -> {
			System.out.print("    City of " + player.getName() + ": ");
			printDistrictWithColor(player);
		});
		System.out.println("");
	}

	public void printBoardOfPlayer(Player player) {
		System.out.println("[?] City of " + player.getName() + ": " + player.getCity());
		System.out.println();
	}

	public void printNumberRound(int roundNumber) {
		String output = colorize("Round number " + roundNumber + ".", BRIGHT_BLUE_TEXT());
		System.out.println(output);
	}

	public void botIsDead(Player player) {
		System.out.println();
		String output = "[\uD83D\uDC80] " + player.getName() + " was the " + player.getCharacter()
				+ ". He has been killed he will not play this turn";
		System.out.println(colorize(output, RED_TEXT()));
	}

	public void killCharacter(Character characterToDie) {
		String output = "[\uD83D\uDD2A] The Assassin chooses to kill " + characterToDie;
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
		String output = "[?] The Thief chooses to steal from " + characterToSteal
				+ " but no one has chosen this character.";
		System.out.println(colorize(output, BLUE_TEXT()));
	}

	public void printKingEarnedMoney(int collectGold) {
		System.out.println("The king has " + collectGold + " noble district.(+" + collectGold + " golds).");
	}

	public void printMerchantEarnedMoney(int collectGold) {
		System.out.println(
				"The merchant has " + collectGold + " trade and handicrafts district.(+" + collectGold + " golds).");
	}

	public Attribute getDistrictColor(District district) {
		return switch (district.getColor()) {
		case "Blue" -> BLUE_TEXT();
		case "Red" -> RED_TEXT();
		case "Yellow" -> YELLOW_TEXT();
		case "Green" -> GREEN_TEXT();
		case "Purple" -> BRIGHT_MAGENTA_TEXT();
		default -> WHITE_TEXT();
		};
	}

	public void printDistrictWithColor(Player player) {
		City playerCity = player.getCity();
		for (District district : playerCity.getBuiltDistrict()) {
			System.out.print("| ");
			System.out.print(colorize(district.toString(), getDistrictColor(district)));
			System.out.print(" | ");
		}
		System.out.println();
	}

	public void printArchitectSpell() {
		String output = "[!] The Architect can take 2 more Districts and build 3 Districts";
		System.out.println(colorize(output, GREEN_TEXT()));
	}

	public void printMagicianSpellSwapHands(Character character) {
		String output = "[!] The Magician swaps hand with " + character;
		System.out.println(colorize(output, CYAN_TEXT()));
	}

	public void printMagicianSpellSwapCards(ArrayList<District> districts) {
		String output = "[!] The Magician swaps " + districts.size() + " with the deck: " + districts;
		System.out.println(colorize(output, CYAN_TEXT()));
	}

	public void printMagicianSpellFailed(Character characterToSwapWith) {
		String output = "[!] The Magician tried to swap with " + characterToSwapWith
				+ " but no Player has this Character.";
		System.out.println(colorize(output, CYAN_TEXT()));
	}

	public void printAddCardToTheDeck(Player player, District district) {
		String coloredOutput = colorize("    [+] ", GREEN_TEXT());
		String coloredOutput2 = colorize(district.toString(), getDistrictColor(district));
		System.out.println(coloredOutput + player.getName() + " add the card to his hand " + coloredOutput2);
	}

	public void printPutCardBackToTheDeck(Player player, District district) {
		String coloredOutput2 = colorize(district.toString(), getDistrictColor(district));
		System.out.println(player.getName() + " putBack the card " + coloredOutput2);
	}

	public void printBasicAction(ArrayList<BasicActions> basicActions) {
		//basicActions.forEach(basicAction -> System.out.println(basicActions));
		for(BasicActions action : basicActions)
			System.out.println(action);
	}

}
