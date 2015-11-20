package com.capgemini.gameoflife;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameOfLifeTest {

	@Before
	public void initialize() {
	}
	
	@Test
	public void shouldReturnFalseForOutOfBound() {
		GameOfLife game = new GameOfLife(5, 5);
		game.set(4, 4, true);
		assertEquals(true, game.get(4, 4));
		
		assertEquals(false, game.get(-1, 0));
		assertEquals(false, game.get(0, -1));
		assertEquals(false, game.get(5, 0));
		assertEquals(false, game.get(0, 5));
	}
	
	@Test
	public void shouldReturnCorrectNumberOfNeighbors() {
		GameOfLife game = new GameOfLife(3, 3);
		
		game.set(0, 0, true);
		game.set(1, 1, true);
		game.set(1, 2, true);
		game.set(2, 1, true);
		
		assertEquals(1, game.countNeighbors(0, 0));
		assertEquals(3, game.countNeighbors(0, 1));
		assertEquals(2, game.countNeighbors(0, 2));
		assertEquals(3, game.countNeighbors(1, 0));
		assertEquals(3, game.countNeighbors(1, 1));
		assertEquals(2, game.countNeighbors(1, 2));
		assertEquals(2, game.countNeighbors(2, 0));
		assertEquals(2, game.countNeighbors(2, 1));
		assertEquals(3, game.countNeighbors(2, 2));
	}
	
	@Test
	public void shouldCalculateNext() {
		GameOfLife game = new GameOfLife(3, 3);
		
		game.set(0, 0, true);
		game.set(1, 1, true);
		game.set(1, 2, true);
		game.set(2, 1, true);
		
		game.displayState();
		
		for(int i = 0; i < 10; i++) {
			System.out.println("Iteration " + i);
			game.calculateNextState();
			game.displayState();
		}
	}
	
	@Test
	public void shouldOscilateForBlinker() {
		GameOfLife game = new GameOfLife(3, 3);
		
		game.set(1, 0, true);
		game.set(1, 1, true);
		game.set(1, 2, true);
		
		game.displayState();
		
		for(int i = 0; i < 10; i++) {
			System.out.println("Iteration " + i);
			game.calculateNextState();
			game.displayState();
		}
	}
}
