package net.kingsbery.baseball;

import java.util.ArrayList;
import java.util.List;

import net.kingsbery.baseball.events.DefaultMatchEventHandler;
import net.kingsbery.baseball.events.MatchEventHandler;
import net.kingsbery.baseball.pitches.DefaultPitchSimulatorFactory;
import net.kingsbery.baseball.pitches.PitchOutcome;
import net.kingsbery.baseball.pitches.PitchSimulator;
import net.kingsbery.baseball.pitches.PitchSimulatorFactory;
import net.kingsbery.baseball.players.Player;

public class BaseballMatch {

	int strikes = 0;
	int balls = 0;

	int totalAtBats = 0;
	int hits = 0;

	int halfInning = 1;
	int outs = 0;

	List<Integer> runsPerInning = new ArrayList<Integer>();

	MatchEventHandler handler = new DefaultMatchEventHandler();

	public static class Runner {

	}

	Runner[] baseRunners = new Runner[4];
	private int runs;
	private Team home;
	int[] lineupPos = new int[]{0,0};
	private Team away;
	private Player batter;

	public BaseballMatch() {
		this(new Team("away"), new Team("home"));
	}

	public BaseballMatch(Team away, Team home) {
		this.home = home;
		this.away = away;
	}

	public String toString() {
		if(notStarted()){
			return this.away + " vs. " + this.home;
		}else if(stillPlaying()){
			return this.away + " " + getRuns(0)+ ", " + this.home + " " + this.getRuns(1);
		}else{
			return "final score: " +  this.away + " " + getRuns(0)+ ", " + this.home + " " + this.getRuns(1);
		}
	}

	public void setHandler(MatchEventHandler handler) {
		this.handler = handler;
	}

	// TODO PitchSimulator will come from a different source at some point
	void simulateMatch(PitchSimulatorFactory factory) {
		handler.matchStart(this);
		while (stillPlaying()) {
			Player pitcher = Player.buildPitcher();
			Player batter = Player.buildBatter();
			PitchSimulator simulator = factory.getSimulator(pitcher, batter);
			next(simulator.pitch());
		}
		handler.endOfMatch(this);
	}

	public void putRunnerOn(Runner runner, int base) {
		putRunnerOn(runner, base, 0);
	}

	private void putRunnerOn(Runner runner, int base, int from) {
		if (base >= 4) {
			handler.runScored(this);
			runs++;
		} else if (baseRunners[base] == null) {
			baseRunners[base] = runner;
			baseRunners[from] = null;
		} else {
			putRunnerOn(baseRunners[base], base + 1, base);
			baseRunners[base] = runner;
		}
	}

	public void advanceRunners(PitchOutcome pitch) {
		switch (pitch) {
		case Single:
			for (int i = 2; i < baseRunners.length; i++) {
				if (baseRunners[i] != null) {
					runs++;
					baseRunners[i] = null;
				}
			}
			if (baseRunners[1] != null) {
				putRunnerOn(baseRunners[1], 2, 1);
			}
			putRunnerOn(new Runner(), 1);
			break;
		case Double:
			for (int i = 0; i < baseRunners.length; i++) {
				if (baseRunners[i] != null) {
					runs++;
					baseRunners[i] = null;
				}
			}
			putRunnerOn(new Runner(), 2);
			break;
		case Triple:
			for (int i = 0; i < baseRunners.length; i++) {
				if (baseRunners[i] != null) {
					runs++;
					baseRunners[i] = null;
				}
			}
			putRunnerOn(new Runner(), 3);
			break;
		case HR:
			for (int i = 0; i < baseRunners.length; i++) {
				if (baseRunners[i] != null) {
					runs++;
					baseRunners[i] = null;
				}
			}
			runs++;
			break;
		default:
			// For now, nothing happens on outs
		}
	}

	public void next(PitchOutcome pitch) {
		switch (pitch) {
		case Strike:
			strikes++;
			if (strikes == 3) {
				handler.strikeOut();
				recordOut();
			}
			break;
		case Ball:
			balls++;
			if (balls == 4) {
				handler.walk();
				putRunnerOn(new Runner(), 1);

				nextBatter();
			}
			break;
		case FoulBall:
			if (strikes < 2) {
				strikes++;
			}
			break;
		case GroundOut:
		case PopOut:
			handler.playResult(pitch);
			recordOut();
			break;
		case Single:
		case Double:
		case Triple:
		case HR:
			advanceRunners(pitch);
			handler.playResult(pitch);
			hits++;
			nextBatter();
			break;
		}
	}

	public Runner getRunnerOn(int base) {
		return baseRunners[base];
	}

	public int getRunsThisInning() {
		return runs;
	}

	private void recordOut() {
		outs++;
		batter.getStats().onOut();
		if (outs == 3) {
			nextInning();
		} else {
			nextBatter();
		}
	}

	public int getRuns(int team) {
		int totalRuns = 0;
		for (int i = team; i < runsPerInning.size(); i += 2) {
			totalRuns += runsPerInning.get(i);
		}
		return totalRuns;
	}

	public String topOrBottom() {
		return teamAtBat() == 0 ? "Bottom" : "Top";
	}

	private int teamAtBat() {
		return halfInning % 2;
	}

	private void nextInning() {
		handler.nextInning(this, runs);

		halfInning++;

		runsPerInning.add(runs);
		runs = 0;
		outs = 0;
		baseRunners = new Runner[4];
		nextBatter();
	}

	public void nextBatter() {
		
		totalAtBats++;
		strikes = 0;
		balls = 0;
	}

	public boolean stillPlaying() {
		return halfInning <= 18; // or, extra innings...
	}

	private boolean notStarted() {
		return halfInning == 1 ;
	}

	public int getInning() {
		return (halfInning + 1) / 2;
	}

	public int getHits() {
		return hits;
	}

	public int getBatterCount() {
		return totalAtBats;
	}

	public boolean has(Team team0, Team team1) {
		return (home == team0 && away == team1) || (home == team1 && away == team0);
	}

	public Team getAwayTeam() {
		return away;
	}

	public Team getHomeTeam() {
		return home;
	}

	public static void main(String[] args) {
		BaseballMatch match = new BaseballMatch();
		match.simulate();
	}

	public Team getLosingTeam() {
		if(getRuns(0)==getRuns(1)){
			return null;
		}
		else if(getRuns(0) < getRuns(1)){
			return away;
		} else{
			return home;
		}
	}

	public static int tieCounter = 0;

	public Team getWinningTeam() {
		if(getRuns(0)==getRuns(1)){
			tieCounter++;
			System.err.println("TIE #" + tieCounter);
			return null;
		}
		else if(getRuns(0) > getRuns(1)){
			return away;
		} else{
			return home;
		}
	}

	public void simulate() {
		PitchSimulatorFactory simulator = new DefaultPitchSimulatorFactory();
		simulateMatch(simulator);
	}

}
