package com.capgemini.placeToSplit;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlaceToSplitTest {

	@Test
	public void shouldReturnFalseForNull() {
		//given
		int[] nums = null;
		//when
		boolean result = PlaceToSplit.canBalance(nums);
		//then
		assertFalse(result);
	}
	
	@Test
	public void shouldReturnFalseForEmpty() {
		//given
		int[] nums = new int[0];
		//when
		boolean result = PlaceToSplit.canBalance(nums);
		//then
		assertFalse(result);
	}
	
	@Test
	public void shouldReturnTrueForOneZeroElement() {
		//given
		int[] nums = new int[]{0};
		//when
		boolean result = PlaceToSplit.canBalance(nums);
		//then
		assertTrue(result);
	}
	
	@Test
	public void shouldReturnTrueForTwoEqualElements() {
		//given
		int[] nums = new int[]{10, 10};
		//when
		boolean result = PlaceToSplit.canBalance(nums);
		//then
		assertTrue(result);
	}

	@Test
	public void shouldReturnFalseForOneNonZeroElement() {
		//given
		int[] nums = new int[]{5};
		//when
		boolean result = PlaceToSplit.canBalance(nums);
		//then
		assertFalse(result);
	}
	
	@Test
	public void shouldReturnTrueForFirstDataSet() {
		//given
		int[] nums = new int[]{1, 1, 1, 2, 1};
		//when
		boolean result = PlaceToSplit.canBalance(nums);
		//then
		assertTrue(result);
	}
	
	@Test
	public void shouldReturnFalseForSecondDataSet() {
		//given
		int[] nums = new int[]{2, 1, 1, 2, 1};
		//when
		boolean result = PlaceToSplit.canBalance(nums);
		//then
		assertFalse(result);
	}
}
