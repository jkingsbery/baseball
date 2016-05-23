package net.kingsbery.baseball.events;

import net.kingsbery.baseball.BaseballMatch;
import net.kingsbery.baseball.pitches.PitchOutcome;

/**
 * When simulating many games, this event handler is much faster than
 * the default. As an example, simulating 156*4 games took around 0.2 seconds
 * with this handler, vs 1.7 seconds with the default.
 * 
 *
 */
public class QuietMatchEventHandler implements MatchEventHandler {

	@Override
	public void matchStart(BaseballMatch match) {
		
	}

	@Override
	public void runScored(BaseballMatch baseballMatch) {
		
	}

	@Override
	public void nextInning(BaseballMatch baseballMatch, int runs) {
		
	}

	@Override
	public void strikeOut() {
		
	}

	@Override
	public void walk() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playResult(PitchOutcome pitch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endOfMatch(BaseballMatch match) {
		
	}
	
}