package fr.unice.polytech.citadelle.bot;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.Player;

public class RushBot extends Bot{

	public RushBot(Player player) {
		super(player);
	}
	
	public void normalBehaviour(DeckDistrict deckDistrict) {
		if (player.getDistrictCardsSize() == 0 || player.districtWeCanBuild(player.getDistrictCards()).size() == 0)
			takeCard(deckDistrict);
		else {
			printC.printTakeGold(player);
			player.addGold();
		}
		ifPossibleBuildADistrict();
	}

	private void endGameBehaviour(DeckDistrict deckDistrict) {
		printC.printPhase("Endgame",player);
		takeCard(deckDistrict);
		ifPossibleBuildADistrict();
	}

	private void lastTurnBehaviour(DeckDistrict deckDistrict) {
		printC.printPhase("LAST TURN",player);
		takeCard(deckDistrict);
		ifPossibleBuildADistrict();
	}

}
