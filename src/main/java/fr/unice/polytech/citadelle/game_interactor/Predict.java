package fr.unice.polytech.citadelle.game_interactor;

import fr.unice.polytech.citadelle.characters_class.*;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.ArrayList;
import java.util.Optional;

public class Predict {
	Board board;

	public Predict(Board board) {
		this.board = board;
	}

	// Methode qui predit ce que le player a.
	public Character predictWhoIsPlayer(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		Optional<Character> potentialCharacterOfTargetPlayer = checkAlreadyReveal(player);
		//vérifier que l'on puisse renvoyer bien utiliser le sort sur ce character
		if(potentialCharacterOfTargetPlayer.isPresent()) {
			Character targetCharacter = potentialCharacterOfTargetPlayer.get();
			return targetCharacter;
		}

		if (canBeArchitect(player, listOfUntargetableCharacter))
			return listGetCharacter(Initializer.ARCHITECT_INDEX);

		if (canBeBishop(player, listOfUntargetableCharacter))
			return listGetCharacter(Initializer.BISHOP_INDEX);

		if (canBeKing(player, listOfUntargetableCharacter))
			return listGetCharacter(Initializer.KING_INDEX);

		if (canBeMerchant(player, listOfUntargetableCharacter))
			return listGetCharacter(Initializer.MERCHANT_INDEX);

		if (canBeThief(player, listOfUntargetableCharacter))
			return listGetCharacter(Initializer.THIEF_INDEX);

		if (canBeMagician(player, listOfUntargetableCharacter))
			return listGetCharacter(Initializer.MAGICIAN_INDEX);

		if (canBeWarlord(player, listOfUntargetableCharacter))
			return listGetCharacter(Initializer.WARLORD_INDEX);

		return targetableCharactersForPredictWhoIsPlayer(listOfUntargetableCharacter).get(0);
	}

	private Optional<Character> checkAlreadyReveal(Player player) {
		Optional<Character> potentialCharacterOfTargetPlayer = board.gethashOfViewCharacters().get(player);
		if (potentialCharacterOfTargetPlayer.isPresent()) {
			Character targetCharacter = potentialCharacterOfTargetPlayer.get();
			PrintCitadels.printPlayerHasAlreadyRevealCharacter(player, player, targetCharacter);
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
	private ArrayList<Player> playersForPredictWhoIsPlayer(Player player) {
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

		return (handOfPlayer.size() >= 3 && goldsOfPlayer >= 6 && listOfTargetableCharacter.contains(Initializer.ARCHITECT_INDEX));
	}

	// Is the Bishop interesting for this player? It can be if he has 6 or more
	// districts built in his city
	private boolean canBeBishop(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<District> cityOfPlayer = cityForPredictWhoIsPlayer(player);

		return (cityOfPlayer.size() >= 6 && listOfTargetableCharacter.contains(Initializer.BISHOP_INDEX));
	}

	// Is the King interesting for this player? It can be if he has 3 nobility
	// districts built in his city
	private boolean canBeKing(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<District> cityOfPlayer = cityForPredictWhoIsPlayer(player);

		int counter = 0;
		for (District district : cityOfPlayer) {
			if (district.getNameOfFamily().equals("Nobility"))
				counter++;
		}

		return (counter == 3 && listOfTargetableCharacter.contains(Initializer.KING_INDEX));
	}

	// Is the Merchant interesting for this player? It can be if he has 3 or more
	// Trade and Handicrafts districts built in his city
	private boolean canBeMerchant(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<District> cityOfPlayer = cityForPredictWhoIsPlayer(player);

		int counter = 0;
		for (District district : cityOfPlayer) {
			if (district.getNameOfFamily().equals("Trade and Handicrafts"))
				counter++;
		}

		return (counter >= 3 && listOfTargetableCharacter.contains(Initializer.MERCHANT_INDEX));
	}

	// Is the Thief interesting for this player? It can be if he does not have a lot
	// of golds
	private boolean canBeThief(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		int goldsOfPlayer = goldsForPredictWhoIsPlayer(player);

		return (goldsOfPlayer <= 3 && listOfTargetableCharacter.contains(Initializer.THIEF_INDEX));
	}

	// Is the Magician interesting for this player? It can be if he does not have a
	// lot of district cards
	private boolean canBeMagician(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<District> handOfPlayer = handForPredictWhoIsPlayer(player);

		return (handOfPlayer.size() <= 2 && listOfTargetableCharacter.contains(Initializer.MAGICIAN_INDEX));
	}

	// Is the Warlord interesting for this player? It can be if someone is close to
	// finish the game (has 7 districts)
	private boolean canBeWarlord(Player player, ArrayList<Character> listOfUntargetableCharacter) {
		ArrayList<Character> listOfTargetableCharacter = targetableCharactersForPredictWhoIsPlayer(
				listOfUntargetableCharacter);
		ArrayList<Player> listOfPlayers = playersForPredictWhoIsPlayer(player);

		for (Player otherPlayer : listOfPlayers)
			if (otherPlayer.getCity().getBuiltDistrict().size() == 7 && listOfTargetableCharacter.contains(Initializer.WARLORD_INDEX))
				return true;

		return false;
	}

	Character listGetCharacter(int indexOfCharacter) {
		ArrayList<Character> listOfCharacter = board.getListOfCharacter();

		return listOfCharacter.get(indexOfCharacter);
	}

}
