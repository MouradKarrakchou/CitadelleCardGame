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

	protected static final int ZERO_CARD = 0;
	protected static final int ONE_CARD = 1;
	protected static final int TWO_CARD = 2;

	public Behaviour(Player player) {
		this.player = player;
		cityMan = new CityManagement(player);
		executor = new Executor(player);
	}

	/**
	 * @param pickedDistricts The two picked cards.
	 * @return The district having the higher value.
	 */
	public District selectTheHigherDistrict(DeckDistrict deckDistrict, ArrayList<District> pickedDistricts) {
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
	public District selectTheLowerDistrict(DeckDistrict deckDistrict, ArrayList<District> pickedDistricts) {
		int cardOneValue = pickedDistricts.get(0).getValue();
		int cardTwoValue = pickedDistricts.get(1).getValue();

		if (cardOneValue < cardTwoValue) {
			deckDistrict.addDistrict(pickedDistricts.get(1));
			return pickedDistricts.get(0);
		}
		deckDistrict.addDistrict(pickedDistricts.get(0));
		return pickedDistricts.get(1);
	}

	public boolean play(DeckDistrict deckDistrict, String currentPhase,
			LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters) {
		printC.dropALine();
		this.getPlayer().getCharacter().spellOfTurn(this, hashOfCharacters, printC);
		if (currentPhase == PhaseManager.END_GAME_PHASE && player.getCity().getSizeOfCity() < 6)
			endGameBehaviour(deckDistrict);
		if (currentPhase == PhaseManager.LAST_TURN_PHASE)
			lastTurnBehaviour(deckDistrict);
		else
			normalBehaviour(deckDistrict);
		return (player.getCity().isComplete());

	}

	public void normalBehaviour(DeckDistrict deckDistrict) {
	};

	public void endGameBehaviour(DeckDistrict deckDistrict) {
	};

	public void lastTurnBehaviour(DeckDistrict deckDistrict) {
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

	public ArrayList<District> pick2CardsIntoTheDeck(DeckDistrict deckDistrict) {
		ArrayList<District> pickedCards = executor.pickCards(deckDistrict);
		return pickedCards;
	}

	/*
	 * For the two cards chosen look if they are present in the city or the hand, if
	 * yes we discard the card
	 */
	public ArrayList<District> chooseToKeepOrNotPickedCards(ArrayList<District> pickedDistrictCards,
			DeckDistrict deckDistrict) {
		ArrayList<District> removeDistrictCards = new ArrayList<District>();
		for (int i = 0; i < 2; i++) {
			District currentDistrictCard = pickedDistrictCards.get(i);
			if (cityMan.isAlreadyBuilt(currentDistrictCard.getName()) || player.hasDistrict(currentDistrictCard)) {
				executor.putCardBackInDeck(deckDistrict, currentDistrictCard);
				removeDistrictCards.add(pickedDistrictCards.get(i));
			}
		}
		pickedDistrictCards.removeAll(removeDistrictCards);
		return pickedDistrictCards;
	}

	public void takeCard(District districtCard, DeckDistrict deckDistrict) {
		executor.takeCard(districtCard, deckDistrict);
	}

	public void takeGold() {
		executor.takeGold();
	}

	public void buildDistrict(District district) {
		executor.buildDistrict(district);
	}

	public District pickCardsInDeck(DeckDistrict deckDistrict) {
		ArrayList<District> pickedCards = new ArrayList<>();
		ArrayList<District> possibleCards = new ArrayList<>();
		District choosenDistrictCard = null; // bof

		pickedCards = pick2CardsIntoTheDeck(deckDistrict);
		possibleCards = chooseToKeepOrNotPickedCards(pickedCards, deckDistrict);

		switch (possibleCards.size()) {
		case ONE_CARD:
			choosenDistrictCard = possibleCards.get(0);
			break;
		case TWO_CARD:
			choosenDistrictCard = chooseBetweenTwoCards(possibleCards.get(0), possibleCards.get(1), deckDistrict);
			break;
		case ZERO_CARD:
			choosenDistrictCard = chooseBetweenTwoCards(pickedCards.get(0), pickedCards.get(1), deckDistrict);
			break;
		}
		return choosenDistrictCard;
	}

	public District chooseBetweenTwoCards(District district, District district1, DeckDistrict deckDistrict) {
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
