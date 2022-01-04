package fr.unice.polytech.citadelle.output;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
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
	private final static Logger LOGGER = Logger.getLogger(PrintCitadels.class.getName());


	public static void startCitadelles() {
		System.setProperty("java.util.logging.SimpleFormatter.format", colorize("%5$s %n", BRIGHT_WHITE_TEXT()));

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
		LOGGER.info(colorize(normal, BOLD()));
		LOGGER.info(colorize("_____________________________________________", BOLD()));
		dropALine();
		dropALine();
		dropALine();
		
	}

	public static void printRolePhase() {
		printFitLayer();
		LOGGER.info(colorize("Pick character cards:", BOLD()));
	}
	
	public static String stringColoredGold(int value) {
		String gold = " golds";
		if(value < 2) gold= " gold";
		return colorize(value+gold, BRIGHT_YELLOW_TEXT());
	}
	
	public static void playerStartTurn(Character character, Player player) {
		StringBuilder handOutput = new StringBuilder();
		handOutput.append(colorize("[", BRIGHT_WHITE_TEXT()));

		ArrayList<District> playerHand = player.getDistrictCards();
		if (playerHand.size() > 0) {
			int i;
			for (i = 0; i < playerHand.size() - 1; i++) {
				handOutput.append(colorize(playerHand.get(i).toString(), getDistrictColor(playerHand.get(i))))
						.append(colorize(", ", BRIGHT_WHITE_TEXT()));
			}
			handOutput.append(colorize(playerHand.get(i).toString(), getDistrictColor(playerHand.get(i))));
			handOutput.append(colorize("]", BRIGHT_WHITE_TEXT()));
		}
		else
			handOutput.append(colorize("empty]", BRIGHT_WHITE_TEXT()));

		String output =
				colorize("It is up to the " + character + " to play (", BRIGHT_WHITE_TEXT(), BOLD())
				+ colorize(player.getName() + ", ", BRIGHT_WHITE_TEXT())
				+ stringColoredGold(player.getGolds())
				+ colorize(", Hand: ", BRIGHT_WHITE_TEXT())
				+  handOutput
				+ colorize("):", BRIGHT_WHITE_TEXT(), BOLD());

		LOGGER.info(output);
	}

	public static void chooseRole(Player player, Character role) {
		LOGGER.info("\t- The robot " + player.getName() + " choose the character " + role.getName() + " "
				+ role.getValue());
		dropALine();
	}

	public static void chooseDistrict(Player player, District district) {
		LOGGER.info("The robot " + player.getName() + " choose the district " + district.getName() + " "
				+ district.getValue());
	}

	public static void printRanking(ArrayList<Player> listOfPlayer) {
		LOGGER.info(colorize("- Game Results -", BOLD()));
		LOGGER.info(colorize("================", BOLD()));
		listOfPlayer.forEach(player -> LOGGER.info("[" + player.getRank() + "] " + player.getName()
				+ " with a score of " + player.getScore() + " (" +stringColoredGold(player.getGolds())+ colorize(")", BRIGHT_WHITE_TEXT())));
	}

	public static void dropALine() {
		LOGGER.info("");
	}

	public static void printFirstPlayerToComplete(Player firstPlayerToComplete) {
		String output = "*** The player " + firstPlayerToComplete.getName()
				+ " is the first player to complete his city ! ***";
		LOGGER.info(colorize(output, YELLOW_BACK(), BLACK_TEXT()));
	}

	public static void printPlayerToCompleteCity(Player player) {
		String output = player.getName() + " has completed his city !";
		LOGGER.info(colorize(output, YELLOW_BACK(), BLACK_TEXT()));
	}

	public static void printFitLayer() {
		dropALine();
		LOGGER.info(colorize("____________________________________________________________________", BOLD()));
	}

	public static void printLayer() {
		String layer = 
				"________________________________________________________________________________________________________________________________________";
		LOGGER.info(colorize(layer, BOLD()));
		LOGGER.info(colorize(layer, BOLD()));

		dropALine();
	}

	public static void printKingSpell(Player player) {
		String output = "\t[!] The robot " + player.getName() + " has the king, he will play first next round.";
		LOGGER.info(colorize(output, YELLOW_TEXT()));
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

		LOGGER.info("\t" + coloredOutput + colorize(player.getName() + " takes ", BRIGHT_WHITE_TEXT())+stringColoredGold(2));
	}

	public static void printTakeDistrictCard(Player player, District district) {
		String coloredOutput2 = colorize(district.toString(), getDistrictColor(district));
		LOGGER.info("\t"+player.getName() + " takes a district card: " + coloredOutput2);
	}

	public static void printBuildDistrict(Player player, District district) {
		String coloredOutput = colorize("[?] ", GREEN_TEXT());
		String coloredDistrictName = colorize(district.toString(), getDistrictColor(district));
		LOGGER.info("\t" + coloredOutput +  colorize(player.getName() + " builds ", BRIGHT_WHITE_TEXT()) + coloredDistrictName);
	}

	public static void printBoard(Board game) {
		printFitLayer();
		LOGGER.info(colorize("[?] City of all the Players:", BOLD()));
		game.getListOfPlayer().forEach(PrintCitadels::printBoardOfPlayer);
		dropALine();
	}

	public static void printBoardOfPlayer(Player player) {
		LOGGER.info(colorize("\t[*] City of " + player.getName() + ": " + printDistrictWithColor(player), BRIGHT_WHITE_TEXT()));
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
		
		LOGGER.info(colorize(topLayer, BOLD()));
		LOGGER.info(colorize("|  "+output+"  |",BOLD()));
		LOGGER.info(colorize(botLayer, BOLD()));
		dropALine();
	}

	public static void botIsDead(Player player) {
		String output = "[?] " + player.getName() + " was the " + player.getCharacter()
				+ ". He has been killed, he will not play this turn";
		LOGGER.info("\t"+colorize(output, RED_TEXT()));
	}

	public static void killCharacter(Character characterToDie) {
		String output = "\t[?] The Assassin chooses to kill " + characterToDie;
		LOGGER.info(colorize(output, RED_TEXT()));
	}

	public static void printPhase(String phase, Player player) {
		String output = "*** " + player.getName() + " is in " + phase + " mode ***";
		LOGGER.info(colorize(output, MAGENTA_BACK(), BLACK_TEXT()));
	}

	public static void stealCharacter(Character characterToSteal, int golds) {
		String output = "\t[!] The Thief chooses to steal from " + characterToSteal
				+ colorize(" (+", BRIGHT_WHITE_TEXT())
				+ stringColoredGold(golds) + colorize(").", BRIGHT_WHITE_TEXT());
		LOGGER.info(colorize(output, BLUE_TEXT()));
	}

	public static void failedToStealCharacter(Character characterToSteal) {
		String output = "\t[?] The Thief chooses to steal from " + characterToSteal
				+ " but no one has chosen this character.";
		LOGGER.info(colorize(output, BLUE_TEXT()));
	}


	public static void printCharacterEarnedMoney(int collectGold,String nameOfTheCharacter,String nameOfTheFamilyDistrict) {
		LOGGER.info(
				"\tThe "+nameOfTheCharacter+" has " + collectGold + " "+nameOfTheFamilyDistrict+" district. (+" + stringColoredGold(collectGold) + colorize(")", BRIGHT_WHITE_TEXT()));
	}
	public static void printCharacterEarnedNoMoney(String nameOfTheCharacter,String nameOfTheFamilyDistrict) {
		LOGGER.info("\tThe "+ nameOfTheCharacter+" has no "+nameOfTheFamilyDistrict+" District. (+"+stringColoredGold(0)+")");
	}

	public static Attribute getDistrictColor(District district) {
		return switch (district.getColor()) {
		case "Blue" -> BLUE_TEXT();
		case "Red" -> RED_TEXT();
		case "Yellow" -> YELLOW_TEXT();
		case "Green" -> GREEN_TEXT();
		case "Purple" -> BRIGHT_MAGENTA_TEXT();
		default -> BRIGHT_WHITE_TEXT();
		};
	}

	public static String printDistrictWithColor(Player player) {
		City playerCity = player.getCity();
		StringBuilder output = new StringBuilder();
		for (District district : playerCity.getBuiltDistrict()) {
			output.append(colorize("| ", BRIGHT_WHITE_TEXT()));
			output.append(colorize(district.toString(), getDistrictColor(district)));
			output.append(colorize(" | ", BRIGHT_WHITE_TEXT()));
		}
		return String.valueOf(output);
	}

	public static void printArchitectSpell() {
		String output = "\t[!] The Architect can take 2 more Districts and build 3 Districts";
		LOGGER.info(colorize(output, GREEN_TEXT()));
	}

	public static void printMagicianSpellSwapHands(Player player) {
		String output = "\t[!] The Magician swaps hand with " + player;
		LOGGER.info(colorize(output, CYAN_TEXT()));
	}

	public static void printMagicianSpellSwapCards(ArrayList<District> districts) {
		String output = "\t[!] The Magician swaps " + districts.size() + " with the deck: " + districts;
		LOGGER.info(colorize(output, CYAN_TEXT()));
	}

	public static void printAddCardToTheDeck(Player player, District district) {
		String coloredOutput = colorize("\t[+] ", GREEN_TEXT());
		String coloredOutput2 = colorize(district.toString(), getDistrictColor(district));
		LOGGER.info(coloredOutput + colorize(player.getName() + " add the card to his hand ", BRIGHT_WHITE_TEXT()) + coloredOutput2);
	}

	public static void printPutCardBackToTheDeck(Player player, District district) {
		String coloredOutput2 = colorize(district.toString(), getDistrictColor(district));
		LOGGER.info("\t"+player.getName() + " put back the card " + coloredOutput2);
	}

	public static void printMerchantEarnedStartRoundMoney() {
		LOGGER.info("\tThe Merchant wins "+stringColoredGold(1)
				+ colorize(" at the beginning of his turn", BRIGHT_WHITE_TEXT()));
	}

    public static void printShuffle(String deckName) {
		String output = "[?] The " + deckName + " has been shuffled!";
		LOGGER.info(colorize(output, BOLD()));
    }

    public static void printBurned(Character character) {
		String output = "\t- The character " + character.getName() + " has been ";
		String output2 = "burned";
		String output3 = ". (players can see it but cannot pick it)";
		LOGGER.info(output + colorize(output2, BRIGHT_YELLOW_TEXT()) + output3);
    }

	public static void printAssassinAdvancedChoice(Player playerWithClosestScore, int predictedScore, int scoreDiffenreceWithClosestScore) {
		LOGGER.info("STRATEGY:");
		LOGGER.info(colorize("The bot tries to find who is the player that currently has the closest score to his own score.",ITALIC()));
		LOGGER.info(colorize("He predicts that his own score is "+predictedScore+" points.",ITALIC()));
		LOGGER.info(colorize("After predicting the score of all the players he thinks that "+playerWithClosestScore.getName()+" has the closet score to his score with a difference of "+scoreDiffenreceWithClosestScore+" points.",ITALIC()));
	}
	public static void printThiefAdvancedChoice(Player playerWithMostGold) {
		LOGGER.info("STRATEGY:");
		LOGGER.info(colorize("The bot wants to steal from the player with most golds.",ITALIC()));
		LOGGER.info(colorize("He analysed the board and saw that "+playerWithMostGold.getName()+" has the most golds.",ITALIC()));
	}

	public static void printWarlordAdvancedChoice(Player playerToDestroy, boolean playerToDestroyHasCompletedCity, boolean playerToDestroyIsBishop, District destroyedDistrict){
		LOGGER.info("STRATEGY:");
		LOGGER.info(colorize("The bot tries to guess who is the player that currently has a better score than him.",ITALIC()));
		LOGGER.info(colorize("He predicts that " + playerToDestroy.getName() + " was the player to attack.",ITALIC()));
		if (playerToDestroyHasCompletedCity){
			LOGGER.info(colorize("However, " + playerToDestroy.getName() + " has completed his city.",ITALIC()));
		} else if (playerToDestroyIsBishop) {
			LOGGER.info(colorize("However, " + playerToDestroy.getName() + " is the Bishop, player Warlord cannot attack him.", ITALIC()));
		} else {
			if (destroyedDistrict == null)
				LOGGER.info(colorize("Warlord strategy algorithm did not find it interesting to destroy a building.",ITALIC()));
			else if (destroyedDistrict.getColor().equals("Purple"))
				LOGGER.info(colorize("Warlord strategy algorithm find it interesting to destroy a purple district.",ITALIC()));
			else
				LOGGER.info(colorize(
						"Warlord strategy algorithm find it interesting to destroy a "
								+ destroyedDistrict.getColor() + " district to reduce gold round collection.",ITALIC()));
		}
	}

	public static void printHidCharacter(Character hidCharacter) {
		String output = "\t- The character " + hidCharacter.getName() + " has been ";
		String output2 = "hiddenly burned";
		String output3 = ". (players cannot see and pick it except for the last player.)";
		LOGGER.info(output + colorize(output2, YELLOW_TEXT()) + colorize(output3, BRIGHT_WHITE_TEXT()));
	}

	public static void warlordDontUseSpell(Player player) {
		String output = "\t[!] The "+ player.getName()+" chooses not to use his Warlord spell.";
		LOGGER.info(colorize(output, MAGENTA_TEXT()));
	}

	public static void warlordDoHisSpell(Player player, Player playerToDestroy, District districtToDestroy) {
		String output = "\t[!] The "+ player.getName()+" use his Warlord spell and destroy a "+playerToDestroy.getName()+"'s District :"+districtToDestroy.getName()+"(-"+(districtToDestroy.getValue()-1)+"golds).";
		LOGGER.info(colorize(output, MAGENTA_TEXT()));
	}
	
	public static void printResultOfPrediction() {}
	
	public static void printPlayerHasAlreadyRevealCharacter(Player currentPlayer, Player playerTarget, Character characterTarget) {
		String output = "\t[!] The "+ currentPlayer.getName()+" wants to use his spell on "+playerTarget.getName()+". He does not need to predict his Character because "+playerTarget.getName()+" has already revealed that he was "+characterTarget;
		LOGGER.info(output);
	}


    public static void printMagicianAdvancedChoice(Player playerWithMostDistrictGames, Player player) {
		LOGGER.info(colorize(" The"+player.getName()+" wants to steal from the player with most district.",ITALIC()));
		LOGGER.info(colorize("He analysed the board and saw that "+playerWithMostDistrictGames.getName()+" has the most districts.",ITALIC()));
    }

	public static void playerHasTwoTimesMoreHisCards(Player player) {
		LOGGER.info("STRATEGY:");
		LOGGER.info(colorize("The "+player.getName()+" is going to choose if he wants to steal from another Player or swap cards with the deck",ITALIC()));
		LOGGER.info(colorize("He analysed the board and saw that there is a player that has two times more district than him. He will try to swap hands.",ITALIC()));
	}

	public static void playerSwapWithDeck(Player player) {
		LOGGER.info("STRATEGY:");
		LOGGER.info(colorize("The "+player.getName()+" is going to choose if he wants to steal from another Player or swap cards with the deck",ITALIC()));
		LOGGER.info(colorize("He analysed the board and saw that no player has two times more district then him. He will change some cards with the deck.",ITALIC()));
	}

	public static void pritCardsToBeSwapped(Player player, ArrayList<District> listDistrictToSwap) {
		String output1=""+listDistrictToSwap.stream().map(district->colorize(district.getName()+" ("+district.getValue()+")",getDistrictColor(district))).collect(Collectors.toList());
		LOGGER.info(colorize("He chooses what cards he will swap by finding cards that he already built.",ITALIC()));
		LOGGER.info(colorize("he swaps:"+output1,ITALIC()));
	}
	public static void printNoCardsToBeSwapped(Player player) {
		LOGGER.info(colorize("He chooses what cards he will swap by finding cards that he already built.",ITALIC()));
		LOGGER.info(colorize("He consider that all his cards are good, he will not do his spell.",ITALIC()));
	}
	
	public static void printPredictionExplaination(Player currentPlayer, Player targetPlayer, Character characterOfTargetPlayer, ArrayList<Character> listOfCharacter){
		String firstPartOutput = "Prediction: "+currentPlayer.getName()+" predicts that "+targetPlayer.getName()+" is "+characterOfTargetPlayer.getName()+" because ";
		String finalOutput = firstPartOutput+ initHashPrediction(listOfCharacter).get(characterOfTargetPlayer);
		LOGGER.info(colorize(finalOutput,ITALIC()));
	}
	
	public static void printChooseCharacterExplaination(Player currentPlayer, Character chooseTargetPlayer, ArrayList<Character> listOfCharacter){
		String firstPartOutput = "\tExplanation: "+currentPlayer.getName()+" chooses the character "+chooseTargetPlayer.getName()+" because ";
		String finalOutput = firstPartOutput+ initHashPrediction(listOfCharacter).get(chooseTargetPlayer);
		LOGGER.info(colorize(finalOutput,ITALIC()));
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
		LOGGER.info(colorize("\tExplanation: "+currentPlayer.getName()+" takes any possible card because none is more advantageous than another",ITALIC()));
	}
}
