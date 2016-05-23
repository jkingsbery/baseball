package net.kingsbery.baseball;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.kingsbery.baseball.players.Player;

public class Team {

	private String teamName;

	private List<Player> players = new ArrayList<Player>();

	public Team(String string) {
		teamName = string;
		populatePlayers();
	}

	public static Map<String, Team> teams;

	public static List<String> TEAM_NAMES = Arrays.asList(new String[]{ "Lions", "Wolves", "Tigers", "Atlantic", "Lizards",
			"Robins", "Hogs", "Hawks", "Pioneers", "Saints", "Devils", "Millers", "Cows", "Rabble"});
	
	public static List<Team> defaultTeams() {
		if (teams == null) {
			populateTeams();
		}
		return new ArrayList<Team>(teams.values());
	}

	private static void populateTeams() {
		teams = new HashMap<String, Team>();
		for (int i = 0; i < 14; i++) {
			teams.put(TEAM_NAMES.get(i), new Team(TEAM_NAMES.get(i)));
		}
	}

	public static Team getTeam(String s){
		if(teams == null){
			populateTeams();
		}
		return teams.get(s);
	}
	
	private void populatePlayers() {
		for (int i = 0; i < 9; i++) {
			players.add(Player.buildBatter());
		}
	}

	public String toString() {
		return teamName;
	}

	public List<Player> getLineup() {
		return new ArrayList<Player>(players.subList(0, 9));
	}

}