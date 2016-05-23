package net.kingsbery.baseball.events;

import net.kingsbery.baseball.BaseballMatch;
import net.kingsbery.baseball.pitches.PitchOutcome;

public class DefaultMatchEventHandler implements MatchEventHandler {

	@Override
	public void matchStart(BaseballMatch match) {
		System.out.println("+++++++++++++++++++++");
		System.out.println(match.getAwayTeam() + " vs. " + match.getHomeTeam());
		System.out.println("+++++++++++++++++++++");
	}

	@Override
	public void runScored(BaseballMatch baseballMatch) {
		System.out.println("Run Scored!");
	}

	@Override
	public void nextInning(BaseballMatch match, int runs) {
		System.out.print(match.topOrBottom() + " of the " + match.getInning() + ": ");
		System.out.println(runs + " runs scored");
		System.out.println("==============================");
		
	}

	@Override
	public void strikeOut() {
		System.out.println("Strike 3!");
		
	}

	@Override
	public void walk() {
		System.out.println("Take your base!");
		
	}

	@Override
	public void playResult(PitchOutcome pitch) {
		System.out.println(pitch);
		
	}

	@Override
	public void endOfMatch(BaseballMatch match) {
		System.out.println("Total hits: " + match.getHits());
		System.out.println("Total batters: " + match.getBatterCount());
		System.out.println("Final score: Home team " + match.getRuns(1) + ", Away Team " + match.getRuns(0));
		
	}
	
}