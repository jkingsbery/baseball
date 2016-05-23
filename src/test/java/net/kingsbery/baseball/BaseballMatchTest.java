package net.kingsbery.baseball;

import static org.junit.Assert.*;

import org.junit.Test;

import net.kingsbery.baseball.pitches.PitchOutcome;

public class BaseballMatchTest {

	@Test
	public void walkNoOneOn(){
		BaseballMatch match = new BaseballMatch();
		match.putRunnerOn(new BaseballMatch.Runner(), 1);
		assertNotNull(match.getRunnerOn(1));
	}
	
	@Test
	public void walkTwoRunners(){
		BaseballMatch match = new BaseballMatch();
		match.putRunnerOn(new BaseballMatch.Runner(), 1);
		match.putRunnerOn(new BaseballMatch.Runner(), 1);
		assertNotNull(match.getRunnerOn(2));
		assertNotNull(match.getRunnerOn(1));
	}
	
	
	@Test
	public void walkInRun(){
		BaseballMatch match = new BaseballMatch();
		match.putRunnerOn(new BaseballMatch.Runner(), 1);
		match.putRunnerOn(new BaseballMatch.Runner(), 1);
		match.putRunnerOn(new BaseballMatch.Runner(), 1);
		match.putRunnerOn(new BaseballMatch.Runner(), 1);
		assertEquals(1, match.getRunsThisInning());
	}
	
	@Test
	public void hrScoresRun(){
		BaseballMatch match = new BaseballMatch();
		match.next(PitchOutcome.HR);
		assertEquals(1, match.getRunsThisInning());
	}
	
	@Test
	public void tripleWithRunnerOn(){
		BaseballMatch match = new BaseballMatch();
		match.next(PitchOutcome.Single);
		match.next(PitchOutcome.Triple);
		assertEquals(1, match.getRunsThisInning());
		assertNotNull(match.getRunnerOn(3));
		assertNull(match.getRunnerOn(1));
	}
}
