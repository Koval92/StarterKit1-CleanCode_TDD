package com.capgemini.gameoflife;

import java.util.ArrayList;
import java.util.List;

public class GameOfLife {
	private List<List<Boolean>> map;

	public GameOfLife(int rows, int cols) {
		map = new ArrayList<>();
		for (int i = 0; i < rows; i++) {
			List<Boolean> row = new ArrayList<>();
			for (int j = 0; j < cols; j++) {
				row.add(false);
			}
			map.add(row);
		}
	}

	public void calculateNextState() {
		List<List<Boolean>> nextMap = new ArrayList<>();
		
		for (int i = 0; i < map.size(); i++) {
			List<Boolean> row = new ArrayList<>();
			for (int j = 0; j < map.get(i).size(); j++) {
				boolean lives = get(i, j);
				boolean shouldLive = lives;
				int neighborsCount = countNeighbors(i, j);

				if (!lives && neighborsCount == 3) {
					shouldLive = true;
				} else if (lives && (neighborsCount == 2 || neighborsCount == 3)) {
					shouldLive = true;
				} else {
					shouldLive = false;
				}

				row.add(shouldLive);
			}
			nextMap.add(row);
		}

		map = nextMap;
	}

	public void set(int row, int col, boolean liveness) {
		map.get(row).set(col, liveness);
	}

	public boolean get(int row, int col) {
		if (row < 0 || col < 0 || row >= map.size() || col >= map.get(row).size()) {
			return false;
		} else {
			return map.get(row).get(col);
		}
	}

	public int countNeighbors(int row, int col) {
		int neighbors = 0;

		for (int i = row - 1; i < row + 2; i++) {
			for (int j = col - 1; j < col + 2; j++) {
				if (get(i, j) && (i != row || j != col)) {
					neighbors++;
				}
			}
		}

		return neighbors;
	}

	public void displayNumberOfNeighbors() {
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).size(); j++) {
				System.out.print(countNeighbors(i, j) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void displayState() {
		for (List<Boolean> row : map) {
			for (Boolean field : row) {
				System.out.print((field ? "X" : "-") + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
