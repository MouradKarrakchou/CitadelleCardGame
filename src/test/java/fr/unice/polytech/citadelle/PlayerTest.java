package fr.unice.polytech.citadelle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.City;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

public class PlayerTest {
    //Initialization
    Player player = new Player("robot1");
    City city = new City();

    @BeforeEach
    void beforeEach(){
        player.districtCards.add(new District("Haunted City", 2));
        player.districtCards.add(new District("Prison", 2));
        player.districtCards.add(new District("Manor", 3));
        player.districtCards.add(new District("Castle", 4));
        player.districtCards.add(new District("Laboratory", 5));
        player.districtCards.add(new District("Observatory", 5));
        player.districtCards.add(new District("University", 6));
    }

    @Test
    void test(){

        //public ArrayList<District> districtWeCanBuild(ArrayList<District> districtCheapEnough)

        //public void buildDistrict(District districtToBuild)
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

    /*
    @Test
    void isAlreadyBuiltTest(){
        city.builtDistrict.add(new District("Haunted City", 2));
        city.builtDistrict.stream().forEach(dis -> System.out.println(dis.getName()));
        assertTrue(player.isAlreadyBuilt("Haunted City"));
        assertFalse(player.isAlreadyBuilt("Prison"));

    }
    //To do : the case where there is no district in the city
    */

}
