package net.kingsbery.baseball;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Season {

	List<Team> teams = new ArrayList<Team>();

	public static class Series {

		private Team home;
		private Team away;

		public Series(Team away, Team home) {
			this.home = home;
			this.away = away;
		}

		public String toString() {
			return this.away + " vs. " + this.home;
		}

		public boolean isPlaying(Team team) {
			return team == away || team == home;
		}

		public List<Team> getTeams() {
			return Arrays.asList(away, home);
		}
	}

	private Map<Integer, List<Series>> seriesMatchups = new HashMap<Integer, List<Series>>();
	private Map<Integer, List<BaseballMatch>> schedule = new HashMap<Integer, List<BaseballMatch>>();

	private Random rand = new Random(13);
	int seriesCount = 0;

	// Pretty good, just need to balance home/away
	public Season(List<Team> teams) {
		this.teams = teams;
		scheduleSeries();
		seriesToGames();
		printSchedule();
	}

	public Season() {
		List<Team> teams = defaultTeams();
		this.teams = teams;
		scheduleSeries();
		seriesToGames();
		printSchedule();
	}

	private List<Team> defaultTeams() {
		List<Team> teams = new ArrayList<Team>();
		for (int i = 0; i < 14; i++) {
			teams.add(new Team(Integer.toString(i)));
		}
		return teams;
	}

	private void scheduleSeries() {
		List<Team> teamRoundRobin = new LinkedList<Team>(teams);
		for (int i = 0; i < 4; i++) {
			roundRobin(teamRoundRobin);
		}
	}

	private void printSchedule() {
		for (int i = 0; i < schedule.size(); i++) {
			System.out.println((i + 1) + ": " + schedule.get(i));
		}
	}

	private void seriesToGames() {
		for (int i = 0; i < seriesMatchups.size(); i++) {
			for (int j = 0; j < 3; j++) {
				List<BaseballMatch> matchups = new ArrayList<BaseballMatch>();
				for (Series series : seriesMatchups.get(i + 1)) {
					matchups.add(new BaseballMatch(series.away, series.home));
				}
				schedule.put(3 * i + j, matchups);
			}
		}
	}

	private void roundRobin(List<Team> teamRoundRobin) {
		// round robin implementation
		// 0 7
		// 1 6
		// 2 5
		// 3 4
		// then rotate all except 0
		for (int i = 1; i < teams.size(); i++) {
			Team teamToMove = teamRoundRobin.remove(1);
			teamRoundRobin.add(teamToMove);
			createSeries(teamRoundRobin);
		}
		Collections.shuffle(teamRoundRobin, rand);
	}

	private void createSeries(List<Team> teamRoundRobin) {
		seriesCount++;
		List<Series> seriesThisDay = new ArrayList<Series>(teamRoundRobin.size() / 2);
		for (int i = 0; i < teamRoundRobin.size() / 2; i++) {
			seriesThisDay.add(new Series(teamRoundRobin.get(i), teamRoundRobin.get(teamRoundRobin.size() - 1 - i)));
		}
		seriesMatchups.put(seriesCount, seriesThisDay);
	}

	public List<Team> getTeams() {
		return Collections.unmodifiableList(teams);
	}

	public List<Series> getSeries(Team team0, Team team1) {
		List<Series> result = new ArrayList<Series>();
		for (List<Series> days : seriesMatchups.values()) {
			for (Series series : days) {
				if ((series.home == team0 && series.away == team1) || (series.home == team1 && series.away == team0)) {
					result.add(series);
				}
			}
		}
		return result;
	}

	public List<BaseballMatch> getMatches(Team team0, Team team1) {
		List<BaseballMatch> result = new ArrayList<BaseballMatch>();
		for (List<BaseballMatch> days : schedule.values()) {
			for (BaseballMatch series : days) {
				if (series.has(team0, team1)) {
					result.add(series);
				}
			}
		}
		return result;
	}

	public void simulate(int day) {
		PitchSimulator simulator = new PitchSimulator();
		for (BaseballMatch match : schedule.get(day)) {
			match.simulateMatch(simulator);
			match.printResults();
		}
	}

	private int getNumberOfDays() {
		return schedule.size();
	}

	public static void main(String args[]) {
		Season season = new Season();
		long start = System.nanoTime();
		for (int i = 0; i < season.getNumberOfDays(); i++) {
			season.simulate(i);
		}
		long end = System.nanoTime();
		System.out.println("Simulation took " + (end - start));
	}

}
