package fr.unice.polytech.startingpoint;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ControllerTest {
	Controller controller;
	
	@BeforeEach
	public void init() {
		controller= new Controller();
		controller.initGame();
	}
		
	@Test
	public void startRoundPart2NormalUtilisationTest() {
		
		
		controller.startRoundPart2();
		//assertTrue();
	}
	
	
}
