package fr.unice.polytech.citadelle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.District;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;

public class PlayerTest {
    //Initialization
    Player player = new Player("robot1");

    @BeforeEach
    void beforeEach(){
        player.districtCards.clear();
        player.districtCards.add(new District("Haunted City", 2,"empty","empty"));
        player.districtCards.add(new District("Prison", 2,"empty","empty"));
        player.districtCards.add(new District("Manor", 3,"empty","empty"));
        player.districtCards.add(new District("Castle", 4,"empty","empty"));
        player.districtCards.add(new District("Laboratory", 5,"empty","empty"));
        player.districtCards.add(new District("Observatory", 5,"empty","empty"));
        player.districtCards.add(new District("University", 6,"empty","empty"));

        player.getCity().builtDistrict.clear();
    }


    @Test
    void buildDistrictTest(){
        player.golds = 4;

        ArrayList<District> districts = new ArrayList<>();
        districts.add(new District("Manor", 3,"empty","empty"));

        ArrayList<District> districts1 = new ArrayList<>();
        districts1.add(new District("Haunted City", 2,"empty","empty"));
        districts1.add(new District("Prison", 2,"empty","empty"));
        districts1.add(new District("Castle", 4,"empty","empty"));
        districts1.add(new District("Laboratory", 5,"empty","empty"));
        districts1.add(new District("Observatory", 5,"empty","empty"));
        districts1.add(new District("University", 6,"empty","empty"));

        player.buildDistrict(new District("Manor", 3,"empty","empty"));

        assertEquals(districts, player.getCity().builtDistrict);
        assertEquals(districts1, player.getDistrictCards());
        assertEquals(1, player.getGolds());
        assertEquals(3, player.getScore());
    }

}
