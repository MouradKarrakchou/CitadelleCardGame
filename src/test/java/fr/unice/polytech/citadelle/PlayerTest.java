package fr.unice.polytech.citadelle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.District;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

public class PlayerTest {
    //Initialization
    Player player = new Player("robot1");

    @BeforeEach
    void beforeEach(){
        player.districtCards.clear();
        player.districtCards.add(new District("Haunted City", 2));
        player.districtCards.add(new District("Prison", 2));
        player.districtCards.add(new District("Manor", 3));
        player.districtCards.add(new District("Castle", 4));
        player.districtCards.add(new District("Laboratory", 5));
        player.districtCards.add(new District("Observatory", 5));
        player.districtCards.add(new District("University", 6));

        player.getCity().builtDistrict.clear();
    }

    @Test
    //With 2 golds
    void districtWeHaveEnoughMoneyToBuildWith2GoldsTest(){
        ArrayList<District> districts = new ArrayList<>();
        districts.add(new District("Haunted City", 2));
        districts.add(new District("Prison", 2));

        assertEquals(districts, player.districtWeHaveEnoughMoneyToBuild());
    }

    @Test
    //With 5 golds
    void districtWeHaveEnoughMoneyToBuildWith5goldsTest(){
        player.golds = 5;

        ArrayList<District> districts = new ArrayList<>();
        districts.add(new District("Haunted City", 2));
        districts.add(new District("Prison", 2));
        districts.add(new District("Manor", 3));
        districts.add(new District("Castle", 4));
        districts.add(new District("Laboratory", 5));
        districts.add(new District("Observatory", 5));

        assertEquals(districts, player.districtWeHaveEnoughMoneyToBuild());
    }

    @Test
    //With 6 golds
    void districtWeHaveEnoughMoneyToBuildWith6goldsTest(){
        player.golds = 6;

        ArrayList<District> districts = new ArrayList<>();
        districts.add(new District("Haunted City", 2));
        districts.add(new District("Prison", 2));
        districts.add(new District("Manor", 3));
        districts.add(new District("Castle", 4));
        districts.add(new District("Laboratory", 5));
        districts.add(new District("Observatory", 5));
        districts.add(new District("University", 6));

        assertEquals(districts, player.districtWeHaveEnoughMoneyToBuild());
    }


    @Test
    void isAlreadyBuiltTest(){
        player.getCity().builtDistrict.add(new District("Haunted City", 2));
        assertTrue(player.isAlreadyBuilt("Haunted City"));
        assertFalse(player.isAlreadyBuilt("Prison"));

    }

    @Test
    void isAlreadyBuiltEmptyTest(){
        assertFalse(player.isAlreadyBuilt("Prison"));
    }

    @Test
    void districtWeCanBuildTest(){
        player.golds = 4;

        player.getCity().builtDistrict.add(new District("Haunted City", 2));
        player.getCity().builtDistrict.add(new District("Manor", 3));

        ArrayList<District> districtCheapEnough = new ArrayList<>();
        districtCheapEnough.add(new District("Haunted City", 2));
        districtCheapEnough.add(new District("Prison", 2));
        districtCheapEnough.add(new District("Manor", 3));
        districtCheapEnough.add(new District("Castle", 4));

        ArrayList<District> districts = new ArrayList<>();
        districts.add(new District("Prison", 2));
        districts.add(new District("Castle", 4));

        assertEquals(districts, player.districtWeCanBuild(districtCheapEnough));
    }

    @Test
    void zeroDistrictWeCanBuildTest(){
        player.golds = 4;

        player.getCity().builtDistrict.add(new District("Haunted City", 2));
        player.getCity().builtDistrict.add(new District("Manor", 3));

        ArrayList<District> districtCheapEnough = new ArrayList<>();
        districtCheapEnough.add(new District("Haunted City", 2));
        districtCheapEnough.add(new District("Manor", 3));

        ArrayList<District> districts = new ArrayList<>();

        assertEquals(districts, player.districtWeCanBuild(districtCheapEnough));
    }

    @Test
    void buildDistrictTest(){
        player.golds = 4;

        ArrayList<District> districts = new ArrayList<>();
        districts.add(new District("Manor", 3));

        ArrayList<District> districts1 = new ArrayList<>();
        districts1.add(new District("Haunted City", 2));
        districts1.add(new District("Prison", 2));
        districts1.add(new District("Castle", 4));
        districts1.add(new District("Laboratory", 5));
        districts1.add(new District("Observatory", 5));
        districts1.add(new District("University", 6));

        player.buildDistrict(new District("Manor", 3));

        assertEquals(districts, player.getCity().builtDistrict);
        assertEquals(districts1, player.getDistrictCards());
        assertEquals(1, player.getGolds());
        assertEquals(3, player.getScore());
    }

}
