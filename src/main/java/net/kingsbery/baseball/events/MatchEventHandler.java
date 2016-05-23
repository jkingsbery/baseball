package net.kingsbery.baseball.events;

import net.kingsbery.baseball.BaseballMatch;
import net.kingsbery.baseball.pitches.PitchOutcome;

public interface MatchEventHandler {

	void matchStart(BaseballMatch match);

	void runScored(BaseballMatch baseballMatch);

	void nextInning(BaseballMatch baseballMatch, int runs);

	void strikeOut();

	void walk();

	void playResult(PitchOutcome pitch);

	void endOfMatch(BaseballMatch baseballMatch);
	
}