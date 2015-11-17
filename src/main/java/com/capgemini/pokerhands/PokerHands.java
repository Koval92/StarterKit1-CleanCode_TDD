package com.capgemini.pokerhands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PokerHands {
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	public static final int ACE = 14;
	public static final int LEFT_WIN = 1;
	public static final int RIGHT_WIN = -1;

	private static int result;
	private static boolean leftOneColor;
	private static boolean rightOneColor;

	private static void initializeFields() {
		result = 0;
		leftOneColor = false;
		rightOneColor = false;
	}

	public static boolean isLeftOneColor() {
		return leftOneColor;
	}

	public static boolean isRightOneColor() {
		return rightOneColor;
	}

	// returns 1 when left hand wins, and -1, if right one
	public static int compare(String handsAsString) {
		initializeFields();

		System.out.println();
		System.out.println("Current hands: " + handsAsString);

		List<String> leftHand = getLeftHand(handsAsString);
		List<String> rightHand = getRightHand(handsAsString);

		leftOneColor = isInOneColor(leftHand);
		rightOneColor = isInOneColor(rightHand);

		notifyIfHandHasColor();

		List<Integer> leftFigures = getFiguresAsIntegers(leftHand);
		List<Integer> rightFigures = getFiguresAsIntegers(rightHand);

		displayFigures(leftFigures);
		displayFigures(rightFigures);

		Map<Integer, Integer> leftCounts = createCountsMap(leftFigures);
		Map<Integer, Integer> rightCounts = createCountsMap(rightFigures);

		displayCounts(leftCounts);
		displayCounts(rightCounts);

		checkForTwoPairs(leftCounts, rightCounts);
		checkForHighestPair(leftCounts, rightCounts);
		checkForHighestSingleCard(leftCounts, rightCounts);

		if (result == 0) {
			System.out.println("No one wins!");
		}

		return result;
	}

	private static void checkForTwoPairs(Map<Integer, Integer> leftCounts, Map<Integer, Integer> rightCounts) {
		if (result != 0) {
			return;
		}

		List<Integer> leftPairs = new ArrayList<>();
		List<Integer> rightPairs = new ArrayList<>();

		for (int card = ACE; card >= 2; card--) {
			int left = leftCounts.getOrDefault(card, 0);
			int right = rightCounts.getOrDefault(card, 0);

			if (left == 2) {
				leftPairs.add(card);
			}
			if (right == 2) {
				rightPairs.add(card);
			}
		}
		
		if(leftPairs.size() > rightPairs.size()) {
			result = LEFT_WIN;
		}
		if(leftPairs.size() < rightPairs.size()) {
			result = RIGHT_WIN;
		}
	}

	private static void checkForHighestPair(Map<Integer, Integer> leftCounts, Map<Integer, Integer> rightCounts) {
		if (result != 0) {
			return;
		}

		for (int card = ACE; card >= 2; card--) {
			int left = leftCounts.getOrDefault(card, 0);
			int right = rightCounts.getOrDefault(card, 0);

			if ((left == 2) && (right < 2)) {
				System.out.println("Left wins by pair: " + card);
				result = LEFT_WIN;
				return;
			}

			if ((left < 2) && (right == 2)) {
				System.out.println("Right wins by pair: " + card);
				result = RIGHT_WIN;
				return;
			}
		}
	}

	public static void checkForHighestSingleCard(Map<Integer, Integer> leftCounts, Map<Integer, Integer> rightCounts) {
		if (result != 0) {
			return;
		}

		for (int card = ACE; card >= 2; card--) {
			int left = leftCounts.getOrDefault(card, 0);
			int right = rightCounts.getOrDefault(card, 0);

			if ((left == 1) && (right == 0)) {
				System.out.println("Left wins by single card: " + card);
				result = LEFT_WIN;
				return;
			}

			if ((left == 0) && (right == 1)) {
				System.out.println("Right wins by single card: " + card);
				result = RIGHT_WIN;
				return;
			}
		}
	}

	public static void displayCounts(Map<Integer, Integer> counts) {
		System.out.println("Counts: ");
		for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
			System.out.println("\t" + entry.getKey() + " " + entry.getValue());
		}
	}

	public static Map<Integer, Integer> createCountsMap(List<Integer> figures) {
		Map<Integer, Integer> counts = new TreeMap<>(Collections.reverseOrder());
		for (Integer figure : figures) {
			counts.put(figure, counts.getOrDefault(figure, 0) + 1);
		}
		return counts;
	}

	public static void displayFigures(List<Integer> figures) {
		System.out.print("Cards as ints: ");
		for (Integer integer : figures) {
			System.out.print(integer + " ");
		}
		System.out.println();
	}

	private static void notifyIfHandHasColor() {
		if (leftOneColor) {
			System.out.println("Left has color!");
		}
		if (rightOneColor) {
			System.out.println("Right has color!");
		}
	}

	private static List<String> getLeftHand(String handsAsString) {
		return Arrays.asList(handsAsString.split(" ")).subList(0, 5);
	}

	private static List<String> getRightHand(String handsAsString) {
		return Arrays.asList(handsAsString.split(" ")).subList(5, 10);
	}

	private static List<Integer> getFiguresAsIntegers(List<String> cardsAsStrings) {
		List<Integer> leftFigures = new ArrayList<>();
		for (String cardAsString : cardsAsStrings) {
			String figure = cardAsString.substring(0, 1);
			switch (figure) {
			case "T":
				leftFigures.add(10);
				break;
			case "J":
				leftFigures.add(JACK);
				break;
			case "Q":
				leftFigures.add(QUEEN);
				break;
			case "K":
				leftFigures.add(KING);
				break;
			case "A":
				leftFigures.add(ACE);
				break;
			default:
				leftFigures.add(Integer.parseInt(figure));
			}
		}
		return leftFigures;
	}

	private static boolean isInOneColor(List<String> hand) {
		char color = hand.get(0).charAt(1);
		for (String card : hand) {
			if (card.charAt(1) != color) {
				return false;
			}
		}
		return true;
	}
}
