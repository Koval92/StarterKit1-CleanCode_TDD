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
	private static TreeMap<Integer, Integer> leftCounts;
	private static TreeMap<Integer, Integer> rightCounts;
	private static boolean leftIsStraight;
	private static boolean rightIsStraight;

	private static void initializeFields(String handsAsString) {
		result = 0;

		List<String> leftHand = getLeftHand(handsAsString);
		List<String> rightHand = getRightHand(handsAsString);

		leftOneColor = isInOneColor(leftHand);
		rightOneColor = isInOneColor(rightHand);

		List<Integer> leftFigures = getFiguresAsIntegers(leftHand);
		List<Integer> rightFigures = getFiguresAsIntegers(rightHand);

		displayFigures(leftFigures);
		displayFigures(rightFigures);

		leftCounts = createCountsTreeMap(leftFigures);
		rightCounts = createCountsTreeMap(rightFigures);
		
		leftIsStraight = isStraight(leftCounts);
		rightIsStraight = isStraight(rightCounts);
	}
	
	// returns 1 when left hand wins, and -1, if right one
	public static int compare(String handsAsString) {
		initializeFields(handsAsString);

		System.out.println("Current hands: " + handsAsString);
		notifyIfHandHasColor();

		displayCounts(leftCounts);
		displayCounts(rightCounts);

		// royal flush is straight flush, but with greater values
		checkForStraightFlush();
		checkForFourOfAKind();
		checkForFullHouse();
		checkForFlush();
		checkForStraight();
		checkForThreeOfAKind();
		checkForTwoPairs();
		checkForHighestPair();
		checkForHighestSingleCard();

		if (result == 0) {
			System.out.println("No one wins!");
		}
		
		System.out.println();

		return result;
	}

	private static void checkForStraightFlush() {
		boolean leftIsStraightFlush = leftIsStraight && leftOneColor;
		boolean rightIsStraightFlush = rightIsStraight && rightOneColor;
		
		System.out.println(leftIsStraightFlush);
		System.out.println(rightIsStraightFlush);
		
		if(leftIsStraightFlush && !rightIsStraightFlush) {
			System.out.println("Left wins by straight flush");
			result = LEFT_WIN;
		}
		if(!leftIsStraightFlush && rightIsStraightFlush) {
			System.out.println("Right wins by straight flush");
			result = RIGHT_WIN;
		}
		
		// if both have straight flush, then checkForStraight() finds higher
	}

	private static void checkForFourOfAKind() {
		if (result != 0) {
			return;
		}

		for (int card = ACE; card >= 2; card--) {
			int leftCount = leftCounts.getOrDefault(card, 0);
			int rightCount = rightCounts.getOrDefault(card, 0);

			if ((leftCount == 4) && (rightCount < 4)) {
				System.out.println("Left wins by four of a kind!");
				result = LEFT_WIN;
				return;
			}

			if ((leftCount < 4) && (rightCount == 4)) {
				System.out.println("Right wins by four of a kind!");
				result = RIGHT_WIN;
				return;
			}
		}
	}

	private static void checkForFullHouse() {
		if (result != 0) {
			return;
		}
		
		boolean leftIsFull = leftCounts.containsValue(2) && leftCounts.containsValue(3);
		boolean rightIsFull = rightCounts.containsValue(2) && rightCounts.containsValue(3);
		
		if(leftIsFull && !rightIsFull) {
			System.out.println("Left wins by full");
			result = LEFT_WIN;
			return;
		}
		if(!leftIsFull && rightIsFull) {
			System.out.println("Right wins by full");
			result = RIGHT_WIN;
			return;
		}
		
		// when both have full, one wins by greater triple - checkForThreeOfAKind()
	}

	private static void checkForFlush() {
		if (result != 0) {
			return;
		}

		if (leftOneColor && !rightOneColor) {
			System.out.println("Left wins by color!");
			result = LEFT_WIN;
		}
		if (!leftOneColor && rightOneColor) {
			System.out.println("Right wins by color!");
			result = RIGHT_WIN;
		}
	}

	private static void checkForStraight() {
		if (result != 0) {
			return;
		}

		if (leftIsStraight && !rightIsStraight) {
			System.out.println("Left wins by straight");
			result = LEFT_WIN;
		} else if (!leftIsStraight && rightIsStraight) {
			System.out.println("Right wins by straight");
			result = RIGHT_WIN;
		}
		
		// when both are straight, one wins by greater single value - checkForHighestSingleCard()
	}

	public static boolean isStraight(TreeMap<Integer, Integer> counts) {
		int highestCard = counts.firstKey();
		int lowestCard = counts.lastKey();
		int countsDifference = highestCard - lowestCard;
		boolean isStraight = (counts.size() == 5) && (countsDifference == 4);
		return isStraight;
	}

	private static void checkForThreeOfAKind() {
		if (result != 0) {
			return;
		}

		for (int card = ACE; card >= 2; card--) {
			int leftCount = leftCounts.getOrDefault(card, 0);
			int rightCount = rightCounts.getOrDefault(card, 0);

			if ((leftCount == 3) && (rightCount < 3)) {
				System.out.println("Left wins by triple: " + card);
				result = LEFT_WIN;
				return;
			}

			if ((leftCount < 3) && (rightCount == 3)) {
				System.out.println("Right wins by triple: " + card);
				result = RIGHT_WIN;
				return;
			}
		}
	}

	private static void checkForTwoPairs() {
		if (result != 0) {
			return;
		}

		List<Integer> leftPairs = new ArrayList<>();
		List<Integer> rightPairs = new ArrayList<>();

		for (int card = ACE; card >= 2; card--) {
			int leftCount = leftCounts.getOrDefault(card, 0);
			int rightCount = rightCounts.getOrDefault(card, 0);

			if (leftCount == 2) {
				leftPairs.add(card);
			}
			if (rightCount == 2) {
				rightPairs.add(card);
			}
		}

		if (leftPairs.size() > rightPairs.size()) {
			System.out.println("Left wins by pairs: " + leftPairs.toString());
			result = LEFT_WIN;
		}
		if (leftPairs.size() < rightPairs.size()) {
			System.out.println("Right wins by pairs: " + rightPairs.toString());
			result = RIGHT_WIN;
		}
	}

	private static void checkForHighestPair() {
		if (result != 0) {
			return;
		}

		for (int card = ACE; card >= 2; card--) {
			int leftCount = leftCounts.getOrDefault(card, 0);
			int rightCount = rightCounts.getOrDefault(card, 0);

			if ((leftCount == 2) && (rightCount < 2)) {
				System.out.println("Left wins by pair: " + card);
				result = LEFT_WIN;
				return;
			}

			if ((leftCount < 2) && (rightCount == 2)) {
				System.out.println("Right wins by pair: " + card);
				result = RIGHT_WIN;
				return;
			}
		}
	}

	public static void checkForHighestSingleCard() {
		if (result != 0) {
			return;
		}

		for (int card = ACE; card >= 2; card--) {
			int leftCount = leftCounts.getOrDefault(card, 0);
			int rightCount = rightCounts.getOrDefault(card, 0);

			if ((leftCount == 1) && (rightCount == 0)) {
				System.out.println("Left wins by single card: " + card);
				result = LEFT_WIN;
				return;
			}

			if ((leftCount == 0) && (rightCount == 1)) {
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

	public static TreeMap<Integer, Integer> createCountsTreeMap(List<Integer> figures) {
		TreeMap<Integer, Integer> counts = new TreeMap<>(Collections.reverseOrder());
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
		List<Integer> figures = new ArrayList<>();
		for (String cardAsString : cardsAsStrings) {
			String figure = cardAsString.substring(0, 1);
			switch (figure) {
			case "T":
				figures.add(10);
				break;
			case "J":
				figures.add(JACK);
				break;
			case "Q":
				figures.add(QUEEN);
				break;
			case "K":
				figures.add(KING);
				break;
			case "A":
				figures.add(ACE);
				break;
			default:
				figures.add(Integer.parseInt(figure));
			}
		}
		return figures;
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
