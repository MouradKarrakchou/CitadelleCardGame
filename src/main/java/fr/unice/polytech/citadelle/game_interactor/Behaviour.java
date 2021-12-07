package fr.unice.polytech.citadelle.game_interactor;

import java.nio.file.DirectoryStream;
import java.util.*;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.purple_districts.DragonGate;
import fr.unice.polytech.citadelle.game_engine.PhaseManager;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 * A Behaviour realize all the action of a player.
 * 
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Behaviour {
	// The player controlled by the Behaviour.
	protected final Player player;
	protected final PrintCitadels printC = new PrintCitadels();
	protected Boolean botIsKing = false;
	protected int numberOfCharacter = 8;

	// ---
	CityManagement cityMan;
	Executor executor;
	Board board;

	protected static final int ZERO_CARD = 0;
	protected static final int ONE_CARD = 1;
	protected static final int TWO_CARD = 2;

	public Behaviour(Player player, Board board) {
		this.player = player;
		cityMan = new CityManagement(player);
		executor = new Executor(player);
		this.board = board;
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

	public boolean play(String currentPhase, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters) {
		printC.dropALine();
		this.getPlayer().getCharacter().spellOfTurn(this, hashOfCharacters, printC);
		if (currentPhase == PhaseManager.END_GAME_PHASE && player.getCity().getSizeOfCity() < 6)
			endGameBehaviour();
		else if (currentPhase == PhaseManager.LAST_TURN_PHASE)
			lastTurnBehaviour();
		else
			normalBehaviour();
		return (player.getCity().isComplete());

	}

	public void normalBehaviour() {
	};

	public void endGameBehaviour() {
	};

	public void lastTurnBehaviour() {
	};

	/**
	 * 
	 * Je comprend pas l'interêt, si on veut voler la carte de l'assasin pk on
	 * retourne pas le character assasin direct ???
	 */
	public Character selectCharacterForSpell(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters) {
		int i = randomInt(numberOfCharacter - 1);
		Character character = (Character) hashOfCharacters.keySet().toArray()[i];
		List<Character> list = hashOfCharacters.keySet().stream().toList();
		if (this.player.getCharacter().getName().equals("Thief")) {
			while (list.get(i).getName().equals(this.player.getCharacter().getName())
					|| list.get(i).getName().equals("Assassin") || character.isCharacterIsAlive() == false) {
				i = randomInt(numberOfCharacter - 1);
				character = (Character) hashOfCharacters.keySet().toArray()[i];
			}
		}

		while (hashOfCharacters.keySet().stream().toList().get(i).getName()
				.equals(this.player.getCharacter().getName())) {
			i = randomInt(numberOfCharacter - 1);
			character = (Character) hashOfCharacters.keySet().toArray()[i];
		}

		return (character);
	}

	public void ifPossibleBuildADistrict() {
		ArrayList<District> districtWeCanBuild = cityMan.listOfDistrictBuildable();
		if (!districtWeCanBuild.isEmpty()) {
			Collections.sort(districtWeCanBuild);
			Collections.reverse(districtWeCanBuild);
			District district = districtWeCanBuild.get(0);
			executor.buildDistrict(district);
		}
	}

	public ArrayList<District> pick2CardsIntoTheDeck() {
		ArrayList<District> pickedCards = executor.pickCards(board.getDeckDistrict());
		return pickedCards;
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
				executor.putCardBackInDeck(board.getDeckDistrict(), currentDistrictCard);
				removeDistrictCards.add(pickedDistrictCards.get(i));
			}
		}
		pickedDistrictCards.removeAll(removeDistrictCards);
		return pickedDistrictCards;
	}

	public void takeCard(District districtCard) {
		executor.takeCard(districtCard, board.getDeckDistrict());
	}

	public void takeGold() {
		executor.takeGold();
	}

	public void buildDistrict(District district) {
		executor.buildDistrict(district);
	}

	public District pickCardsInDeck() {
		ArrayList<District> pickedCards = new ArrayList<>();
		ArrayList<District> possibleCards = new ArrayList<>();
		District choosenDistrictCard = null; // bof

		pickedCards = pick2CardsIntoTheDeck();
		possibleCards = chooseToKeepOrNotPickedCards((ArrayList<District>) pickedCards.clone());

		switch (possibleCards.size()) {
		case ONE_CARD:
			choosenDistrictCard = possibleCards.get(0);
			break;
		case TWO_CARD:
			choosenDistrictCard = chooseBetweenTwoCards(possibleCards.get(0), possibleCards.get(1));
			break;
		case ZERO_CARD:
			choosenDistrictCard = chooseBetweenTwoCards(pickedCards.get(0), pickedCards.get(1));
			break;
		}
		return choosenDistrictCard;
	}

	public District chooseBetweenTwoCards(District district, District district1) {
		return null;
	}

	public int randomInt(int scope) {
		Random random = new Random();
		return (random.nextInt(scope));
	}

	public void setBehaviourIsKing(Boolean BehaviourIsKing) {
		this.botIsKing = BehaviourIsKing;
	}

	public void setCharacterIsAlive(Boolean characterIsAlive) {
		player.getCharacter().setCharacterIsAlive(characterIsAlive);
	}

	public Player getPlayer() {
		return player;
	}

	public Boolean getBehaviourIsKing() {
		return botIsKing;
	}

	public CityManagement getCityManager() {
		return cityMan;
	}

	public Executor getExecutor() {
		return executor;
	}

}