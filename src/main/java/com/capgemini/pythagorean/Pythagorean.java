package com.capgemini.pythagorean;

/**
 * A Pythagorean triplet is a set of three natural numbers, a < b < c, for
 * which, a2 + b2 = c2 For example, 3^2 + 4^2 = 9 + 16 = 25 = 5^2. There exists
 * exactly one Pythagorean triplet for which a + b + c = 1000. Find the product
 * abc.
 */
public class Pythagorean {
	// a < b < c, and natural numbers
	// if a=0, then b=c (because a^2+b^2=c^2)
	private static final int MIN_A = 1;
	private static final int MIN_B = 2;
	private static final int MIN_C = 3;

	public static int[] findTriple(int sum) {
		for (int c = MIN_C; c < sum; c++) {
			for (int b = MIN_B; b < c; b++) {
				for (int a = MIN_A; a < b; a++) {
					if (a + b + c == sum) {
						if (areTriple(a, b, c)) {
							return new int[] { a, b, c };
						}
					}
				}
			}
		}

		return null;
	}

	private static boolean areTriple(int a, int b, int c) {
		return (a * a) + (b * b) == (c * c);
	}
}
