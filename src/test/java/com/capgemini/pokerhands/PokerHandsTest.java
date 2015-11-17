package com.capgemini.pokerhands;

import static org.junit.Assert.*;

import org.junit.Test;

public class PokerHandsTest {
	// colors: C S H D
	//sorted by winning
	
	private static final String TWO_PAIR_SEVEN_THREE_SINGLE_FOUR = "7C 7S 3S 3D 4H";
	private static final String TWO_PAIR_SIX_THREE_HIGHEST_EIGHT = "6C 6S 3S 3D 8H";
	private static final String PAIR_EIGHT_HIGHEST_QUEEN = "8C 8D QC 3S 4H";
	private static final String PAIR_SIX_HIGHEST_KING = "6C 6D KC 3S 4H";
	private static final String PAIR_SIX_HIGHEST_FOUR = "6C 6D 2C 3S 4H";
	private static final String PAIR_FIVE_HIGHEST_SEVEN = "7C 2H 5D 3C 5S";
	private static final String HIGHEST_ACE_OTHER_LOWEST = "2C 3H 4S 5D AC";
	private static final String HIGHEST_KING_OTHER_HIGH = "KC QS 8D TH 9C";
	private static final String HIGHEST_KING_OTHER_LOW = "2C 8H KC 5D 6C";

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
}
