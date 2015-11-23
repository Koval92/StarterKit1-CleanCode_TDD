package com.capgemini.bowling;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BowlingGameTest {

	private BowlingGame game;

	@Before
	public void initialize() {
		game = new BowlingGame();
	}

	@Test
	public void shouldReturnZeroForAllMisses() {
		rollMany(20, 0);
		assertEquals(0, game.getScore());
	}

	@Test
	public void shouldReturnTwentyForAllOnes() {
		rollMany(20, 1);
		assertEquals(20, game.getScore());
	}

	@Test
	public void canRollSpare() {
		game.roll(5);
		game.roll(5);
		game.roll(3);
		rollMany(17, 0);
		assertEquals(16, game.getScore());
	}

	@Test
	public void canRollStrike() {
		game.roll(10);
		game.roll(4);
		game.roll(3);
		rollMany(16, 0);
		assertEquals(24, game.getScore());
	}

	@Test
	public void canRollAllStrikes() {
		rollMany(12, 10);
		assertEquals(300, game.getScore());
	}

	@Test
	public void canRollAllStrikesWithFourAndFiveInBonusThrows() {
		rollMany(10, 10);
		game.roll(4);
		game.roll(5);
		game.displayRolls();
		assertEquals(283, game.getScore());
	}

	@Test
	public void canRollAllStrikesWithoutLast() {
		rollMany(9, 10);
		game.roll(0);
		game.roll(10);
		game.roll(5);
		game.displayRolls();
		assertEquals(265, game.getScore());
	}

	public void rollMany(int rollsCount, int pins) {
		for (int rollNumber = 0; rollNumber < rollsCount; rollNumber++) {
			game.roll(pins);
		}
	}
}
