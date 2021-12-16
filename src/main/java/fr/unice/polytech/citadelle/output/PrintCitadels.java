package fr.unice.polytech.citadelle.output;

import java.util.ArrayList;

import com.diogonunes.jcolor.Attribute;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.Character;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class PrintCitadels {

	public static void startCitadelles() {
		String normal = "  ____ _ _            _      _ _           \r\n"
				+ " / ___(_) |_ __ _  __| | ___| | | ___  ___ \r\n"
				+ "| |   | | __/ _` |/ _` |/ _ \\ | |/ _ \\/ __|\r\n"
				+ "| |___| | || (_| | (_| |  __/ | |  __/\\__ \\\r\n"
				+ " \\____|_|\\__\\__,_|\\__,_|\\___|_|_|\\___||___/";
		String epic = " _______ __________________ _______  ______   _______  _        _        _______  _______ \\r\\n\"\r\n"
				+ "				+ \"(  ____ \\\\\\\\__   __/\\\\__   __/(  ___  )(  __  \\\\ (  ____ \\\\( \\\\      ( \\\\      (  ____ \\\\(  ____ \\\\\\r\\n\"\r\n"
				+ "				+ \"| (    \\\\/   ) (      ) (   | (   ) || (  \\\\  )| (    \\\\/| (      | (      | (    \\\\/| (    \\\\/\\r\\n\"\r\n"
				+ "				+ \"| |         | |      | |   | (___) || |   ) || (__    | |      | |      | (__    | (_____ \\r\\n\"\r\n"
				+ "				+ \"| |         | |      | |   |  ___  || |   | ||  __)   | |      | |      |  __)   (_____  )\\r\\n\"\r\n"
				+ "				+ \"| |         | |      | |   | (   ) || |   ) || (      | |      | |      | (            ) |\\r\\n\"\r\n"
				+ "				+ \"| (____/\\\\___) (___   | |   | )   ( || (__/  )| (____/\\\\| (____/\\\\| (____/\\\\| (____/\\\\/\\\\____) |\\r\\n\"\r\n"
				+ "				+ \"(_______/\\\\_______/   )_(   |/     \\\\|(______/ (_______/(_______/(_______/(_______/\\\\_______)";
		dropALine();
		dropALine();
		System.out.println(colorize(normal, BOLD()));
		System.out.println(colorize("_____________________________________________", BOLD()));
		dropALine();
		dropALine();
		dropALine();
		
	}

	public static void printRolePhase() {
		printFitLayer();
		System.out.println(colorize("Pick character cards:", BOLD()));
	}
	
	public static String stringColoredGold(int value) {
		String gold = " golds";
		if(value < 2) gold= " gold";
		return colorize(value+gold, BRIGHT_YELLOW_TEXT());
	}
	
	public static void playerStartTurn(Character character, Player player) {
		String output = "It is up to the "+ character +" to play ("+player.getName()+", "+stringColoredGold(player.getGolds())+"):";
		System.out.println(colorize(output, BOLD()));
	}

	public static void chooseRole(Player player, Character role) {
		System.out.println("\t- The robot " + player.getName() + " choose the character " + role.getName() + " "
				+ role.getValue());
	}

	public static void chooseDistrict(Player player, District district) {
		System.out.println("The robot " + player.getName() + " choose the district " + district.getName() + " "
				+ district.getValue());
	}

	public static void printRanking(ArrayList<Player> listOfPlayer) {
		System.out.println(colorize("- Game Results -", BOLD()));
		System.out.println(colorize("================", BOLD()));
		listOfPlayer.forEach(player -> System.out.println("[" + player.getRank() + "] " + player.getName()
				+ " with a score of " + player.getScore() + " (" +stringColoredGold(player.getGolds())+ ")"));
	}

	public static void dropALine() {
		System.out.println();
	}

	public static void printFirstPlayerToComplete(Player firstPlayerToComplete) {
		String output = "*** The player " + firstPlayerToComplete.getName()
				+ " is the first player to complete his city ! ***";
		System.out.println(colorize(output, YELLOW_BACK(), BLACK_TEXT()));
	}

	public static void printPlayerToCompleteCity(Player player) {
		String output = player.getName() + " has completed his city !";
		System.out.println(colorize(output, YELLOW_BACK(), BLACK_TEXT()));
	}

	public static void printFitLayer() {
		dropALine();
		System.out.println(colorize("____________________________________________________________________", BOLD()));
	}

	public static void printLayer() {
		String layer = 
				"________________________________________________________________________________________________________________________________________";
		System.out.println(colorize(layer, BOLD()));
		System.out.println(colorize(layer, BOLD()));

		dropALine();
	}

	public static void printKingSpell(Player player) {
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

	public static void printTakeGold(Player player) {
		String coloredOutput = colorize("[+] ", GREEN_TEXT());
		System.out.println("\t"+coloredOutput + player.getName() + " takes "+stringColoredGold(2)+".");
	}

	public static void printTakeDistrictCard(Player player, District district) {
		String coloredOutput2 = colorize(district.toString(), getDistrictColor(district));
		System.out.println("\t"+player.getName() + " takes a district card: " + coloredOutput2);
	}

	public static void printBuildDistrict(Player player, District district) {
		String coloredOutput = colorize("[⛏] ", GREEN_TEXT());
		System.out.print("\t"+coloredOutput + player.getName() + " builds ");
		coloredOutput = colorize(district.toString(), getDistrictColor(district));
		System.out.println(coloredOutput);
	}

	public static void printBoard(Board game) {
		printFitLayer();
		System.out.println(colorize("[?] City of all the Players:", BOLD()));
		game.getListOfPlayer().forEach(PrintCitadels::printBoardOfPlayer);
		dropALine();
	}

	public static void printBoardOfPlayer(Player player) {
		System.out.print("\t[?] City of " + player.getName() + ": ");
		printDistrictWithColor(player);
	}

	public static void printNumberRound(int roundNumber) {
		//String output = colorize("Round number " + roundNumber + ".", BRIGHT_BLUE_TEXT());
		String output = "Round number " + roundNumber + ".";
		String topLayer;
		String botLayer;

		if(roundNumber >=10) {
			topLayer = " ____________________\r\n"
					+ "|                    |";
			botLayer = "|____________________|";
		}
			

		else {
			topLayer = " ___________________\r\n"
					+ "|                   |";
			botLayer = "|___________________|";
		}
		
		System.out.println(colorize(topLayer, BOLD()));
		System.out.println(colorize("|  "+output+"  |",BOLD()));
		System.out.println(colorize(botLayer, BOLD()));
		dropALine();
	}

	public static void botIsDead(Player player) {
		String output = "[\uD83D\uDC80] " + player.getName() + " was the " + player.getCharacter()
				+ ". He has been killed he will not play this turn";
		System.out.println("\t"+colorize(output, RED_TEXT()));
	}

	public static void killCharacter(Character characterToDie) {
		String output = "\t[\uD83D\uDD2A] The Assassin chooses to kill " + characterToDie;
		System.out.println(colorize(output, RED_TEXT()));
	}

	public static void printPhase(String phase, Player player) {
		String output = "*** " + player.getName() + " is in " + phase + " mode ***";
		System.out.println(colorize(output, MAGENTA_BACK(), BLACK_TEXT()));
	}

	public static void stealCharacter(Character characterToSteal, int golds) {
		String output = "\t[!] The Thief chooses to steal from " + characterToSteal + " (+" + stringColoredGold(golds) + " ).";
		System.out.println(colorize(output, BLUE_TEXT()));
	}

	public static void failedToStealCharacter(Character characterToSteal) {
		String output = "\t[?] The Thief chooses to steal from " + characterToSteal
				+ " but no one has chosen this character.";
		System.out.println(colorize(output, BLUE_TEXT()));
	}


	public static void printCharacterEarnedMoney(int collectGold,String nameOfTheCharacter,String nameOfTheFamilyDistrict) {
		System.out.println(
				"\tThe "+nameOfTheCharacter+" has " + collectGold + " "+nameOfTheFamilyDistrict+" district.(+" + stringColoredGold(collectGold) + " ).");
	}
	public static void printCharacterEarnedNoMoney(String nameOfTheCharacter,String nameOfTheFamilyDistrict) {
		System.out.println("\tThe "+ nameOfTheCharacter+" has no "+nameOfTheFamilyDistrict+" District.(+"+stringColoredGold(0)+")");
	}

	public static Attribute getDistrictColor(District district) {
		return switch (district.getColor()) {
		case "Blue" -> BLUE_TEXT();
		case "Red" -> RED_TEXT();
		case "Yellow" -> YELLOW_TEXT();
		case "Green" -> GREEN_TEXT();
		case "Purple" -> BRIGHT_MAGENTA_TEXT();
		default -> WHITE_TEXT();
		};
	}

	public static void printDistrictWithColor(Player player) {
		City playerCity = player.getCity();
		for (District district : playerCity.getBuiltDistrict()) {
			System.out.print("| ");
			System.out.print(colorize(district.toString(), getDistrictColor(district)));
			System.out.print(" | ");
		}
		System.out.println();
	}

	public static void printArchitectSpell() {
		String output = "\t[!] The Architect can take 2 more Districts and build 3 Districts";
		System.out.println(colorize(output, GREEN_TEXT()));
	}

	public static void printMagicianSpellSwapHands(Character character) {
		String output = "\t[!] The Magician swaps hand with " + character;
		System.out.println(colorize(output, CYAN_TEXT()));
	}

	public static void printMagicianSpellSwapCards(ArrayList<District> districts) {
		String output = "\t[!] The Magician swaps " + districts.size() + " with the deck: " + districts;
		System.out.println(colorize(output, CYAN_TEXT()));
	}

	public static void printMagicianSpellFailed(Character characterToSwapWith) {
		String output = "\t[!] The Magician tried to swap with " + characterToSwapWith
				+ " but no Player has this Character.";
		System.out.println(colorize(output, CYAN_TEXT()));
	}

	public static void printAddCardToTheDeck(Player player, District district) {
		String coloredOutput = colorize("\t[+] ", GREEN_TEXT());
		String coloredOutput2 = colorize(district.toString(), getDistrictColor(district));
		System.out.println(coloredOutput + player.getName() + " add the card to his hand " + coloredOutput2);
	}

	public static void printPutCardBackToTheDeck(Player player, District district) {
		String coloredOutput2 = colorize(district.toString(), getDistrictColor(district));
		System.out.println("\t"+player.getName() + " putBack the card " + coloredOutput2);
	}

	public static void printMerchantEarnedStartRoundMoney() {
		System.out.println("\tThe Merchant wins "+stringColoredGold(1)+" at the beginning of his turn");
	}

    public static void printShuffle(String deckName) {
		String output = "[?] The " + deckName + " has been shuffled!";
		System.out.println(colorize(output, BOLD()));
    }

    public static void printBurned(Character character) {
		String output = "\t- The character " + character.getName() + " has been burned. (players can see it but can't pick it)";
		System.out.println(colorize(output));
    }

	public static void printAssassinAdvancedChoice(Player playerWithClosestScore, int predictedScore, int scoreDiffenreceWithClosestScore) {
		System.out.println("STRATEGIE:");
		System.out.println("The bot tries to find who is the player that currently has the score the closest to his own score.");
		System.out.println("He predicts thats his own score is "+predictedScore+" points.");
		System.out.println("After predicting the score of all the players he thinks that "+playerWithClosestScore+" has the score the closet to his score with a difference of "+scoreDiffenreceWithClosestScore+" points.");
	}
}
