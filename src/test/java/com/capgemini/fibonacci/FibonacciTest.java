package com.capgemini.fibonacci;

import static org.junit.Assert.*;

import org.junit.Test;

public class FibonacciTest {

	@Test
	public void shouldReturnZeroForLessThanOne() {
		//given
		int number = 0;
		//when
		long expected = 0;
		long actual = Fibonacci.fib(number);
		//then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnOneForOne() {
		//given
		int number = 1;
		//when
		long expected = 1;
		long actual = Fibonacci.fib(number);
		//then
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldReturnOneForTwo() {
		//given
		int number = 2;
		//when
		long expected = 1;
		long actual = Fibonacci.fib(number);
		//then
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldReturnTwoForThree() {
		//given
		int number = 3;
		//when
		long expected = 2;
		long actual = Fibonacci.fib(number);
		//then
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldReturn233For13() {
		//given
		int number = 13;
		//when
		long expected = 233;
		long actual = Fibonacci.fib(number);
		//then
		assertEquals(expected, actual);
	}
}
