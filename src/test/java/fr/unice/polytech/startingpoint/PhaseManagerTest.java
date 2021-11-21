package fr.unice.polytech.startingpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PhaseManagerTest {
	private ArrayList<City> listCity;
	private PhaseManager phaseMananager;
	City endedCity;
	City endPhaseCity;
	
	@BeforeEach
	public void init() {
		listCity = new ArrayList<City>();
		phaseMananager = new PhaseManager();
		
		for(int i = 0 ; i < 5 ; i++) {
			City c = new City();
			listCity.add(new City());
		}
		
		endedCity=Mockito.mock(City.class);
		endPhaseCity=Mockito.mock(City.class);
		
		when(endedCity.isComplete()).thenReturn(true);
		when(endPhaseCity.isComplete()).thenReturn(false);

		when(endPhaseCity.getSizeOfCity()).thenReturn(6);




	}
	
	@Test
	public void analyseGameLastTurnTest() {
		listCity.set(0, endedCity);
		String phase = phaseMananager.analyseGame(listCity);
		assertEquals(phase, PhaseManager.LAST_TURN_PHASE);
	}
	
	@Test
	public void analyseGameEndGameTest() {
		listCity.set(0, endPhaseCity);
		String phase = phaseMananager.analyseGame(listCity);
		assertEquals(phase, PhaseManager.END_GAME_PHASE);
	}
	
}
