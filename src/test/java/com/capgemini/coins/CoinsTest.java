package com.capgemini.coins;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CoinsTest {

	@Test
	public void shouldReturn2InitialPairsFor110100() {
		// given
		List<Integer> coins = new ArrayList<>(Arrays.asList(1, 1, 0, 1, 0, 0));
		int expected = 2;
		// when
		int actual = Coins.countPairs(coins);
		// then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturn4PairsFor110100() {
		// given
		List<Integer> coins = new ArrayList<>(Arrays.asList(1, 1, 0, 1, 0, 0));
		int expected = 4;
		// when
		int actual = Coins.solution(coins);
		// then
		assertEquals(expected, actual);
	}
}
