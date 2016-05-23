package net.kingsbery.baseball.players;

public class PlayerStats {

	int ab =0;
	int hit = 0;
	int doubles;
	int triples;
	int hr = 0;
	
	public void onOut(){
		ab++;
	}
	
	public void onSingle(){
		hit++;
		ab++;
	}
	
	public void onDouble(){
		hit++;
		ab++;
		doubles++;
	}
	
	public void onTriple(){
		hit++;
		ab++;
		triples++;
	}
	
	public void onHr(){
		hit++;
		ab++;
		hr++;
	}
}
