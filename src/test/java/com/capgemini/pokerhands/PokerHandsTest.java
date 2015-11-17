package com.capgemini.pokerhands;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class PokerHandsTest {
	// colors: C S H D
	// sorted by winning

	// royal flush	
	private static final String ROYAL_FLUSH = "AC QC KC JC TC";
	// straight flush
	private static final String STRAIGHT_FLUSH_FROM_KING = "9S QS TS KS JS";
	private static final String STRAIGHT_FLUSH_FROM_TEN = "9D TD 8D 6D 7D";
	// four of a kind (quadra)
	private static final String QUADRA_ACE_SINGLE_QUEEN = "AS AH QH AC AD";
	private static final String QUADRA_FOUR_SINGLE_QUEEN = "4S 4H QS 4C 4D";
	// full house
	private static final String FULL_HOUSE_QUEEN_TWO = "QS QH 2S 2C QD";
	private static final String FULL_HOUSE_TWO_QUEEN = "QS 2H 2S 2C QD";
	// flush
	private static final String FLUSH_HIGHEST_EIGHT_NEXT_SEVEN = "8D 3D 2D 7D 5D";
	private static final String FLUSH_HIGHEST_EIGHT_NEXT_FIVE = "8S 3S 2S 4S 5S";
	// straight
	private static final String STRAIGHT_FROM_ACE = "AS QD TS KH JC";
	private static final String STRAIGHT_FROM_SIX = "6S 4H 5D 2D 3C";
	// three of a kind (triple)
	private static final String TRIPLE_TEN_SINGLE_TWO_FOUR = "TS 2H TD 4C TH";
	private static final String TRIPLE_THREE_SINGLE_ACE_KING = "3S 3H AD 3D KH";
	// two pairs
	private static final String TWO_PAIR_SEVEN_THREE_SINGLE_FOUR = "7C 7S 3S 3D 4H";
	private static final String TWO_PAIR_SIX_THREE_HIGHEST_EIGHT = "6C 6S 3S 3D 8H";
	// pairs
	private static final String PAIR_EIGHT_HIGHEST_QUEEN = "8C 8D QC 3S 4H";
	private static final String PAIR_SIX_HIGHEST_KING = "6C 6D KC 3S 4H";
	private static final String PAIR_SIX_HIGHEST_FOUR = "6S 6H 2C 3S 4H";
	private static final String PAIR_FIVE_HIGHEST_SEVEN = "7C 2H 5D 3C 5S";
	// high cards
	private static final String HIGHEST_ACE_OTHER_LOWEST = "2C 3H 4S 5D AC";
	private static final String HIGHEST_KING_OTHER_HIGH = "KC QS 8D TH 9C";
	private static final String HIGHEST_KING_OTHER_LOW = "2C 8H KC 5D 6C";

	// Left wins: 376, right wins: 624
	@Test
	public void shouldWorkForFileWithHands() throws IOException {
		File file = new File(".\\src\\main\\java\\com\\capgemini\\pokerhands\\poker.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader br = new BufferedReader(fileReader);

		int leftWins = 0;
		int rightWins = 0;

		String hand;
		while ((hand = br.readLine()) != null) {
			int result = PokerHands.compare(hand);

			if (result == PokerHands.LEFT_WIN) {
				leftWins++;
			} else if (result == PokerHands.RIGHT_WIN) {
				rightWins++;
			}
		}

		System.out.println("Left wins: " + leftWins + ", right wins: " + rightWins);

		br.close();

	}

	// High Card tests

	@Test
	public void shouldReturnLeftWinForHigherSingleValueOnLeft() {
		int actual = PokerHands.compare(HIGHEST_ACE_OTHER_LOWEST + " " + HIGHEST_KING_OTHER_HIGH);
		assertEquals(PokerHands.LEFT_WIN, actual);
	}

	@Test
	public void shouldReturnRightForEqualHighestCardButHigherNextOnRight() {
		int actual = PokerHands.compare(HIGHEST_KING_OTHER_LOW + " " + HIGHEST_KING_OTHER_HIGH);
		assertEquals(PokerHands.RIGHT_WIN, actual);
	}

	// One Pair tests

	@Test
	public void shouldReturnLeftForPairVsHighCard() {
		int actual = PokerHands.compare(PAIR_FIVE_HIGHEST_SEVEN + " " + HIGHEST_ACE_OTHER_LOWEST);
		assertEquals(PokerHands.LEFT_WIN, actual);
	}

	@Test
	public void shouldReturnRightForLowPairVsHighPair() {
		int actual = PokerHands.compare(PAIR_FIVE_HIGHEST_SEVEN + " " + PAIR_SIX_HIGHEST_FOUR);
		assertEquals(PokerHands.RIGHT_WIN, actual);
	}

	@Test
	public void shouldReturnLeftForEqualPairAndHigherSingleValueOnLeft() {
		int actual = PokerHands.compare(PAIR_SIX_HIGHEST_KING + " " + PAIR_SIX_HIGHEST_FOUR);
		assertEquals(PokerHands.LEFT_WIN, actual);
	}

	// Two Pairs tests

	@Test
	public void shouldReturnLeftForTwoPairsVsSingleHigherCard() {
		int actual = PokerHands.compare(TWO_PAIR_SEVEN_THREE_SINGLE_FOUR + " " + HIGHEST_KING_OTHER_HIGH);
		assertEquals(PokerHands.LEFT_WIN, actual);
	}

	@Test
	public void shouldReturnLeftForTwoPairsVsSinglePair() {
		int actual = PokerHands.compare(TWO_PAIR_SEVEN_THREE_SINGLE_FOUR + " " + PAIR_EIGHT_HIGHEST_QUEEN);
		assertEquals(PokerHands.LEFT_WIN, actual);
	}

	@Test
	public void shouldReturnLeftForHigherPairOnLeftAndEqualLowPair() {
		int actual = PokerHands.compare(TWO_PAIR_SEVEN_THREE_SINGLE_FOUR + " " + TWO_PAIR_SIX_THREE_HIGHEST_EIGHT);
		assertEquals(PokerHands.LEFT_WIN, actual);
	}

	// Three of a Kind tests

	@Test
	public void shouldReturnRightForTwoPairsVsLowerTriple() {
		int actual = PokerHands.compare(TWO_PAIR_SEVEN_THREE_SINGLE_FOUR + " " + TRIPLE_THREE_SINGLE_ACE_KING);
		assertEquals(PokerHands.RIGHT_WIN, actual);
	}

	@Test
	public void shouldReturnLeftForTripleVsLowerTriple() {
		int actual = PokerHands.compare(TRIPLE_TEN_SINGLE_TWO_FOUR + " " + TRIPLE_THREE_SINGLE_ACE_KING);
		assertEquals(PokerHands.LEFT_WIN, actual);
	}

	// Straight tests
	
	@Test
	public void shouldReturnRightForStraightVsTriple() {
		int actual = PokerHands.compare(TRIPLE_TEN_SINGLE_TWO_FOUR + " " + STRAIGHT_FROM_SIX);
		assertEquals(PokerHands.RIGHT_WIN, actual);
	}
	
	@Test
	public void shouldReturnLeftForHighStraightVsLowStraight() {
		int actual = PokerHands.compare(STRAIGHT_FROM_ACE + " " + STRAIGHT_FROM_SIX);
		assertEquals(PokerHands.LEFT_WIN, actual);
	}

	// Flush tests
	
	@Test
	public void shouldReturnRightForTripleVsFlush() {
		int actual = PokerHands.compare(TRIPLE_TEN_SINGLE_TWO_FOUR + " " + FLUSH_HIGHEST_EIGHT_NEXT_FIVE);
		assertEquals(PokerHands.RIGHT_WIN, actual);
	}

	@Test
	public void shouldReturnLeftForFlushVsStraight() {
		int actual = PokerHands.compare(FLUSH_HIGHEST_EIGHT_NEXT_FIVE + " " + STRAIGHT_FROM_ACE);
		assertEquals(PokerHands.LEFT_WIN, actual);
	}

	@Test
	public void shouldReturnLeftForFlushWithHigherValuesOnRight() {
		int actual = PokerHands.compare(FLUSH_HIGHEST_EIGHT_NEXT_FIVE + " " + FLUSH_HIGHEST_EIGHT_NEXT_SEVEN);
		assertEquals(PokerHands.RIGHT_WIN, actual);
	}

	// Full House tests
	
	@Test
	public void shouldReturnLeftForFullVsFlush() {
		int actual = PokerHands.compare(FULL_HOUSE_QUEEN_TWO + " " + FLUSH_HIGHEST_EIGHT_NEXT_SEVEN);
		assertEquals(PokerHands.LEFT_WIN, actual);
	}
	
	@Test
	public void shouldReturnRightForFullVsLowerFull() {
		int actual = PokerHands.compare(FULL_HOUSE_TWO_QUEEN + " " + FULL_HOUSE_QUEEN_TWO);
		assertEquals(PokerHands.RIGHT_WIN, actual);
	}

	// Four of a Kind tests
	
	@Test
	public void shouldReturnLeftForQuadraVsTwoPairs() {
		int actual = PokerHands.compare(QUADRA_FOUR_SINGLE_QUEEN + " " + TWO_PAIR_SEVEN_THREE_SINGLE_FOUR);
		assertEquals(PokerHands.LEFT_WIN, actual);
	}

	@Test
	public void shouldReturnRightForHighQuadraVsLowQuadra() {
		int actual = PokerHands.compare(QUADRA_FOUR_SINGLE_QUEEN + " " + QUADRA_ACE_SINGLE_QUEEN);
		assertEquals(PokerHands.RIGHT_WIN, actual);
	}

	@Test
	public void shouldReturnLeftForQuadraVsFullHouse() {
		int actual = PokerHands.compare(QUADRA_ACE_SINGLE_QUEEN + " " + FULL_HOUSE_QUEEN_TWO);
		assertEquals(PokerHands.LEFT_WIN, actual);
	}

	// Straight Flush tests
	
	@Test
	public void shouldReturnLeftForStraightFlushVsFullHouse() {
		int actual = PokerHands.compare(STRAIGHT_FLUSH_FROM_KING + " " + FULL_HOUSE_QUEEN_TWO);
		assertEquals(PokerHands.LEFT_WIN, actual);
	}
	
	@Test
	public void shouldReturnRightForHighStraightFlushVsLowStraightFlush() {
		int actual = PokerHands.compare(STRAIGHT_FLUSH_FROM_TEN + " " + STRAIGHT_FLUSH_FROM_KING);
		assertEquals(PokerHands.RIGHT_WIN, actual);
	}

	// Royal Flush tests
	@Test
	public void shouldReturnRightForFlushVsRoyalFlush() {
		int actual = PokerHands.compare(STRAIGHT_FLUSH_FROM_KING + " " + ROYAL_FLUSH);
		assertEquals(PokerHands.RIGHT_WIN, actual);
	}
}
