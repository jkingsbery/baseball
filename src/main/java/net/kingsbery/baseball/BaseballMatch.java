package net.kingsbery.baseball;

import java.util.ArrayList;
import java.util.List;

import net.kingsbery.baseball.Season.Team;

public class BaseballMatch {

	int strikes = 0;
	int balls = 0;
	
	int totalAtBats = 0;
	int hits = 0;
	
	int halfInning = 1;
	int outs = 0;
	
	List<Integer> runsPerInning = new ArrayList<Integer>();
	
	public static class Runner{
		
	}

	public String toString(){
		return this.away + " vs. " + this.home;
	}
	
	Runner[] baseRunners = new Runner[4];
	private int runs;
	private Team home;
	private Team away;
	
	public BaseballMatch(){
		this(null, null);
	}
	
	public BaseballMatch(Team away, Team home) {
		this.home = home;
		this.away = away;
		// TODO Auto-generated constructor stub
	}

	public void putRunnerOn(Runner runner, int base){
		putRunnerOn(runner, base, 0);
	}
	
	private void putRunnerOn(Runner runner, int base, int from){
		if(base >= 4){
			System.out.println("Run Scored!");
			runs++;
		}
		else if(baseRunners[base] == null){
			baseRunners[base] = runner;
			baseRunners[from] = null;
		} else {
			putRunnerOn(baseRunners[base], base + 1, base);
			baseRunners[base] = runner;
		}
	}
	
	public void advanceRunners(PitchOutcome pitch){
		switch(pitch){
		case Single:
			for (int i = 2; i < baseRunners.length; i++) {
				if (baseRunners[i] != null) {
					runs++;
					baseRunners[i] = null;
				}
			}
			if(baseRunners[1]!=null){
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
			//For now, nothing happens
		}
	}
	
	public void next(PitchOutcome pitch){
		switch(pitch){
		case Strike:
			strikes++;
			if(strikes == 3){
				System.out.println("Strike 3!");
				recordOut();
			}
			break;
		case Ball:
			balls++;
			if(balls == 4){
				System.out.println("Take your base!");
				putRunnerOn(new Runner(),1);

				nextBatter();
			}
			break;
		case FoulBall:
			if(strikes < 2){
				strikes++;
			}
			break;
		case GroundOut:
		case PopOut:
			System.out.println(pitch);
			recordOut();
			break;
		case Single:
		case Double:
		case Triple:
		case HR:
			advanceRunners(pitch);
			System.out.println(pitch);
			hits++;
			nextBatter();
			break;
		}
	}
	
	public Runner getRunnerOn(int base){
		return baseRunners[base];
	}
	
	public int getRunsThisInning(){
		return runs;
	}
	
	private void recordOut() {
		outs++;
		if(outs==3){
			nextInning();
		}else {
			nextBatter();
		}
	}

	public int getRuns(int team){
		int totalRuns = 0;
		for(int i = team; i<runsPerInning.size(); i+=2){
			totalRuns+=runsPerInning.get(i);
		}
		return totalRuns;
	}
	
	private String topOrBottom(){
		return halfInning % 2 == 0 ? "Bottom" : "Top"; 
	}
	
	private void nextInning() {
		System.out.print(topOrBottom() + " of the " + ((halfInning+1)/2) + ": " );
		System.out.println(runs + " runs scored");
		halfInning++;
		
		
		System.out.println("==============================");
		runsPerInning.add(runs);
		runs = 0;
		outs = 0;
		baseRunners = new Runner[4];
		nextBatter();
	}

	public void nextBatter(){
		totalAtBats++;
		strikes = 0;
		balls = 0;
	}
	
	public boolean stillPlaying(){
		return halfInning <= 18; //or, extra innings...
	}
	
	public int getInning(){
		return halfInning / 2;
	}

	public int getHits(){
		return hits;
	}
	
	public int getBatterCount() {
		return totalAtBats;
	}
}
