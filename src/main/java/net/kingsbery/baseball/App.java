package net.kingsbery.baseball;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
	
	
	private static BufferedReader reader;

	public static void main(String args[]) throws IOException{
		initInput();
		String command;
		System.out.println("Baseball simulator!");

		while(!(command = prompt(reader)).equals("quit")){
			if("season".equals(command)){
				Season season = new Season();
				season.simulate();
				season.printSchedule();
				season.printStandings();
			}else if("game".equals(command)){
				BaseballMatch match = new BaseballMatch();
				match.simulate();
			}else{
				System.out.println("Unknown command: " + command);
			}
		}
		System.out.println("That's all folks!");
	}

	private static void initInput() {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	private static String prompt(BufferedReader reader) throws IOException {
		System.out.print("Command> ");
		return reader.readLine();
	}

	
	
}
