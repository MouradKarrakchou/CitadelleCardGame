package fr.unice.polytech.citadelle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

public class DeckDistrictTest {

    //Names : "Haunted City", "Laboratory", "Smithy", "Observatory", "Graveyard", "Library", "University", "School of Magic", "Dragon Gate", "Cathedral", "Palace", "Town Hall", "Fortress", "Keep", "Temple", "Watchtower", "Trading Post", "Prison", "Monastery", "Docks", "Battlefield", "Harbor", "Church", "Market", "Castle", "Tavern", "Manor"
    DeckDistrict deck = new DeckDistrict();
    ArrayList<Integer> numberOfCards = new ArrayList<>();
    ArrayList<String> nameOfDistrict = new ArrayList<>();
    ArrayList<District> deckDistrictReal = new ArrayList<>();
    ArrayList<District> deckDistrictToTest = new ArrayList<>();

    @BeforeEach
    void beforeEach(){
        Collections.addAll(numberOfCards,3, 5, 3, 4, 3, 4, 3, 1, 3, 5, 3, 3, 2, 4, 3, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1);

        Collections.addAll(nameOfDistrict,"Temple", "Tavern", "Watchtower");
        nameOfDistrict.stream().forEach(name -> deckDistrictReal.add(new District(name, 1, "TestColor", "testFamily")));
        nameOfDistrict.clear();

        Collections.addAll(nameOfDistrict,"Church", "Trading Post", "Market", "Prison", "Haunted City");
        nameOfDistrict.stream().forEach(name -> deckDistrictReal.add(new District(name, 2, "TestColor", "testFamily")));
        nameOfDistrict.clear();

        Collections.addAll(nameOfDistrict,"Monastery", "Manor", "Docks", "Battlefield", "Keep");
        nameOfDistrict.stream().forEach(name -> deckDistrictReal.add(new District(name, 3,"TestColor", "testFamily")));
        nameOfDistrict.clear();

        Collections.addAll(nameOfDistrict,"Castle", "Harbor");
        nameOfDistrict.stream().forEach(name -> deckDistrictReal.add(new District(name, 4, "TestColor", "testFamily")));
        nameOfDistrict.clear();

        Collections.addAll(nameOfDistrict, "Cathedral", "Palace", "Town Hall", "Fortress", "Laboratory", "Smithy", "Observatory", "Graveyard");
        nameOfDistrict.stream().forEach(name -> deckDistrictReal.add(new District(name, 5, "TestColor", "testFamily")));
        nameOfDistrict.clear();

        Collections.addAll(nameOfDistrict,"Library", "School of Magic", "University", "Dragon Gate");
        nameOfDistrict.stream().forEach(name -> deckDistrictReal.add(new District(name, 6, "TestColor", "testFamily")));
        nameOfDistrict.clear();

        deck.initialise();
        deckDistrictToTest = deck.getDeckDistrict();
    }

    @RepeatedTest(100)
    void initializerTestName(){
        for (District districtReal : deckDistrictReal) {
            deckDistrictToTest.stream()
                              .filter(district -> district.getName().equals(districtReal.getName()))
                              .forEach(district -> assertTrue(district.getName().equals(districtReal.getName())));
        }
    }

    @RepeatedTest(100)
    void initializerTestQuantity(){
        for (int i = 0; i < deckDistrictReal.size(); i++) {
            int j = i;
            int number = deckDistrictToTest.stream()
                                           .filter(districtToTest -> districtToTest.getName().equals(deckDistrictReal.get(j).getName()))
                                           .map(districtToTest -> 1)
                                           .reduce(0, (total, count) -> total + count);
            assertEquals(numberOfCards.get(i), number);
        }
    }

    @RepeatedTest(100)
     void initializerTestValue(){
        for (District districtReal : deckDistrictReal) {
            deckDistrictToTest.stream()
                              .filter(districtToTest -> districtToTest.getName().equals(districtReal.getName()))
                              .forEach(districtToTest -> assertEquals(districtReal.getValue(), districtToTest.getValue()));
        }
    }
    @RepeatedTest(100)
    void putBackCardInDeck(){
        deck.getDeckDistrict().clear();
        deck.addDistrict(new District("district",1,null,null));
        deck.addDistrict(new District("district",1,null,null));
        deck.addDistrictAtTheEnd(new District("districtAtTheEnd",1,null,null));
        assertEquals(deck.getDeckDistrict().get(2).getName(),"districtAtTheEnd");
    }
}
