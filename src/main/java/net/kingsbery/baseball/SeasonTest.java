package net.kingsbery.baseball;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import net.kingsbery.baseball.Season.Team;

public class SeasonTest {

	@Test
	public void getGamesBetween() {
		Season season = new Season();

		Team team0 = season.getTeams().get(0);
		Team team1 = season.getTeams().get(1);
		
		List<BaseballMatch> matches = season.getMatches(team0, team1);
		
		assertTrue(matches.size() > 1);
	}
}
