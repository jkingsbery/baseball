package net.kingsbery.baseball.pitches;

import net.kingsbery.baseball.players.Player;

public interface PitchSimulatorFactory {
	PitchSimulator getSimulator(Player pitcher, Player hitter);
}
