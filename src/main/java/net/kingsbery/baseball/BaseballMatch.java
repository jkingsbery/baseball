package net.kingsbery.baseball;

public class BaseballMatch {

	int strikes = 0;
	int balls = 0;
	
	int totalAtBats = 0;
	int hits = 0;
	
	int halfInning = 1;
	int outs = 0;
	
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
			System.out.println(pitch);
			hits++;
			nextBatter();
			break;
		}
	}
	
	private void recordOut() {
		outs++;
		if(outs==3){
			nextInning();
		}else {
			nextBatter();
		}
		
	}

	private String topOrBottom(){
		return halfInning % 2 == 0 ? "Bottom" : "Top"; 
	}
	
	private void nextInning() {
		
		halfInning++;
		System.out.println(topOrBottom() + " of the " + (halfInning/2) );
		System.out.println("==============================");
		outs = 0;
		nextBatter();
	}

	public void nextBatter(){
		totalAtBats++;
		strikes = 0;
		balls = 0;
	}
	
	public boolean stillPlaying(){
		return getInning() <= 9; //or, extra innings...
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
