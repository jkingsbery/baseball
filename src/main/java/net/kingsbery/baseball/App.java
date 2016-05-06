package net.kingsbery.baseball;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		BaseballMatch match = new BaseballMatch();
		while(match.stillPlaying()){
			match.next(PitchSimulator.pitch());
		}
		System.out.println("Total hits: " + match.getHits());
		System.out.println("Total batters: " + match.getBatterCount());
		System.out.println("Final score: Home team " + match.getRuns(1) + ", Away Team " + match.getRuns(0));
	}
	
}
