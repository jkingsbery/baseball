package net.kingsbery.baseball;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import net.kingsbery.baseball.Season.Series;

public class SeasonTest {

	@Test
	public void getGamesBetween() {
		Season season = new Season();

		Team team0 = season.getTeams().get(0);
		Team team1 = season.getTeams().get(1);
		
		List<Series> series = season.getSeries(team0, team1);
		List<BaseballMatch> matches = season.getMatches(team0, team1);
		System.out.println(series);
		System.out.println(matches);
		assertTrue(series.size() > 1);
		assertTrue(matches.size() > 1);
	}
}
