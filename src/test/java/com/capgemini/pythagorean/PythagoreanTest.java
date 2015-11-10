package com.capgemini.pythagorean;

import static org.junit.Assert.*;

import org.junit.Test;

public class PythagoreanTest {

	@Test
	public void shouldReturnNullFor0() {
		// given
		int sum = 0;
		// when
		int actuals[] = Pythagorean.findTriple(sum);
		// then
		assertNull(actuals);
	}

	@Test
	public void shouldWorkFor12() {
		// given
		int sum = 12;
		int expecteds[] = new int[] { 3, 4, 5 };
		// when
		int actuals[] = Pythagorean.findTriple(sum);
		// then
		assertArrayEquals(expecteds, actuals);
	}

	@Test
	public void shouldWorkFor1000() {
		// given
		int sum = 1000;
		int expecteds[] = { 200, 375, 425 };
		// when
		int actuals[] = Pythagorean.findTriple(sum);
		// then
		assertTrue(actuals != null);
		assertArrayEquals(expecteds, actuals);
	}
}
