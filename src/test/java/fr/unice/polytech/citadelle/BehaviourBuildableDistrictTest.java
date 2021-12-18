package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;

public class BehaviourBuildableDistrictTest {
    
	private Player player01;
    private Behaviour behaviourOfPlayer01;
	
    @BeforeEach
    void beforeEach(){
    	Board board = new Board();
    	player01 = new Player("Player01");
        behaviourOfPlayer01 = new Behaviour(player01, board);
        
        player01.getDistrictCards().clear();
        player01.getDistrictCards().add(new District("Haunted City", 2,"empty","empty"));
        player01.getDistrictCards().add(new District("Prison", 2,"empty","empty"));
        player01.getDistrictCards().add(new District("Manor", 3,"empty","empty"));
        player01.getDistrictCards().add(new District("Castle", 4,"empty","empty"));
        player01.getDistrictCards().add(new District("Laboratory", 5,"empty","empty"));
        player01.getDistrictCards().add(new District("Observatory", 5,"empty","empty"));
        player01.getDistrictCards().add(new District("University", 6,"empty","empty"));
        player01.getCity().builtDistrict.clear();   
    }

    @RepeatedTest(1)
    //With 2 golds
    void districtWeHaveEnoughMoneyToBuildWith2GoldsTest(){
        ArrayList<District> districts = new ArrayList<>();
        districts.add(new District("Haunted City", 2,"empty","empty"));
        districts.add(new District("Prison", 2,"empty","empty"));

        assertEquals(districts, behaviourOfPlayer01.getCityManager().districtWeHaveEnoughMoneyToBuild(behaviourOfPlayer01.getPlayer().getGolds()));
    }

    @RepeatedTest(1)
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

        assertEquals(districts, behaviourOfPlayer01.getCityManager().districtWeHaveEnoughMoneyToBuild(behaviourOfPlayer01.getPlayer().getGolds()));
    }

    @RepeatedTest(1)
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

        assertEquals(districts, behaviourOfPlayer01.getCityManager().districtWeHaveEnoughMoneyToBuild(behaviourOfPlayer01.getPlayer().getGolds()));
    }

    @RepeatedTest(1)
    void isAlreadyBuiltTest(){
        player01.getCity().builtDistrict.add(new District("Haunted City", 2,"empty","empty"));
        assertTrue(behaviourOfPlayer01.getCityManager().isAlreadyBuilt("Haunted City"));
        assertFalse(behaviourOfPlayer01.getCityManager().isAlreadyBuilt("Prison"));

    }

    @RepeatedTest(1)
    void isAlreadyBuiltEmptyTest(){
        assertFalse(behaviourOfPlayer01.getCityManager().isAlreadyBuilt("Prison"));
    }

    @RepeatedTest(1)
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

        assertEquals(districts, behaviourOfPlayer01.getCityManager().districtWeCanBuild(districtCheapEnough));
    }

    @RepeatedTest(1)
    void zeroDistrictWeCanBuildTest(){
    	player01.golds = 4;

        player01.getCity().builtDistrict.add(new District("Haunted City", 2,"empty","empty"));
        player01.getCity().builtDistrict.add(new District("Manor", 3,"empty","empty"));

        ArrayList<District> districtCheapEnough = new ArrayList<>();
        districtCheapEnough.add(new District("Haunted City", 2,"empty","empty"));
        districtCheapEnough.add(new District("Manor", 3,"empty","empty"));

        ArrayList<District> districts = new ArrayList<>();

        assertEquals(districts, behaviourOfPlayer01.getCityManager().districtWeCanBuild(districtCheapEnough));
    }
}
