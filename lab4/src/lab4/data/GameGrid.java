package lab4.data;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{

	int[][] gridSize;
	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = 2;
	int INROW = 5;
	
	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		gridSize = new int[size][size];
		for(int y = 0; y < size; y++) {
			for(int x = 0; x < size; x++) {
				gridSize[y][x] = EMPTY;
			}
		}
		/*
		gridSize[2][5] = 1;
		gridSize[3][4] = 1;
		gridSize[4][3] = 1;
		gridSize[5][2] = 1;
		gridSize[6][1] = 1;
		*/
	}
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y){
		return gridSize[y][x];
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return gridSize.length;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player){
		if(gridSize[y][x] == EMPTY) {
			if(player == ME) {
				gridSize[y][x] = ME;
			} else {
				gridSize[y][x] = OTHER;
			}
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
	}
	
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid(){
		for(int y = 0; y < gridSize.length; y++) {
			for(int x = 0; x < gridSize.length; x++) {
				gridSize[y][x] = EMPTY;
			}
		}
	}
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		int xcount = 0;
		int ycount = 0;
		int diaRcount = 0;
		int diaLcount = 0;
		for(int y = 0; y < gridSize.length;y++) {
			xcount = 0;
			ycount = 0;
			for(int x = 0; x < gridSize.length;x++) {
				if(xcount != INROW) {
					if(player == gridSize[y][x]) {
						xcount ++;
					} else {
						xcount = 0;
					}
				} else {
					return true;
				}
				
				if(ycount != INROW) {
					if(player == gridSize[x][y]) {
						ycount ++;
					} else {
						ycount = 0;
					}
				}else {
					return true;
				}
				if(diaRcount != INROW) {
					if(player == gridSize[y][x]) {
						int next = 0;
						if ((gridSize.length - x) < (gridSize.length - y)) {
							next = gridSize.length - x; 
						} else {
							next = gridSize.length - y; 
						}
						for(int i = 0; i < next; i++) {
							if(diaRcount != INROW) {
								if(player == gridSize[y+i][x+i]) {
									diaRcount ++;
								} else {
									diaRcount = 0;
								}
							} else {
								return true;
							}
						}	
					}
					diaRcount = 0;
				} else {
					return true;
				}
				if (diaLcount != INROW) {
					if(player == gridSize[y][x]) {
						int next = 0;
						if ((x) < (gridSize.length - y)) {
							next = x + 1; 
						} else {
							next = gridSize.length - y; 
						}
						for(int i = 0; i < next; i++) {
							if(diaLcount != INROW) {
								if(player == gridSize[y+i][x-i]) {
									diaLcount ++;
								} else {
									diaLcount = 0;
								}
							} else {
								return true;
							}
						}
					}
					diaLcount = 0;
				}else {
					return true;
				}
			}
			
		}
		return false;
	}
	
	public static void main(String[] args) {
		GameGrid test = new GameGrid(10);
		System.out.println(test.isWinner(ME));
		System.out.println(test.getSize());
	}
	
}
