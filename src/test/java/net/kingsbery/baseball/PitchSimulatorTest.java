package net.kingsbery.baseball;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class PitchSimulatorTest {

	@Test
	public void sumsToOne(){
		PitchSimulator simulator = new PitchSimulator();
		Map<PitchOutcome, Double> probs = simulator.getProbabilities();
		double total = 0.0;
		for (double x : probs.values()) {
			total += x;
		}
		assertEquals(1.0, total, 0.0000001);
	}
	
}
