package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board
 * 
 * @author Abboshon Hamraliev & Adam Joakim Hedberg
 */

public class GamePanel extends JPanel implements Observer{

	private final int UNIT_SIZE = 20;
	private GameGrid grid;
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid){
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y){
		return new int[] {(x/UNIT_SIZE),(y/UNIT_SIZE)};
	}
	
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	private Color color( int red, int green, int blue) {
		return new Color(red, green, blue);
		
	}
	
	private void drawBoard(Graphics g) {
		g.setColor(color(0, 98, 255));
		g.fillRect(0,0, grid.getSize()*UNIT_SIZE, grid.getSize()*UNIT_SIZE);
		g.setColor(color(181, 209, 255));
		
		
		for( int gx = 0; gx <= grid.getSize()*UNIT_SIZE; gx+=UNIT_SIZE){
		g.drawLine(gx, 0, gx, grid.getSize()*UNIT_SIZE);
		}
		
		for( int gy = 0; gy <= grid.getSize()*UNIT_SIZE; gy+=UNIT_SIZE){
		g.drawLine(0, gy, grid.getSize()*UNIT_SIZE,gy);
		}
	}
	
	private void player(Graphics g) {
		for(int y = 0; y < grid.getSize();y++) {
			for(int x = 0; x < grid.getSize();x++) {
				if(grid.getLocation(x,y) == GameGrid.ME) {
					g.drawOval(x*UNIT_SIZE,y*UNIT_SIZE,UNIT_SIZE,UNIT_SIZE);
				} else if(grid.getLocation(x,y) == GameGrid.OTHER) {
					g.drawLine(x*UNIT_SIZE, y*UNIT_SIZE, x*UNIT_SIZE+UNIT_SIZE, y*UNIT_SIZE+ UNIT_SIZE);
					g.drawLine(x*UNIT_SIZE+UNIT_SIZE, y*UNIT_SIZE, x*UNIT_SIZE, y*UNIT_SIZE+ UNIT_SIZE);
				}
			}
		}
	}
	/**
	 * Method that draws the Components
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawBoard(g);
		player(g);
	}
	
}
