package com.capgemini.pokerhands;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class PokerHandsTest {

	@Test
	public void test() {
		PokerHands.compare("TC TS TC 9H 4S 7D 2S 5D 3S AC");
	}

	@Test
	public void colorTest() {
		PokerHands.compare("8C TC KC 9C 4C 7D 2S 5D 3S AC");
		assertTrue(PokerHands.isLeftOneColor());
		assertFalse(PokerHands.isRightOneColor());
	}

}
