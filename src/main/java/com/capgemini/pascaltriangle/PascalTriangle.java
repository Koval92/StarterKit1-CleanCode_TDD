package com.capgemini.pascaltriangle;

/**
 * Created by ldrygala on 2015-01-23.
 * 0                     1
 * 1                   1   1
 * 2                 1   2   1
 * 3               1   3   3   1
 * 4             1   4   6   4   1
 * 5           1   5   10  10   5   1
 * 6         1   6   15  20  15   6   1
 * 7       1   7   21  35  35   21  7   1
 * 8     1   8   28  56  70  56   28  8   1
 * 9   1   9  36   84  126 126  84  36  9   1
 */
public class PascalTriangle {
    public static long pascal(int column, int row) {
    	checkBounds(column, row);
    	int[][] pascalTriangle = generateTriangle(row+1); //rows are numbered from 0
  
        return pascalTriangle[row][column];
    }

	private static int[][] generateTriangle(int numberOfRows) {
		int[][] pascalTriangle = new int[numberOfRows][numberOfRows];
    	for (int i = 0; i<numberOfRows; i++) {
			fillBoundariesOfRow(pascalTriangle, i);
			fillInsideOfRow(pascalTriangle, i);
		}
		return pascalTriangle;
	}

	private static void fillInsideOfRow(int[][] pascalTriangle, int row) {
		for(int j = 1; j<row; j++) {
			pascalTriangle[row][j]=pascalTriangle[row-1][j-1]+pascalTriangle[row-1][j];
		}
	}

	private static void fillBoundariesOfRow(int[][] pascalTriangle, int row) {
		pascalTriangle[row][0] = 1; //first in row
		pascalTriangle[row][row] = 1; //last in row
	}

	@SuppressWarnings("unused")
	private static void display(int[][] pascalTriangle) {
		for (int[] is : pascalTriangle) {
			for (int i : is) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}

	private static void checkBounds(int column, int row) {
		if(column>row)
    		throw new IndexOutOfBoundsException("Column is greater than row");
    	if(column<0) // row < 0 is covered by previous check
    		throw new IndexOutOfBoundsException("Column is negative");
	}
}
