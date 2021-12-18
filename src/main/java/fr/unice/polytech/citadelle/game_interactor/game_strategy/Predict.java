package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.*;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Predict allows to try to predict the character of other players
 * by evaluating the situation of each player if the character
 * has not been revealed yet
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Predict {
	Board board;
	Player currentPlayer;

	public Predict(Board board, Player player) {
		this.board = board;
		this.currentPlayer = player;
	}

	/**
	 * Predict which character the player might have
	 * @param targetPlayer
	 * @param listOfUntargetableCharacter ex: the thief cannot steal from the Assassin, or we cannot focus a dead character
	 * @return the character the player might have
	 */
	public Character predictWhoIsPlayer(Player targetPlayer, ArrayList<Character> listOfUntargetableCharacter) {
		Optional<Character> potentialCharacterOfTargetPlayer = checkAlreadyReveal(targetPlayer);
		if (potentialCharacterOfTargetPlayer.isPresent()) {
			Character targetCharacter = potentialCharacterOfTargetPlayer.get();
			if (!listOfUntargetableCharacter.contains(targetCharacter))
				return targetCharacter;
		}

		if (canBeArchitect(targetPlayer, listOfUntargetableCharacter)) {
			Character targetCharacter = listGetCharacter(Initializer.ARCHITECT_INDEX);
			PrintCitadels.printChooseCharacterExplaination(currentPlayer, targetCharacter, board.getListOfCharacter());
			return targetCharacter;
		}

		if (canBeBishop(targetPlayer, listOfUntargetableCharacter)) {
			Character targetCharacter = listGetCharacter(Initializer.BISHOP_INDEX);
			PrintCitadels.printChooseCharacterExplaination(currentPlayer, targetCharacter, board.getListOfCharacter());
			return targetCharacter;
		}

		if (canBeKing(targetPlayer, listOfUntargetableCharacter)) {
			Character targetCharacter = listGetCharacter(Initializer.KING_INDEX);
			PrintCitadels.printChooseCharacterExplaination(currentPlayer,targetCharacter, board.getListOfCharacter());
			return targetCharacter;
		}

		if (canBeMerchant(targetPlayer, listOfUntargetableCharacter)) {
			Character targetCharacter = listGetCharacter(Initializer.MERCHANT_INDEX);
			PrintCitadels.printChooseCharacterExplaination(currentPlayer,targetCharacter, board.getListOfCharacter());
			return targetCharacter;
		}

		if (canBeThief(targetPlayer, listOfUntargetableCharacter)) {
			Character targetCharacter = listGetCharacter(Initializer.THIEF_INDEX);
			PrintCitadels.printChooseCharacterExplaination(currentPlayer,targetCharacter, board.getListOfCharacter());
			return targetCharacter;
		}

		if (canBeMagician(targetPlayer, listOfUntargetableCharacter)) {
			Character targetCharacter = listGetCharacter(Initializer.MAGICIAN_INDEX);
			PrintCitadels.printChooseCharacterExplaination(currentPlayer,targetCharacter, board.getListOfCharacter());
			return targetCharacter;
		}

		if (canBeWarlord(targetPlayer, listOfUntargetableCharacter)) {
			Character targetCharacter = listGetCharacter(Initializer.WARLORD_INDEX);
			PrintCitadels.printChooseCharacterExplaination(currentPlayer,targetCharacter, board.getListOfCharacter());
			return targetCharacter;
		}

		return targetableCharactersForPredictWhoIsPlayer(listOfUntargetableCharacter).get(0);
	}

	/**
	 * Check if the character has already been revealed
	 * @param targetPlayer
	 * @return an Optional of Character if it has been revealed or an empty if it has not
	 */
	private Optional<Character> checkAlreadyReveal(Player targetPlayer) {
		Optional<Character> potentialCharacterOfTargetPlayer = board.gethashOfViewCharacters().get(targetPlayer);
		if (potentialCharacterOfTargetPlayer.isPresent()) {
			Character targetCharacter = potentialCharacterOfTargetPlayer.get();
			PrintCitadels.printPlayerHasAlreadyRevealCharacter(currentPlayer, targetPlayer, targetCharacter);
			return Optional.of(targetCharacter);
		} else
			return Optional.empty();
	}

	/**
	 * Get a copy of listOfCharacter that we can modify
	 * @return the copy of listOfCharacter
	 */
	public ArrayList<Character> allCharacters() {
		ArrayList<Character> listOfAllCharacter = new ArrayList<>();

		for (int i = 0; i < 8; i++)
			listOfAllCharacter.add(listGetCharacter(i));

		return listOfAllCharacter;
	}

	/**
	 * Find only the characters we can target with a spell
	 * @param listOfUntargetableCharacter
	 * @return the list of characters we can target
	 */
	public ArrayList<Character> targetableCharactersForPredictWhoIsPlayer(
			ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = allCharacters();

		for (Character character : listOfUntargetableCharacter)
			listOfTargetableCharacter.remove(character);

		return listOfTargetableCharacter;
	}

	// Return the golds of the player who is targeted
	private int goldsForPredictWhoIsPlayer(Player player) {
		return player.getGolds();
	}

	// Return the hand of the player who is targeted
	private ArrayList<District> handForPredictWhoIsPlayer(Player player) {
		return player.getDistrictCards();
	}

	// Return the city of the player who is targeted
	private ArrayList<District> cityForPredictWhoIsPlayer(Player player) {
		return player.getCity().getBuiltDistrict();
	}

	// Return the players other than the one who is targeted
	public ArrayList<Player> playersForPredictWhoIsPlayer(Player player) {
		ArrayList<Player> listOfPlayers = (ArrayList<Player>) board.getListOfPlayer().clone();
		listOfPlayers.remove(player);
		return listOfPlayers;
	}

	/**
	 * Check if the player can potentially be the Architect
	 * @param player
	 * @param listOfUntargetableCharacter
	 * @return if he can be the Architect
	 */
	// Is the Architect interesting for this player? It can be if he does not have a
	// lot of golds
	public boolean canBeArchitect(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<District> handOfPlayer = handForPredictWhoIsPlayer(player);
		int goldsOfPlayer = goldsForPredictWhoIsPlayer(player);

		return (handOfPlayer.size() >= 3 && goldsOfPlayer >= 6
				&& listOfTargetableCharacter.contains(listGetCharacter(Initializer.ARCHITECT_INDEX)));
	}

	/**
	 * Check if the player can potentially be the Bishop
	 * @param player
	 * @param listOfUntargetableCharacter
	 * @return if he can be the Bishop
	 */
	// Is the Bishop interesting for this player? It can be if he has 6 or more
	// districts built in his city
	public boolean canBeBishop(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<District> cityOfPlayer = cityForPredictWhoIsPlayer(player);

		return (cityOfPlayer.size() >= 6
				&& listOfTargetableCharacter.contains(listGetCharacter(Initializer.BISHOP_INDEX)));
	}

	/**
	 * Check if the player can potentially be the King
	 * @param player
	 * @param listOfUntargetableCharacter
	 * @return if it can be the King
	 */
	// Is the King interesting for this player? It can be if he has 3 nobility
	// districts built in his city
	public boolean canBeKing(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<District> cityOfPlayer = cityForPredictWhoIsPlayer(player);

		int counter = 0;
		for (District district : cityOfPlayer) {
			if (district.getNameOfFamily().equals("Nobility"))
				counter++;
		}

		return (counter == 3 && listOfTargetableCharacter.contains(listGetCharacter(Initializer.KING_INDEX)));
	}

	/**
	 * Check if the player can potentially be the Merchant
	 * @param player
	 * @param listOfUntargetableCharacter
	 * @return if he can be the Merchant
	 */
	// Is the Merchant interesting for this player? It can be if he has 3 or more
	// Trade and Handicrafts districts built in his city
	public boolean canBeMerchant(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<District> cityOfPlayer = cityForPredictWhoIsPlayer(player);

		int counter = 0;
		for (District district : cityOfPlayer) {
			if (district.getNameOfFamily().equals("Trade and Handicrafts"))
				counter++;
		}

		return (counter >= 3 && listOfTargetableCharacter.contains(listGetCharacter(Initializer.MERCHANT_INDEX)));
	}

	/**
	 * Check if the player can potentially be the Thief
	 * @param player
	 * @param listOfUntargetableCharacter
	 * @return if he can be the Thief
	 */
	// Is the Thief interesting for this player? It can be if he does not have a lot
	// of golds
	public boolean canBeThief(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		int goldsOfPlayer = goldsForPredictWhoIsPlayer(player);

		return (goldsOfPlayer <= 3 && listOfTargetableCharacter.contains(listGetCharacter(Initializer.THIEF_INDEX)));
	}

	/**
	 * Check if the player can potentially be the Magician
	 * @param player
	 * @param listOfUntargetableCharacter
	 * @return if he can be the Magician
	 */
	// Is the Magician interesting for this player? It can be if he does not have a
	// lot of district cards
	public boolean canBeMagician(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<District> handOfPlayer = handForPredictWhoIsPlayer(player);

		return (handOfPlayer.size() <= 2
				&& listOfTargetableCharacter.contains(listGetCharacter(Initializer.MAGICIAN_INDEX)));
	}

	/**
	 * Check if the player can potentially be the Warlord
	 * @param player
	 * @param listOfUntargetableCharacter
	 * @return if he can be the Warlord
	 */
	// Is the Warlord interesting for this player? It can be if someone is close to
	// finish the game (has 7 districts)
	public boolean canBeWarlord(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<Player> listOfPlayers = playersForPredictWhoIsPlayer(player);

		for (Player otherPlayer : listOfPlayers)
			if (otherPlayer.getCity().getBuiltDistrict().size() == 7
					&& listOfTargetableCharacter.contains(listGetCharacter(Initializer.WARLORD_INDEX)))
				return true;

		return false;
	}

	/**
	 * Get a character in function of index
	 * @param indexOfCharacter
	 * @return the character corresponding to the index
	 */
	public Character listGetCharacter(int indexOfCharacter) {
		ArrayList<Character> listOfCharacter = board.getListOfCharacter();

		return listOfCharacter.get(indexOfCharacter);
	}

}
