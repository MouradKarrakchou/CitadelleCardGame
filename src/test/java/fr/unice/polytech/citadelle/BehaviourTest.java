package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Architect;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.game_interactor.NormalBot;

public class BehaviourTest {
	Player playerTest;
	Behaviour bea;
	DeckDistrict deckDistrict;
	Board board;

	@BeforeEach
	public void init() {
		deckDistrict = new DeckDistrict();
		deckDistrict.initialise();
    	board = new Board(new ArrayList<Player>(),new ArrayList<Character>(), deckDistrict, new DeckCharacter());
		playerTest = new Player("playerTest");
		bea = new Behaviour(playerTest, board);
		
	}

	@Test
	public void selectTheHigherDistrictTest() {
		ArrayList<District> pickedDistricts = new ArrayList<District>();

		District highterDistrict = new District("HighterDistrictTest", 10, "colorTest", "familyTest");
		District cheaperDistrict = new District("cheaperDistrictTest", 5, "colorTest", "familyTest");

		pickedDistricts.add(highterDistrict);
		pickedDistricts.add(cheaperDistrict);
		District choosenDistrict = bea.selectTheHigherDistrict(pickedDistricts);
		assertEquals(choosenDistrict, highterDistrict);
	}

	@Test
	public void ifPossibleBuildADistrictCouldBuildTest() {
		District aDistrict = new District("districtTest", 2, "colorTest", "familyTest");

		playerTest.setGolds(10);
		playerTest.getDistrictCards().clear();
		playerTest.addDistrict(aDistrict);

		assertEquals(playerTest.getCity().getSizeOfCity(), 0);
		bea.ifPossibleBuildADistrict();
		assertEquals(playerTest.getCity().getSizeOfCity(), 1);
		assertEquals(playerTest.getCity().getBuiltDistrict().get(0), aDistrict);
	}
	
	


	@Test
	public void chooseToKeepOrNotPickedCardskeepAllTest() {
		ArrayList<District> pickedDistrictCards = new ArrayList<District>();
		ArrayList<District> keepedDistrictCards = new ArrayList<District>();

		District aDistrict = new District("aDistrict", 10, "colorTest", "familyTest");
		District aOtherDistrict = new District("aOtherDistrict", 5, "colorTest", "familyTest");

		pickedDistrictCards.add(aDistrict);
		pickedDistrictCards.add(aOtherDistrict);

		playerTest.getDistrictCards().clear();
		playerTest.getCity().getBuiltDistrict().clear();

		assertEquals(playerTest.getDistrictCards().size(), 0);
		assertEquals(playerTest.getCity().getSizeOfCity(), 0);

		keepedDistrictCards = bea.chooseToKeepOrNotPickedCards(pickedDistrictCards);

		assertEquals(keepedDistrictCards.size(), 2);
	}

	@Test
	public void chooseToKeepOrNotPickedCardsKeepFirstBecause2ndInHandCardTest() {
		ArrayList<District> pickedDistrictCards = new ArrayList<District>();
		ArrayList<District> keepedDistrictCards = new ArrayList<District>();

		District futurKeepDistrict = new District("futurKeepDistrict", 10, "colorTest", "familyTest");
		District futurBurnDistrict = new District("futurBurnDistrict", 5, "colorTest", "familyTest");

		pickedDistrictCards.add(futurKeepDistrict);
		pickedDistrictCards.add(futurBurnDistrict);

		playerTest.getDistrictCards().clear();
		playerTest.getCity().getBuiltDistrict().clear();

		playerTest.getDistrictCards().add(futurBurnDistrict);

		assertEquals(playerTest.getDistrictCards().size(), 1);
		assertEquals(playerTest.getCity().getSizeOfCity(), 0);

		keepedDistrictCards = bea.chooseToKeepOrNotPickedCards(pickedDistrictCards);

		assertEquals(keepedDistrictCards.size(), 1);
		assertEquals(keepedDistrictCards.get(0), futurKeepDistrict);

	}

	@Test
	public void chooseToKeepOrNotPickedCardsKeepFirstBecause2ndInCityCardTest() {
		ArrayList<District> pickedDistrictCards = new ArrayList<District>();
		ArrayList<District> keepedDistrictCards = new ArrayList<District>();

		District futurKeepDistrict = new District("futurKeepDistrict", 10, "colorTest", "familyTest");
		District futurBurnDistrict = new District("futurBurnDistrict", 5, "colorTest", "familyTest");

		pickedDistrictCards.add(futurKeepDistrict);
		pickedDistrictCards.add(futurBurnDistrict);

		playerTest.getDistrictCards().clear();
		playerTest.getCity().getBuiltDistrict().clear();

		playerTest.getCity().getBuiltDistrict().add(futurBurnDistrict);

		assertEquals(playerTest.getDistrictCards().size(), 0);
		assertEquals(playerTest.getCity().getSizeOfCity(), 1);

		keepedDistrictCards = bea.chooseToKeepOrNotPickedCards(pickedDistrictCards);

		assertEquals(keepedDistrictCards.size(), 1);
		assertEquals(keepedDistrictCards.get(0), futurKeepDistrict);

	}

	@Test
	public void chooseToKeepOrNotPickedCardsNoKeepTest() {
		ArrayList<District> pickedDistrictCards = new ArrayList<District>();
		ArrayList<District> keepedDistrictCards = new ArrayList<District>();

		District otherFuturBurnDistrict = new District("futurKeepDistrict", 10, "colorTest", "familyTest");
		District futurBurnDistrict = new District("futurBurnDistrict", 5, "colorTest", "familyTest");

		pickedDistrictCards.add(otherFuturBurnDistrict);
		pickedDistrictCards.add(futurBurnDistrict);

		playerTest.getDistrictCards().clear();
		playerTest.getCity().getBuiltDistrict().clear();

		playerTest.getCity().getBuiltDistrict().add(futurBurnDistrict);
		playerTest.getCity().getBuiltDistrict().add(otherFuturBurnDistrict);

		assertEquals(playerTest.getDistrictCards().size(), 0);
		assertEquals(playerTest.getCity().getSizeOfCity(), 2);

		keepedDistrictCards = bea.chooseToKeepOrNotPickedCards(pickedDistrictCards);

		assertEquals(keepedDistrictCards.size(), 0);

	} 
	
	@Test
	public void takeGoldTest() {
		playerTest.setGolds(0);
		assertEquals(playerTest.getGolds(), 0);
		bea.takeGold();
		assertEquals(playerTest.getGolds(), 2);
	}
	
	@Test
	public void takeCardTest() {
		District choosenCard = deckDistrict.getDeckDistrict().get(0);
		playerTest.getDistrictCards().clear();;
		assertEquals(playerTest.getDistrictCards().size(), 0);
		bea.takeCard(choosenCard);
		assertEquals(playerTest.getDistrictCards().size(), 1);
		assertEquals(playerTest.getDistrictCards().get(0), choosenCard);
	}
	
	@Test
	public void architectBuilds3Districts(){
		Behaviour botArchitect=new Behaviour(new Player("playerArchitect"),board);
		botArchitect.getPlayer().setRole(new Architect());
		botArchitect.getPlayer().setGolds(999);


		ArrayList<District> districtsToBuild=new ArrayList<>();
		districtsToBuild.add(new District("Test1",1,"Yellow","Trade and Handicrafts"));
		districtsToBuild.add(new District("Test2",1,"Yellow","Trade and Handicrafts"));
		districtsToBuild.add(new District("Test3",1,"Yellow","Trade and Handicrafts"));
		botArchitect.getPlayer().getDistrictCards().addAll(districtsToBuild);

		botArchitect.buildArchitect();
		assertEquals(2,botArchitect.getPlayer().getCity().getSizeOfCity());
	}
	@Test
    public void pickCardsInDeckTest() {
		System.out.println("-------aaaaaaaaaaaaa------");
		DeckDistrict theDeckDistrict = new DeckDistrict();
		theDeckDistrict.initialise();
        ArrayList<District> pickedCard = new ArrayList<District>();
        District districtA = new District("distraaaaaaaictA", 1, "districtColor", "districtFamily");
        District districtB = new District("distraaaaaaaictB", 1, "districtColor", "districtFamily");
        pickedCard.add(districtA);
        pickedCard.add(districtB);
        
        Board aBoard = new Board(new ArrayList<Player>(),new ArrayList<Character>(), theDeckDistrict, new DeckCharacter());

        NormalBot aaaaaaa = spy(new NormalBot(new Player("players"), aBoard));
        aaaaaaa.getPlayer().addDistrict(districtA);
        aaaaaaa.getPlayer().addDistrict(districtB);

        when(aaaaaaa.pick2CardsIntoTheDeck()).thenReturn(pickedCard);
        //doReturn(pickedCard).when(bea).getName());


        aaaaaaa.pickCardsInDeck();
        verify(aaaaaaa, times(1)).chooseBetweenTwoCards(Mockito.any(), Mockito.any()); 
    }
}
