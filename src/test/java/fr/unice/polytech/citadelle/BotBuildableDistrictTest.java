package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;

public class BotBuildableDistrictTest {
    
	private Player player01;
    private Bot botOfPlayer01;
	
    @BeforeEach
    void beforeEach(){
    	player01 = new Player("Player01");
        botOfPlayer01 = new Bot(player01);
        
        player01.districtCards.clear();
        player01.districtCards.add(new District("Haunted City", 2,"empty","empty"));
        player01.districtCards.add(new District("Prison", 2,"empty","empty"));
        player01.districtCards.add(new District("Manor", 3,"empty","empty"));
        player01.districtCards.add(new District("Castle", 4,"empty","empty"));
        player01.districtCards.add(new District("Laboratory", 5,"empty","empty"));
        player01.districtCards.add(new District("Observatory", 5,"empty","empty"));
        player01.districtCards.add(new District("University", 6,"empty","empty"));
        player01.getCity().builtDistrict.clear();   
    }

    @Test
    //With 2 golds
    void districtWeHaveEnoughMoneyToBuildWith2GoldsTest(){
        ArrayList<District> districts = new ArrayList<>();
        districts.add(new District("Haunted City", 2,"empty","empty"));
        districts.add(new District("Prison", 2,"empty","empty"));

        assertEquals(districts, botOfPlayer01.districtWeHaveEnoughMoneyToBuild(botOfPlayer01.getPlayer().getGolds()));
    }

    @Test
    //With 5 golds
    void districtWeHaveEnoughMoneyToBuildWith5goldsTest(){
        player01.golds = 5;

        ArrayList<District> districts = new ArrayList<>();
        districts.add(new District("Haunted City", 2,"empty","empty"));
        districts.add(new District("Prison", 2,"empty","empty"));
        districts.add(new District("Manor", 3,"empty","empty"));
        districts.add(new District("Castle", 4,"empty","empty"));
        districts.add(new District("Laboratory", 5,"empty","empty"));
        districts.add(new District("Observatory", 5,"empty","empty"));

        assertEquals(districts, botOfPlayer01.districtWeHaveEnoughMoneyToBuild(botOfPlayer01.getPlayer().getGolds()));
    }

    @Test
    //With 6 golds
    void districtWeHaveEnoughMoneyToBuildWith6goldsTest(){
        player01.golds = 6;

        ArrayList<District> districts = new ArrayList<>();
        districts.add(new District("Haunted City", 2,"empty","empty"));
        districts.add(new District("Prison", 2,"empty","empty"));
        districts.add(new District("Manor", 3,"empty","empty"));
        districts.add(new District("Castle", 4,"empty","empty"));
        districts.add(new District("Laboratory", 5,"empty","empty"));
        districts.add(new District("Observatory", 5,"empty","empty"));
        districts.add(new District("University", 6,"empty","empty"));

        assertEquals(districts, botOfPlayer01.districtWeHaveEnoughMoneyToBuild(botOfPlayer01.getPlayer().getGolds()));
    }


    @Test
    void isAlreadyBuiltTest(){
        player01.getCity().builtDistrict.add(new District("Haunted City", 2,"empty","empty"));
        assertTrue(botOfPlayer01.isAlreadyBuilt("Haunted City"));
        assertFalse(botOfPlayer01.isAlreadyBuilt("Prison"));

    }

    @Test
    void isAlreadyBuiltEmptyTest(){
        assertFalse(botOfPlayer01.isAlreadyBuilt("Prison"));
    }

    @Test
    void districtWeCanBuildTest(){
        player01.golds = 4;

        player01.getCity().builtDistrict.add(new District("Haunted City", 2,"empty","empty"));
        player01.getCity().builtDistrict.add(new District("Manor", 3,"empty","empty"));

        ArrayList<District> districtCheapEnough = new ArrayList<>();
        districtCheapEnough.add(new District("Haunted City", 2,"empty","empty"));
        districtCheapEnough.add(new District("Prison", 2,"empty","empty"));
        districtCheapEnough.add(new District("Manor", 3,"empty","empty"));
        districtCheapEnough.add(new District("Castle", 4,"empty","empty"));

        ArrayList<District> districts = new ArrayList<>();
        districts.add(new District("Prison", 2,"empty","empty"));
        districts.add(new District("Castle", 4,"empty","empty"));

        assertEquals(districts, botOfPlayer01.districtWeCanBuild(districtCheapEnough));
    }

    @Test
    void zeroDistrictWeCanBuildTest(){
    	player01.golds = 4;

        player01.getCity().builtDistrict.add(new District("Haunted City", 2,"empty","empty"));
        player01.getCity().builtDistrict.add(new District("Manor", 3,"empty","empty"));

        ArrayList<District> districtCheapEnough = new ArrayList<>();
        districtCheapEnough.add(new District("Haunted City", 2,"empty","empty"));
        districtCheapEnough.add(new District("Manor", 3,"empty","empty"));

        ArrayList<District> districts = new ArrayList<>();

        assertEquals(districts, botOfPlayer01.districtWeCanBuild(districtCheapEnough));
    }
}
