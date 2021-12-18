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

public class Predict {
	Board board;
	Player currentPlayer;

	public Predict(Board board, Player player) {
		this.board = board;
		this.currentPlayer = player;
	}

	// Methode qui predit ce que le player a.
	public Character predictWhoIsPlayer(Player targetPlayer, ArrayList<Character> listOfUntargetableCharacter) {
		Optional<Character> potentialCharacterOfTargetPlayer = checkAlreadyReveal(targetPlayer);
		if(potentialCharacterOfTargetPlayer.isPresent()) {
			Character targetCharacter = potentialCharacterOfTargetPlayer.get();
			if(!listOfUntargetableCharacter.contains(targetCharacter))
				return targetCharacter;
		}

		if (canBeArchitect(targetPlayer, listOfUntargetableCharacter)) {
			Character targetCharacter = listGetCharacter(Initializer.ARCHITECT_INDEX);
			PrintCitadels.printChooseCharacterExplaination(targetCharacter, board.getListOfCharacter());
			return targetCharacter;
		}

		if (canBeBishop(targetPlayer, listOfUntargetableCharacter))
			return listGetCharacter(Initializer.BISHOP_INDEX);

		if (canBeKing(targetPlayer, listOfUntargetableCharacter))
			return listGetCharacter(Initializer.KING_INDEX);

		if (canBeMerchant(targetPlayer, listOfUntargetableCharacter))
			return listGetCharacter(Initializer.MERCHANT_INDEX);

		if (canBeThief(targetPlayer, listOfUntargetableCharacter))
			return listGetCharacter(Initializer.THIEF_INDEX);

		if (canBeMagician(targetPlayer, listOfUntargetableCharacter))
			return listGetCharacter(Initializer.MAGICIAN_INDEX);

		if (canBeWarlord(targetPlayer, listOfUntargetableCharacter))
			return listGetCharacter(Initializer.WARLORD_INDEX);

		return targetableCharactersForPredictWhoIsPlayer(listOfUntargetableCharacter).get(0);
	}

	private Optional<Character> checkAlreadyReveal(Player targetPlayer) {
		Optional<Character> potentialCharacterOfTargetPlayer = board.gethashOfViewCharacters().get(targetPlayer);
		if (potentialCharacterOfTargetPlayer.isPresent()) {
			Character targetCharacter = potentialCharacterOfTargetPlayer.get();
			PrintCitadels.printPlayerHasAlreadyRevealCharacter(currentPlayer, targetPlayer, targetCharacter);
			return Optional.of(targetCharacter);
		}
		else
			return Optional.empty();
	}

	public ArrayList<Character> allCharacters() {
		ArrayList<Character> listOfAllCharacter = new ArrayList<>();

		for(int i = 0; i < 8; i++)
			listOfAllCharacter.add(listGetCharacter(i));

		return listOfAllCharacter;
	}

	public ArrayList<Character> targetableCharactersForPredictWhoIsPlayer(ArrayList<Character> listOfUntargetableCharacter) {
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

	// Is the Architect interesting for this player? It can be if he does not have a
	// lot of golds
	public boolean canBeArchitect(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<District> handOfPlayer = handForPredictWhoIsPlayer(player);
		int goldsOfPlayer = goldsForPredictWhoIsPlayer(player);

		return (handOfPlayer.size() >= 3 && goldsOfPlayer >= 6 && listOfTargetableCharacter.contains(listGetCharacter(Initializer.ARCHITECT_INDEX)));
	}

	// Is the Bishop interesting for this player? It can be if he has 6 or more
	// districts built in his city
	public boolean canBeBishop(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<District> cityOfPlayer = cityForPredictWhoIsPlayer(player);

		return (cityOfPlayer.size() >= 6 && listOfTargetableCharacter.contains(listGetCharacter(Initializer.BISHOP_INDEX)));
	}

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

	// Is the Thief interesting for this player? It can be if he does not have a lot
	// of golds
	public boolean canBeThief(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		int goldsOfPlayer = goldsForPredictWhoIsPlayer(player);

		return (goldsOfPlayer <= 3 && listOfTargetableCharacter.contains(listGetCharacter(Initializer.THIEF_INDEX)));
	}

	// Is the Magician interesting for this player? It can be if he does not have a
	// lot of district cards
	public boolean canBeMagician(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<District> handOfPlayer = handForPredictWhoIsPlayer(player);

		return (handOfPlayer.size() <= 2 && listOfTargetableCharacter.contains(listGetCharacter(Initializer.MAGICIAN_INDEX)));
	}

	// Is the Warlord interesting for this player? It can be if someone is close to
	// finish the game (has 7 districts)
	public boolean canBeWarlord(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<Player> listOfPlayers = playersForPredictWhoIsPlayer(player);

		for (Player otherPlayer : listOfPlayers)
			if (otherPlayer.getCity().getBuiltDistrict().size() == 7 && listOfTargetableCharacter.contains(listGetCharacter(Initializer.WARLORD_INDEX)))
				return true;

		return false;
	}

	public Character listGetCharacter(int indexOfCharacter) {
		ArrayList<Character> listOfCharacter = board.getListOfCharacter();

		return listOfCharacter.get(indexOfCharacter);
	}

}
