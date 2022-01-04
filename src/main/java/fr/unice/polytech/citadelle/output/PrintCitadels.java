package fr.unice.polytech.citadelle.output;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.diogonunes.jcolor.Attribute;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_engine.Initializer;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

/**
 * Prints the log
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
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
		String output1=""+player.getDistrictCards().stream().map(district->colorize(district.getName()+" ("+district.getValue()+")",getDistrictColor(district))).collect(Collectors.toList());
		String output = "It is up to the "+ character +" to play ("+player.getName()+", "+stringColoredGold(player.getGolds())+", Hand:"+output1+"):";
		System.out.println(colorize(output, BOLD()));
	}

	public static void chooseRole(Player player, Character role) {
		System.out.println("\t- The robot " + player.getName() + " choose the character " + role.getName() + " "
				+ role.getValue());
		dropALine();
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
		String output = "\t[!] The robot " + player.getName() + " has the king, he will play first next round.";
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
		String coloredOutput = colorize("[?] ", GREEN_TEXT());
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
		String output = "[?] " + player.getName() + " was the " + player.getCharacter()
				+ ". He has been killed, he will not play this turn";
		System.out.println("\t"+colorize(output, RED_TEXT()));
	}

	public static void killCharacter(Character characterToDie) {
		String output = "\t[?] The Assassin chooses to kill " + characterToDie;
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

	public static void printMagicianSpellSwapHands(Player player) {
		String output = "\t[!] The Magician swaps hand with " + player;
		System.out.println(colorize(output, CYAN_TEXT()));
	}

	public static void printMagicianSpellSwapCards(ArrayList<District> districts) {
		String output = "\t[!] The Magician swaps " + districts.size() + " with the deck: " + districts;
		System.out.println(colorize(output, CYAN_TEXT()));
	}

	public static void printAddCardToTheDeck(Player player, District district) {
		String coloredOutput = colorize("\t[+] ", GREEN_TEXT());
		String coloredOutput2 = colorize(district.toString(), getDistrictColor(district));
		System.out.println(coloredOutput + player.getName() + " add the card to his hand " + coloredOutput2);
	}

	public static void printPutCardBackToTheDeck(Player player, District district) {
		String coloredOutput2 = colorize(district.toString(), getDistrictColor(district));
		System.out.println("\t"+player.getName() + " put back the card " + coloredOutput2);
	}

	public static void printMerchantEarnedStartRoundMoney() {
		System.out.println("\tThe Merchant wins "+stringColoredGold(1)+" at the beginning of his turn");
	}

    public static void printShuffle(String deckName) {
		String output = "[?] The " + deckName + " has been shuffled!";
		System.out.println(colorize(output, BOLD()));
    }

    public static void printBurned(Character character) {
		String output = "\t- The character " + character.getName() + " has been ";
		String output2 = "burned";
		String output3 = ". (players can see it but cannot pick it)";
		System.out.println(output + colorize(output2, BRIGHT_YELLOW_TEXT()) + output3);
    }

	public static void printAssassinAdvancedChoice(Player playerWithClosestScore, int predictedScore, int scoreDiffenreceWithClosestScore) {
		System.out.println("STRATEGY:");
		System.out.println(colorize("The bot tries to find who is the player that currently has the closest score to his own score.",ITALIC()));
		System.out.println(colorize("He predicts that his own score is "+predictedScore+" points.",ITALIC()));
		System.out.println(colorize("After predicting the score of all the players he thinks that "+playerWithClosestScore.getName()+" has the closet score to his score with a difference of "+scoreDiffenreceWithClosestScore+" points.",ITALIC()));
	}
	public static void printThiefAdvancedChoice(Player playerWithMostGold) {
		System.out.println("STRATEGY:");
		System.out.println(colorize("The bot wants to steal from the player with most golds.",ITALIC()));
		System.out.println(colorize("He analysed the board and saw that "+playerWithMostGold.getName()+" has the most golds.",ITALIC()));
	}

	public static void printWarlordAdvancedChoice(Player playerToDestroy, boolean playerToDestroyHasCompletedCity, boolean playerToDestroyIsBishop, District destroyedDistrict){
		System.out.println("STRATEGY:");
		System.out.println(colorize("The bot tries to guess who is the player that currently has a better score than him.",ITALIC()));
		System.out.println(colorize("He predicts that " + playerToDestroy.getName() + " was the player to attack.",ITALIC()));
		if (playerToDestroyHasCompletedCity){
			System.out.println(colorize("However, " + playerToDestroy.getName() + " has completed his city.",ITALIC()));
		} else if (playerToDestroyIsBishop) {
			System.out.println(colorize("However, " + playerToDestroy.getName() + " is the Bishop, player Warlord cannot attack him.", ITALIC()));
		} else {
			if (destroyedDistrict == null)
				System.out.println(colorize("Warlord strategy algorithm did not find it interesting to destroy a building.",ITALIC()));
			else if (destroyedDistrict.getColor().equals("Purple"))
				System.out.println(colorize("Warlord strategy algorithm find it interesting to destroy a purple district.",ITALIC()));
			else
				System.out.println(colorize(
						"Warlord strategy algorithm find it interesting to destroy a "
								+ destroyedDistrict.getColor() + " district to reduce gold round collection.",ITALIC()));
		}
	}

	public static void printHidCharacter(Character hidCharacter) {
		String output = "\t- The character " + hidCharacter.getName() + " has been ";
		String output2 = "hiddenly burned";
		String output3 = ". (players cannot see and pick it except for the last player.)";
		System.out.println(output + colorize(output2, YELLOW_TEXT()) + output3);
	}

	public static void warlordDontUseSpell(Player player) {
		String output = "\t[!] The "+ player.getName()+" chooses not to use his Warlord spell.";
		System.out.println(colorize(output, MAGENTA_TEXT()));
	}

	public static void warlordDoHisSpell(Player player, Player playerToDestroy, District districtToDestroy) {
		String output = "\t[!] The "+ player.getName()+" use his Warlord spell and destroy a "+playerToDestroy.getName()+"'s District :"+districtToDestroy.getName()+"(-"+(districtToDestroy.getValue()-1)+"golds).";
		System.out.println(colorize(output, MAGENTA_TEXT()));
	}
	
	public static void printResultOfPrediction() {}
	
	public static void printPlayerHasAlreadyRevealCharacter(Player currentPlayer, Player playerTarget, Character characterTarget) {
		String output = "\t[!] The "+ currentPlayer.getName()+" wants to use his spell on "+playerTarget.getName()+". He does not need to predict his Character because "+playerTarget.getName()+" has already revealed that he was "+characterTarget;
		System.out.println(output);
	}


    public static void printMagicianAdvancedChoice(Player playerWithMostDistrictGames, Player player) {
		System.out.println(colorize(" The"+player.getName()+" wants to steal from the player with most district.",ITALIC()));
		System.out.println(colorize("He analysed the board and saw that "+playerWithMostDistrictGames.getName()+" has the most districts.",ITALIC()));
    }

	public static void playerHasTwoTimesMoreHisCards(Player player) {
		System.out.println("STRATEGY:");
		System.out.println(colorize("The "+player.getName()+" is going to choose if he wants to steal from another Player or swap cards with the deck",ITALIC()));
		System.out.println(colorize("He analysed the board and saw that there is a player that has two times more district than him. He will try to swap hands.",ITALIC()));
	}

	public static void playerSwapWithDeck(Player player) {
		System.out.println("STRATEGY:");
		System.out.println(colorize("The "+player.getName()+" is going to choose if he wants to steal from another Player or swap cards with the deck",ITALIC()));
		System.out.println(colorize("He analysed the board and saw that no player has two times more district then him. He will change some cards with the deck.",ITALIC()));
	}

	public static void pritCardsToBeSwapped(Player player, ArrayList<District> listDistrictToSwap) {
		String output1=""+listDistrictToSwap.stream().map(district->colorize(district.getName()+" ("+district.getValue()+")",getDistrictColor(district))).collect(Collectors.toList());
		System.out.println(colorize("He chooses what cards he will swap by finding cards that he already built.",ITALIC()));
		System.out.println(colorize("he swaps:"+output1,ITALIC()));
	}
	public static void printNoCardsToBeSwapped(Player player) {
		System.out.println(colorize("He chooses what cards he will swap by finding cards that he already built.",ITALIC()));
		System.out.println(colorize("He consider that all his cards are good, he will not do his spell.",ITALIC()));
	}
	
	public static void printPredictionExplaination(Player currentPlayer, Player targetPlayer, Character characterOfTargetPlayer, ArrayList<Character> listOfCharacter){
		String firstPartOutput = "Prediction: "+currentPlayer.getName()+" predicts that "+targetPlayer.getName()+" is "+characterOfTargetPlayer.getName()+" because ";
		String finalOutput = firstPartOutput+ initHashPrediction(listOfCharacter).get(characterOfTargetPlayer);
		System.out.println(colorize(finalOutput,ITALIC()));
	}
	
	public static void printChooseCharacterExplaination(Player currentPlayer, Character chooseTargetPlayer, ArrayList<Character> listOfCharacter){
		String firstPartOutput = "\tExplanation: "+currentPlayer.getName()+" chooses the character "+chooseTargetPlayer.getName()+" because ";
		String finalOutput = firstPartOutput+ initHashPrediction(listOfCharacter).get(chooseTargetPlayer);
		System.out.println(colorize(finalOutput,ITALIC()));
	}
	
	private static HashMap<Character, String> initHashPrediction(ArrayList<Character> listOfCharacter){
		HashMap<Character, String> prediction = new HashMap<Character, String>();
		prediction.put(listOfCharacter.get(Initializer.ASSASSIN_INDEX), "someone is close to finish the game (has 6 districts)");
		prediction.put(listOfCharacter.get(Initializer.THIEF_INDEX), "he does not have a lot of golds");
		prediction.put(listOfCharacter.get(Initializer.MAGICIAN_INDEX), "he does not have a lot of district cards");
		prediction.put(listOfCharacter.get(Initializer.KING_INDEX), "he has 3 Nobility");
		prediction.put(listOfCharacter.get(Initializer.BISHOP_INDEX), "he has 6 or more districts built in his city");
		prediction.put(listOfCharacter.get(Initializer.MERCHANT_INDEX), "he has 3 or more Trade and Handicrafts districts built in his city");
		prediction.put(listOfCharacter.get(Initializer.ARCHITECT_INDEX), "he does not have a lot of golds");
		prediction.put(listOfCharacter.get(Initializer.WARLORD_INDEX), "someone is close to finish the game (has 7 districts)");
		return prediction;
	}

	public static void ExplanationChooseCardButNotFound(Player currentPlayer) {
		System.out.println(colorize("\tExplanation: "+currentPlayer.getName()+" takes any possible card because none is more advantageous than another",ITALIC()));
	}
}
