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

	public static class Team {

		private String teamName;

		public Team(String string) {
			teamName = string;
		}

		public String toString() {
			return teamName;
		}

	}

	List<Team> teams = new ArrayList<Team>();
	
	public static class Series{

		private Team home;
		private Team away;

		public Series(Team away, Team home) {
			this.home =home;
			this.away =away;
		}
		
		public String toString(){
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
	
	public Season() {
		for (int i = 0; i < 8; i++) {
			teams.add(new Team(Integer.toString(i)));
		}
		
		List<Series> matches = new LinkedList<Series>();
		for(int i=0; i<teams.size(); i++){
			for(int j=0; j<teams.size(); j++){
				if(i!=j){
					matches.add(new Series(teams.get(i), teams.get(j)));
					matches.add(new Series(teams.get(i), teams.get(j)));
					matches.add(new Series(teams.get(j), teams.get(i)));
					matches.add(new Series(teams.get(j), teams.get(i)));
				}
			}
		}
		Collections.shuffle(matches, new Random(13));
		while(!matches.isEmpty()){
			Series match = matches.remove(0);
			scheduleFor(match,firstAvailableDay(match.getTeams()));
		}
		System.out.println(seriesMatchups);
		Map<Integer, List<BaseballMatch>> schedule = new HashMap<Integer, List<BaseballMatch>>();
		//shuffle again
		List<Integer> days = new ArrayList<Integer>();
		for(int i=1; i<= seriesMatchups.size(); i++){
			days.add(i);
		}
		//Collections.shuffle(days);
		
		for(int i=0; i< days.size(); i++){
			int day = days.get(i);
			System.out.println(day + ", " + day);
			List<BaseballMatch> matchesForDay = new ArrayList<BaseballMatch>();
			
			for(Series series : seriesMatchups.get(day)){
				matchesForDay.add(new BaseballMatch(series.away, series.home));
			}
			schedule.put(3*i+0, new ArrayList<BaseballMatch>(matchesForDay));
			schedule.put(3*i+1, new ArrayList<BaseballMatch>(matchesForDay));
			schedule.put(3*i+2, new ArrayList<BaseballMatch>(matchesForDay));
		}
		System.out.println(schedule);
		System.out.println("Length of season in days: " + schedule.size());
	}
	
	private void scheduleFor(Series match, int firstAvailableDay) {
		if(!seriesMatchups.containsKey(firstAvailableDay)){
			seriesMatchups.put(firstAvailableDay, new ArrayList<Series>());
		}
		seriesMatchups.get(firstAvailableDay).add(match);
	}

	private int firstAvailableDay(List<Team> teams){
		int i =0;
		boolean allAvailable;
		do{
			i++;
			allAvailable = true;
			for(Team team : teams){
				if(!seriesMatchups.containsKey(i)){
					seriesMatchups.put(i, new ArrayList<Series>());
				}
				for(Series series : seriesMatchups.get(i)){
					if(series.isPlaying(team)){
						allAvailable=false;
					}
				}
			}
		}while(!allAvailable);
		return i;
	}
	
	
	public List<Team> getTeams(){
		return Collections.unmodifiableList(teams);
	}

	public static void main(String args[]) {
		new Season();
	}

	public List<BaseballMatch> getMatches(Team team0, Team team1) {
		// TODO Auto-generated method stub
		return null;
	}
}
