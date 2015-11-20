package com.capgemini.bowling;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {

	private List<Integer> rolls;

	public BowlingGame() {
		rolls = new ArrayList<>();
	}

	public void roll(int pins) {
		rolls.add(pins);
		if (pins == 10 && (rolls.size() % 2 == 1)) {
			rolls.add(0);
		}
	}

	public int getScore() {
		int score = 0;
		int rollIndex = 0;

		for (int round = 0; round < 10; round++) {
			if (isStrike(rollIndex)) {
				score += getStrikeScore(rollIndex);
			} else if (isSpare(rollIndex)) {
				score += getSpareScore(rollIndex);
			} else {
				score += getNormalScore(rollIndex);
			}
			rollIndex += 2;
		}
		return score;
	}

	public int getStrikeScore(int rollIndex) {
		int firstNextRoll = rolls.get(rollIndex + 2);
		int secondNextRoll = isStrike(rollIndex + 2) ? rolls.get(rollIndex + 4) : rolls.get(rollIndex + 3);
		return 10 + firstNextRoll + secondNextRoll;
	}

	public boolean isStrike(int rollIndex) {
		return rolls.get(rollIndex) == 10;
	}

	public void displayRolls() {
		for (int round = 0; round < 10; round++) {
			System.out.println(round + ": " + rolls.get(2 * round) + " " + rolls.get(2 * round + 1));
		}
		if (rolls.size() > 20) {
			System.out.println("Bonus:");
			for (int i = 20; i < rolls.size(); i++) {
				System.out.print(rolls.get(i) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	private int getSpareScore(int rollIndex) {
		return rolls.get(rollIndex) + rolls.get(rollIndex + 1) + rolls.get(rollIndex + 2);
	}

	private boolean isSpare(int rollIndex) {
		return rolls.get(rollIndex) + rolls.get(rollIndex + 1) == 10;
	}

	private int getNormalScore(int rollIndex) {
		return rolls.get(rollIndex) + rolls.get(rollIndex + 1);
	}

}
