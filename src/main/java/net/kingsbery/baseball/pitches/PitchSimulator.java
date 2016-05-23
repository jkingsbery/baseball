package net.kingsbery.baseball.pitches;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.kingsbery.baseball.players.Player;

public class PitchSimulator {

	public Random random = new Random(42);

	Player pitcher;
	Player batter;
	
	public PitchSimulator(Player pitcher, Player batter){
		this.pitcher = pitcher;
		this.batter = batter;
	}

	public PitchOutcome pitch() {

		Map<PitchOutcome, Double> probs = getProbabilities();
		double randDouble = random.nextDouble();
		double total = 0.0;
		for (PitchOutcome x : probs.keySet()) {
			total += probs.get(x);
			if (total > randDouble) {
				return x;
			}
		}
		throw new AssertionError("The probabilities did not add up to 1");
	}

	double extraBaseHitLikelihood = 0.4;
	double singleLikelihood = 1-extraBaseHitLikelihood;
	double doubleLikelihood = extraBaseHitLikelihood * 0.6;
	double tripleLikelihood = extraBaseHitLikelihood * 0.1;
	double hrLikelihood = extraBaseHitLikelihood * 0.3;
	double contactMadeProb = 1.0 / 3;
	double hitProbability = 0.3;
	double hitFair = 0.7;
	double groundOutProb = 0.5;

	public Map<PitchOutcome, Double> getProbabilities() {
		Map<PitchOutcome, Double> probs = new HashMap<PitchOutcome, Double>();
		probs.put(PitchOutcome.Ball, 1.0 * (1 - contactMadeProb) * 0.5);
		probs.put(PitchOutcome.Strike, 1.0 * (1 - contactMadeProb) * 0.5);
		probs.put(PitchOutcome.FoulBall, 1.0 * contactMadeProb * (1 - hitFair));
		probs.put(PitchOutcome.GroundOut, 1.0 * contactMadeProb * hitFair * (1 - hitProbability) * groundOutProb);
		probs.put(PitchOutcome.PopOut, 1.0 * contactMadeProb * hitFair * (1 - hitProbability) * (1 - groundOutProb));
		probs.put(PitchOutcome.Single, 1.0 * contactMadeProb * hitFair * hitProbability * singleLikelihood);
		probs.put(PitchOutcome.Double, 1.0 * contactMadeProb * hitFair * hitProbability * doubleLikelihood);
		probs.put(PitchOutcome.Triple, 1.0 * contactMadeProb * hitFair * hitProbability * tripleLikelihood);
		probs.put(PitchOutcome.HR, 1.0 * contactMadeProb * hitFair * hitProbability * hrLikelihood);
		return Collections.unmodifiableMap(probs);
	}

}
