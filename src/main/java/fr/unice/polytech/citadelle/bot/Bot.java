package fr.unice.polytech.citadelle.bot;

import java.util.*;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.PhaseManager;
import fr.unice.polytech.citadelle.output.PrintCitadels;
import fr.unice.polytech.citadelle.game.Character;

/**
 * A Bot realize all the action of a player.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Bot {
	// The player controlled by the bot.
	protected final Player player;
	protected final PrintCitadels printC = new PrintCitadels();
	protected Boolean botIsKing=false;
	protected int numberOfCharacter=8;

	public Bot(Player player) {
		this.player = player;
	}

	/**
	 * @param pickedDistricts The two picked cards.
	 * @return The district having the higher value.
	 */
	public District selectTheHigherDistrict(DeckDistrict deckDistrict, ArrayList<District> pickedDistricts) {
		int cardOneValue = pickedDistricts.get(0).getValue();
		int cardTwoValue = pickedDistricts.get(1).getValue();

		if (cardOneValue >= cardTwoValue) {
			deckDistrict.addDistrict(pickedDistricts.get(1));
			return pickedDistricts.get(0);
		}
		deckDistrict.addDistrict(pickedDistricts.get(0));
		return pickedDistricts.get(1);
	}

	/**
	 * @param deckDistrict    The global game deck of districts
	 * @param pickedDistricts The two picked cards.
	 * @return Whether a card has been picked and the other burned or not.
	 *
	 *         Criteria : 1 - The card must not already be in the player's city or
	 *         in the player's hand 2 - If there are still 2 cards after applying
	 *         the criteria (1), the card with the higher value is selected.
	 */
	public boolean districtCardIsSelected(DeckDistrict deckDistrict, ArrayList<District> pickedDistricts) {

		// Player will not pick cards it already have on its city or its hand.
		// Fonction modifié après les tests unitaire du 23.11.2021, il fautrai trouver une facon plus jolie de l'ecrire
		District districtOne = pickedDistricts.get(0);
		District districtTwo = pickedDistricts.get(1);
		if (isAlreadyBuilt(districtOne.getName()) || player.hasDistrict(districtOne))
			putCardBackInDeck(deckDistrict, pickedDistricts, districtOne);
		if (isAlreadyBuilt(districtTwo.getName()) || player.hasDistrict(districtTwo))
			putCardBackInDeck(deckDistrict, pickedDistricts, districtTwo);

		switch (pickedDistricts.size()) {
			// One district card have been selected, player will take this card.
			case 1 -> {
				printC.printTakeDistrictCard(player);// pas d'accord, le controller demande au printer de print les infos du
														// bot
				player.addDistrict(pickedDistricts.get(0));
				return true; 
			}

			// Two district cards have been selected, player will take the higher one.
			case 2 -> {
				printC.printTakeDistrictCard(player);
				District higherDistrict = selectTheHigherDistrict(deckDistrict, pickedDistricts);
				player.addDistrict(higherDistrict);
				return true;
			}

			default -> {
				return false;
			}
		}
	}

	public void takeGold() {
		printC.printTakeGold(player);
		player.addGold();
	}
	
	protected void buildDistrict(District district){
		player.buildDistrict(district);
		printC.printBuildDistrict(player, district);
		printC.printBoardOfPlayer(player);
	}


	public boolean play(DeckDistrict deckDistrict, String currentPhase, LinkedHashMap<Character, Optional<Bot>> hashOfCharacters) {
		printC.dropALine();
		if (player.getCharacter().isCharacterIsAlive()){
			this.getPlayer().getCharacter().spellOfTurn(this,hashOfCharacters,printC);
			if (currentPhase == PhaseManager.END_GAME_PHASE && player.getCity().getSizeOfCity() < 6)
				endGameBehaviour(deckDistrict);
			if (currentPhase == PhaseManager.LAST_TURN_PHASE)
				lastTurnBehaviour(deckDistrict);
			else
				normalBehaviour(deckDistrict);
		}
		else{
			printC.botIsDead(player);
		}
		return (player.getCity().isComplete());

	}

	public void normalBehaviour(DeckDistrict deckDistrict) {
		int goldOfPlayer = player.getGolds();
		if ( goldOfPlayer == 0 || districtWeHaveEnoughMoneyToBuild(goldOfPlayer+2).size() > 0)
			takeGold();
		else {
			takeCard(deckDistrict);
		}
		ifPossibleBuildADistrict();
	}

	public void endGameBehaviour(DeckDistrict deckDistrict) {
		printC.printPhase("Endgame",player);
		normalBehaviour(deckDistrict);
	}

	public void lastTurnBehaviour(DeckDistrict deckDistrict) {
		printC.printPhase("LAST TURN",player);
		normalBehaviour(deckDistrict);
	}

	protected void ifPossibleBuildADistrict() {
		ArrayList<District> districtWeCanBuild = listOfDistrictBuildable();
		if (!districtWeCanBuild.isEmpty()){
			Collections.sort(districtWeCanBuild);
			Collections.reverse(districtWeCanBuild);
			District district = districtWeCanBuild.get(0);
			buildDistrict(district);
		}
	}


	public void takeCard(DeckDistrict deckDistrict) {
		// Pick two district cards and add it to an ArrayList.
		ArrayList<District> pickedDistricts = new ArrayList<District>();
		pickedDistricts.add(deckDistrict.chooseDistrict());
		pickedDistricts.add(deckDistrict.chooseDistrict());

		// Allow player to take or not one of the 2 picked district cards
		boolean playerTakeADistrictCard = districtCardIsSelected(deckDistrict, pickedDistricts);

		// If player don't take any district cards
		if (!playerTakeADistrictCard) {
			takeGold();
		}
	}

	private void putCardBackInDeck(DeckDistrict deckDistrict, ArrayList <District> pickedDistricts, District districtCard) {
		pickedDistricts.remove(districtCard);
		deckDistrict.addDistrict(districtCard);
	}

	public Player getPlayer() {
		return player;
	}

	public Character selectCharacterForSpell(LinkedHashMap<Character, Optional<Bot>> hashOfCharacters){
		int i=randomInt(numberOfCharacter-1);
		Character character= (Character) hashOfCharacters.keySet().toArray()[i];
		List<Character> list=hashOfCharacters.keySet().stream().toList();
		if (this.player.getCharacter().getName().equals("Thief"))
		{
			while (list.get(i).getName().equals(this.player.getCharacter().getName()) || list.get(i).getName().equals("Assassin") || character.isCharacterIsAlive()==false) {
				i=randomInt(numberOfCharacter-1);
				character= (Character) hashOfCharacters.keySet().toArray()[i];}
		}

		while (hashOfCharacters.keySet().stream().toList().get(i).getName().equals(this.player.getCharacter().getName())) {
			i=randomInt(numberOfCharacter-1);
			character= (Character) hashOfCharacters.keySet().toArray()[i];}

		return(character);
	}

	public int randomInt(int scope){
		Random random=new Random();
		return(random.nextInt(scope));
	}

	public void setBotIsKing(Boolean botIsKing) {
		this.botIsKing = botIsKing;
	}

	public void setCharacterIsAlive(Boolean characterIsAlive) {
		player.getCharacter().setCharacterIsAlive(characterIsAlive);
	}


	public Boolean getBotIsKing() {
		return botIsKing;
	}
	
	
	protected ArrayList<District> getBuildableDistrictWithTwoMoreGold(){
		return districtWeCanBuild(districtWeHaveEnoughMoneyToBuild(player.getGolds() + 2));
	}
	
	public ArrayList<District> listOfDistrictBuildable() {
		return districtWeCanBuild(districtWeHaveEnoughMoneyToBuild(player.getGolds()));
	}
	
	public ArrayList<District> districtWeCanBuild(ArrayList<District> districtCheapEnough) {
		return districtCheapEnough.stream().filter(district -> !(isAlreadyBuilt(district.getName())))
				.collect(Collectors.toCollection(ArrayList::new));

	}
	
	public ArrayList<District> districtWeHaveEnoughMoneyToBuild(int gold) {
		return player.getDistrictCards().stream().filter(district -> district.getValue() <= gold)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public boolean isAlreadyBuilt(String nameOfDistrict) {
		ArrayList<District> districtIsBuilt;
		districtIsBuilt = player.getCity().getBuiltDistrict().stream()
				.filter(builtDistrict -> builtDistrict.getName().equals(nameOfDistrict))
				.collect(Collectors.toCollection(ArrayList::new));
		if (districtIsBuilt.size() != 1)
			return false;

		return true;
	}
}
