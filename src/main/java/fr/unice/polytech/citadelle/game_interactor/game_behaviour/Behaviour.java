package fr.unice.polytech.citadelle.game_interactor.game_behaviour;

import java.util.*;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.purple_districts.HauntedCity;
import fr.unice.polytech.citadelle.game.purple_districts.SchoolOfMagic;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_interactor.CityManagement;
import fr.unice.polytech.citadelle.game_interactor.Executor;
import fr.unice.polytech.citadelle.game_interactor.PhaseManager;
import fr.unice.polytech.citadelle.game_interactor.Strategy;

/**
 * A Behaviour realize all the action of a player.
 * 
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Behaviour {
	// The player controlled by the Behaviour.
	protected final Player player;
	protected int numberOfCharacter = 8;
	Strategy strategy;

	CityManagement cityMan;
	Executor executor;
	PhaseManager phaseManager;
	Board board;

	protected static final int ZERO_CARD = 0;
	protected static final int ONE_CARD = 1;
	protected static final int TWO_CARD = 2;

	public Behaviour(Player player, Board board) {
		this.player = player;
		this.board = board;
		strategy = new Strategy(numberOfCharacter, board, player);
		cityMan = new CityManagement(player);
		executor = new Executor(player);
		phaseManager = new PhaseManager(player, board);
	}

	/**
	 * @param pickedDistricts The two picked cards.
	 * @return The district having the higher value.
	 */
	public District selectTheHigherDistrict(ArrayList<District> pickedDistricts) {
		District cardOne = pickedDistricts.get(0);
		District cardTwo = pickedDistricts.get(1);

		if (cardOne.getValue() >= cardTwo.getValue()) {
			return cardOne;
		}
		return cardTwo;
	}

	/**
	 * @param pickedDistricts The two picked cards.
	 * @return The district having the lower value.
	 */
	public District selectTheLowerDistrict(ArrayList<District> pickedDistricts) {
		DeckDistrict deckDistrict = board.getDeckDistrict();
		int cardOneValue = pickedDistricts.get(0).getValue();
		int cardTwoValue = pickedDistricts.get(1).getValue();

		if (cardOneValue < cardTwoValue) {
			deckDistrict.addDistrict(pickedDistricts.get(1));
			return pickedDistricts.get(0);
		}
		deckDistrict.addDistrict(pickedDistricts.get(0));
		return pickedDistricts.get(1);
	}

	public Character play(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters) {
		executeSpellOfCharacter(this, hashOfCharacters);
		executeSpellOfPurpleDistricts(this.getPlayer());


		switch (phaseManager.analyseGame()) {
		case PhaseManager.MID_GAME_PHASE -> normalBehaviour();
		case PhaseManager.END_GAME_PHASE -> lastTurnBehaviour();
		case PhaseManager.LAST_TURN_PHASE -> endGameBehaviour();
		}

		buildArchitect();
		return player.getCharacter();
	}

	

	private void executeSpellOfCharacter(Behaviour behaviour,LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters) {
		getPlayer().getCharacter().spellOfTurn(this, hashOfCharacters);
	}
	
	private void executeSpellOfPurpleDistricts(Player player2) {
		this.getPlayer().getCity().getBuiltDistrict().stream().
													filter(district -> district.getName().equals("School of Magic")).
													forEach(district -> {
														ColorDistrict schoolOfMagic = (SchoolOfMagic) district;
														schoolOfMagic.schoolOfMagicSpell(this.getPlayer());
													});
	}

	public void buildArchitect() {
		if (player.getCharacter().getName().equals("Architect")) {
			ifPossibleBuildADistrict();
			ifPossibleBuildADistrict();
		}
	}

	public void normalBehaviour() {
	};

	public void endGameBehaviour() {
	};

	public void lastTurnBehaviour() {
	};

	public Character selectCharacterForSpell(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters) {
		int i = randomInt(numberOfCharacter - 1);
		Character character = (Character) hashOfCharacters.keySet().toArray()[i];
		List<Character> list = hashOfCharacters.keySet().stream().toList();
		switch (this.player.getCharacter().getName()) {
		case "Thief" -> character = chooseCharacterForThief(hashOfCharacters);
		case "Assassin" -> character = chooseCharacterForAssassin(hashOfCharacters);
		case "Magician" -> character = chooseCharacterForMagician(hashOfCharacters);
		}
		return (character);
	}

	public Player selectPlayerForWarlord() {
		return(strategy.choosePlayerForWarlordAdvanced());
	}

	private Character chooseCharacterForMagician(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters) {
		return (strategy.chooseCharacterForMagicianRandom(hashOfCharacters));
	}

	private Character chooseCharacterForAssassin(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters) {
		return (strategy.chooseCharacterForAssassinAdvanced());
	}

	private Character chooseCharacterForThief(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters) {
		return (strategy.chooseCharacterForThiefAdvanced());
	}

	public void ifPossibleBuildADistrict() {
		ArrayList<HauntedCity> hauntedCityArrayList = new ArrayList<>();
		ArrayList<District> districtWeCanBuild = cityMan.listOfDistrictBuildable();
		if (!districtWeCanBuild.isEmpty()) {
			Collections.sort(districtWeCanBuild);
			Collections.reverse(districtWeCanBuild);
			District district = districtWeCanBuild.get(0);
			if (district.isA("Haunted City")) {
				HauntedCity hauntedCity = (HauntedCity) district;
				hauntedCity.setRoundBuilt(board.getRoundNumber());
			}
			executor.buildDistrict(district);
		}
	}

	public ArrayList<District> pick2CardsIntoTheDeck() {
		return executor.pickCards(board.getDeckDistrict());
	}

	/*
	 * For the two cards chosen look if they are present in the city or the hand, if
	 * yes we discard the card
	 */
	public ArrayList<District> chooseToKeepOrNotPickedCards(ArrayList<District> pickedDistrictCards) {
		ArrayList<District> removeDistrictCards = new ArrayList<District>();
		for (int i = 0; i < 2; i++) {
			District currentDistrictCard = pickedDistrictCards.get(i);
			if (cityMan.isAlreadyBuilt(currentDistrictCard.getName()) || player.hasDistrict(currentDistrictCard)) {
				removeDistrictCards.add(pickedDistrictCards.get(i));
			}
		}
		pickedDistrictCards.removeAll(removeDistrictCards);
		return pickedDistrictCards;
	}

	public void takeCard(District districtCard) {
		executor.takeCard(districtCard, board.getDeckDistrict());
	}

	/**
	 * Add two gold to the player.
	 */
	public void takeGold() {
		executor.takeGold();
	}

	/**
	 * Add a district to the hand of the player.
	 */
	public void addDistrict(District district) {
		executor.addDistrict(district);
	}

	/**
	 * Build a district.
	 */
	public void buildDistrict(District district) {
		executor.buildDistrict(district);
	}

	/**
	 * Pick a districtCard into the deck.
	 * 
	 * @return a pickCardAction, that will be print with the printer
	 */
	public District pickCard() {
		return (executor.pickCard(board.getDeckDistrict()));
	}

	public District pickCardsInDeck() {
		ArrayList<District> pickedCards;
		ArrayList<District> possibleCards;
		District choosenDistrictCard = null; // bof

		pickedCards = pick2CardsIntoTheDeck();
		possibleCards = chooseToKeepOrNotPickedCards((ArrayList<District>) pickedCards.clone());

		switch (possibleCards.size()) {
		case ONE_CARD -> {
			choosenDistrictCard = possibleCards.get(0);
			removeOtherCard(pickedCards, possibleCards.get(0));
		}
		case TWO_CARD -> choosenDistrictCard = chooseBetweenTwoCards(possibleCards.get(0), possibleCards.get(1));
		case ZERO_CARD -> choosenDistrictCard = chooseBetweenTwoCards(pickedCards.get(0), pickedCards.get(1));
		}
		return choosenDistrictCard;
	}

	void removeOtherCard(ArrayList<District> pickedCards, District district) {
		if (pickedCards.get(0) == district)
			executor.putCardBackInDeck(board.getDeckDistrict(), pickedCards.get(0));
		else
			executor.putCardBackInDeck(board.getDeckDistrict(), pickedCards.get(1));
	}

	public District chooseBetweenTwoCards(District district, District district1) {
		return null;
	}

	public int randomInt(int scope) {
		Random random = new Random();
		return (random.nextInt(scope));
	}

	public void setCharacterIsAlive(Boolean characterIsAlive) {
		player.getCharacter().setCharacterIsAlive(characterIsAlive);
	}

	public Player getPlayer() {
		return player;
	}

	public Boolean getBehaviourIsKing() {
		return player.getCharacter().getName().equals("King");
	}

	public CityManagement getCityManager() {
		return cityMan;
	}

	public Executor getExecutor() {
		return executor;
	}

	public ArrayList<District> chooseMagicianAction() {
		// return an empty array if he wants to swap Cards with another Character
		// return the position of the Cards that he wants to swap
		return (strategy.chooseMagicianActionForRandom());
		// (for now he always chooses to steal from another Character)
	}

	public District chooseDistrictToDestroy(Player playerToDestroy) {
		return (strategy.chooseDistrictToDestroy(playerToDestroy));
	}

}
