package net.kingsbery.baseball;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		BaseballMatch match = new BaseballMatch();
		PitchSimulator simulator = new PitchSimulator();
		match.simulateMatch(simulator);
		match.printResults();
	}

	
	
}
