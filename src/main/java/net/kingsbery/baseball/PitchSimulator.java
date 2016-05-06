package net.kingsbery.baseball;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PitchSimulator {

	public static Random random = new Random(42);

	public static PitchOutcome pitch() {
		// first calculate probabilities
		Map<PitchOutcome, Double> probs = getProbabilities();

		double randDouble = random.nextDouble();
		double total = 0.0;
		// System.out.println(randDouble);
		for (PitchOutcome x : probs.keySet()) {
			total += probs.get(x);
			// System.out.println(x + ": " + total);
			if (total > randDouble) {
				return x;
			}
		}
		throw new AssertionError("The probabilities did not add up to 1");
	}

	private static Map<PitchOutcome, Double> getProbabilities() {
		Map<PitchOutcome, Double> probs = new HashMap<PitchOutcome, Double>();
		double singleLikelihood = 0.6;
		double doubleLikelihood = 0.2;
		double tripleLikelihood = 0.05;
		double hrLikelihood = 0.15;
		double contactMadeProb = 1.0 / 3;
		double hitProbability = 0.27;
		double hitFair = 0.7;
		double groundOutProb = 0.5;
		probs.put(PitchOutcome.Ball, 1.0 * (1 - contactMadeProb) * 0.5);
		probs.put(PitchOutcome.Strike, 1.0 * (1 - contactMadeProb) * 0.5);
		probs.put(PitchOutcome.FoulBall, 1.0 * contactMadeProb * (1-hitFair));
		probs.put(PitchOutcome.GroundOut, 1.0 * contactMadeProb * hitFair * (1 - hitProbability) * groundOutProb);
		probs.put(PitchOutcome.PopOut, 1.0 * contactMadeProb * hitFair * (1 - hitProbability) * (1 - groundOutProb));
		probs.put(PitchOutcome.Single, 1.0 * contactMadeProb * hitFair * hitProbability * singleLikelihood);
		probs.put(PitchOutcome.Double, 1.0 * contactMadeProb * hitFair * hitProbability * doubleLikelihood);
		probs.put(PitchOutcome.Triple, 1.0 * contactMadeProb * hitFair * hitProbability * tripleLikelihood);
		probs.put(PitchOutcome.HR, 1.0 * contactMadeProb * hitFair * hitProbability * hrLikelihood);
		return probs;
	}

	public static void main(String args[]) {
		Map<PitchOutcome, Double> probs = getProbabilities();
		double total = 0.0;
		for (double x : probs.values()) {
			total += x;
		}
		System.out.println(total);
	}
}
