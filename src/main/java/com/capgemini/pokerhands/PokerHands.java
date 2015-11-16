package com.capgemini.pokerhands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PokerHands {
	private static boolean leftOneColor;
	private static boolean rightOneColor;

	private static void initializeFields() {

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

		return 0;
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
				leftFigures.add(11);
				break;
			case "Q":
				leftFigures.add(12);
				break;
			case "K":
				leftFigures.add(13);
				break;
			case "A":
				leftFigures.add(14);
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
