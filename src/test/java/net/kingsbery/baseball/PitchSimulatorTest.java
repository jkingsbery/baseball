package net.kingsbery.baseball;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import net.kingsbery.baseball.pitches.PitchOutcome;
import net.kingsbery.baseball.pitches.PitchSimulator;
import net.kingsbery.baseball.players.Player;

public class PitchSimulatorTest {

	@Test
	public void sumsToOne() {
		PitchSimulator simulator = new PitchSimulator(Player.buildPitcher(), Player.buildBatter());
		Map<PitchOutcome, Double> probs = simulator.getProbabilities();
		double total = 0.0;
		for (double x : probs.values()) {
			total += x;
		}
		assertEquals(1.0, total, 0.0000001);
	}

}
