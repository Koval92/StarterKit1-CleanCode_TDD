package com.capgemini.placeToSplit;

/**
 * Given a non-empty array, return true if there is a place to split the array so that the sum of the numbers on one side is equal to the sum of the numbers on the other side.
 * Example:
 * {{{
 * canBalance({1, 1, 1, 2, 1}) → true
 * canBalance({2, 1, 1, 2, 1}) → false
 * canBalance({10, 10}) → true
 * }}}
 */
public final class PlaceToSplit {
    private PlaceToSplit() {
    }

    public static boolean canBalance(int[] nums) {
    	if(nums == null || nums.length==0) {
    		return false;
    	} else {
    		int sum = calculateSum(nums);
    		
    		if(sum % 2 != 0) {
    			return false; // we can't divide odd number into two integers
    		}
    		
    		return isThereHalfOfSumAfterOneOfElems(nums, sum);
    	}
    }

	private static boolean isThereHalfOfSumAfterOneOfElems(int[] nums, int sum) {
		int halfOfSum = sum/2;
		int sumUpToI = 0;
		
		for (int num : nums) {
			sumUpToI += num;
			if(sumUpToI==halfOfSum) {
				return true;
			}
		}
		return false;
	}

	private static int calculateSum(int[] nums) {
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		return sum;
	}
}
