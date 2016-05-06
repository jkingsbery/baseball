package net.kingsbery.baseball;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		for(int i=0; i<300; i++){
			System.out.println(pitch());
		}
	}
	
	public static enum PitchOutcome {
		Ball,
		Strike,
		FoulBall,
		GroundOut,
		PopOut,
		Single,
		Double,
		Triple,
		HR
	}
	
	public static Random random = new Random(42);
	
	/**
	 * Possible outcomes and probabilities
	 * Ball 2/6
	 * Strike 2/6
	 * Foul ball 1/6
	 * Ground Out 1/6*0.75*0.5
	 * Pop Out 1/6*0.75*0.5
	 * Single 1/6 * 0.25 * 0.6
	 * Double 1/6 * 0.25 * 0.2
	 * Triple 1/6 * 0.25 * 0.05
	 * HR 1/6 * 0.25 * 0.15
	 */
	public static PitchOutcome pitch(){
		//first calculate probabilities
		Map<PitchOutcome, Double> probs = new HashMap<PitchOutcome, Double>();
		probs.put(PitchOutcome.Ball, 1.0 * 2/6);
		probs.put(PitchOutcome.Strike, 1.0 * 2/6);
		probs.put(PitchOutcome.FoulBall, 1.0 * 1/6);
		probs.put(PitchOutcome.GroundOut, 1.0 * 3/4 * 1/6 / 2);
		probs.put(PitchOutcome.PopOut, 1.0* 3/4 * 1/6 / 2);
		probs.put(PitchOutcome.Single, 1.0 * (1.0/6) * (0.25)  * (0.6));
		probs.put(PitchOutcome.Double, 1.0 * (1.0/6) * (0.25)  * (0.2));
		probs.put(PitchOutcome.Triple, 1.0 * (1.0/6) * (0.25)  * (0.05));
		probs.put(PitchOutcome.HR, 1.0 * (1.0/6) * (0.25)  * (0.15));
		
		double randDouble = random.nextDouble();
		double total = 0.0;
		//System.out.println(randDouble);
		for(PitchOutcome x : probs.keySet()){
			total += probs.get(x);
			//System.out.println(x + ": " + total);
			if(total > randDouble){
				return x;
			}
		}
		throw new AssertionError("The probabilities did not add up to 1");
	}
}
