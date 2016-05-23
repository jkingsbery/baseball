package net.kingsbery.baseball.pitches;

import net.kingsbery.baseball.players.Player;

public class DefaultPitchSimulatorFactory implements PitchSimulatorFactory {

	@Override
	public PitchSimulator getSimulator(Player pitcher, Player hitter) {
		return new PitchSimulator(pitcher, hitter);
	}

}
