package net.kingsbery.baseball.players;

import java.util.Random;

public class Player {

	private static final String[] FIRST_NAMES = new String[] { "Adam", "Alex", "Bob", "Bobby", "Carl", "Dave", "David",
			"Daniel", "Danny", "Edgar", "Ed", "Felix", "Fred", "Greg", "Howard", "Iggy", "John", "James", "Jim",
			"Larry", "Mike", "Michael", "Manuel", "Nick", "Oscar", "Peter", "Paul" };

	private static final String[] LAST_NAMES = new String[]{
			"Adams",
			"Adamson",
			"Birch",
			"Brewer",
			"Fletcher",
			"Jones",
			"Rabble",
			"Smith"
			
	};

	PlayerStats stats = new PlayerStats();

	private String firstName;
	private String lastName;
	private static final Random random = new Random(42);
	public static Player buildPitcher() {
		Player player = new Player();
		player.firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
		player.lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
		return player;
	}

	public static Player buildBatter() {
		Player player = new Player();
		player.firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
		player.lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
		return player;
	}

	public PlayerStats getStats() {
		return stats;
	}
	
	public String toString(){
		return firstName + " " + lastName; 
	}

}
