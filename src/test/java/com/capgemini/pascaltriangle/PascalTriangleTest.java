package com.capgemini.pascaltriangle;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PascalTriangleTest {

	@Test(expected = IndexOutOfBoundsException.class)
	public void shouldThrowExceptionWhenColumnGreaterThanRow() {
		// given
		int row = 10;
		int column = 12;
		// when
		PascalTriangle.pascal(column, row);
		// then
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void shouldThrowExceptionWhenColumnIsNegative() {
		// given
		int row = 10;
		int column = -1;
		// when
		PascalTriangle.pascal(column, row);
		// then
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void shouldThrowExceptionWhenRowIsNegative() {
		// given
		int row = -1;
		int column = 2;
		// when
		PascalTriangle.pascal(column, row);
		// then
	}

	@Test
	public void shouldReturnOneForFirstInRow() {
		// given
		int row = 5;
		int column = 0;
		long expected = 1;
		// when
		long actual = PascalTriangle.pascal(column, row);
		// then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnOneForLastInRow() {
		// given
		int row = 5;
		int column = 5;
		long expected = 1;
		// when
		long actual = PascalTriangle.pascal(column, row);
		// then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnTwoForRowTwoColOne() {
		// given
		int row = 2;
		int column = 1;
		long expected = 2;
		// when
		long actual = PascalTriangle.pascal(column, row);
		// then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnTwoForRowNineColFour() {
		// given
		int row = 9;
		int column = 4;
		long expected = 126;
		// when
		long actual = PascalTriangle.pascal(column, row);
		// then
		assertEquals(expected, actual);
	}

}
