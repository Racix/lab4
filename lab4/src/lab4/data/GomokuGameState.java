/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer{

   // Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;
	
    //Possible game states
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHER_TURN = 2;
	private final int FINISHED = 3;
	private int currentState;
	
	private GomokuClient client;
	
	private String message;
	
	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}
				

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString(){
		return message;
	}
	
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid(){
		return gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y){
		if (currentState == FINISHED) {
			message = "YOU CANT MOVE THE GAME IS FINISHED";
		}
		else if (currentState == NOT_STARTED) {
			message = "YOU CANT MOVE WHEN THER IS NO ONGOING GAME";
		}
		else if (currentState == OTHER_TURN) {
			message = "ITS NOT YOUR TURN";
		}
		else {
			
			// inte säker att de fungerar   
			if (gameGrid.move(x, y,GameGrid.ME )==true ){
				message = "Empty spot no more";
				setChanged();
				notifyObservers();
				currentState = OTHER_TURN;
				if (gameGrid.isWinner(GameGrid.ME)==true) {
					message = "You won the game"; 
					setChanged();
					notifyObservers();
				}
			}
			else {
				message = "ocipide spot";
				setChanged();
				notifyObservers();
			}
			}
			
		}
	
	
	/**
	 * Starts a new game with the current client
	 */
	public void newGame(){
		gameGrid.clearGrid();
		currentState = OTHER_TURN;
		message = "NEW GAME STARTED by you";
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly
	 */
	public void receivedNewGame(){
		gameGrid.clearGrid();
		currentState = MY_TURN;
		message = "NEW GAME STATRED By the uther player";
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 */
	public void otherGuyLeft(){
		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "The other player has left the room";
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The player disconnects from the client
	 */
	public void disconnect(){
		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "You have left the room";
		setChanged();
		notifyObservers();		
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y){
		if (currentState == FINISHED) {
			message = "YOU CANT MOVE THE GAME IS FINISHED";
		}
		else if (currentState == NOT_STARTED) {
			message = "YOU CANT MOVE WHEN THER IS NO ONGOING GAME";
		}
		else if (currentState == MY_TURN) {
			message = "ITS NOT YOUR TURN";
		}
		else {
			
			if (gameGrid.move(x, y,GameGrid.OTHER )==true ){
				message = "Empty spot no more";
				setChanged();
				notifyObservers();
				currentState = OTHER_TURN;
				if (gameGrid.isWinner(GameGrid.OTHER)==true) {
					message = "OTHER won the game"; 
					setChanged();
					notifyObservers();
				}
			}
			else {
				message = "ocipide spot";
				setChanged();
				notifyObservers();
			}
			}
			
		}
	
	
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHER_TURN;
			break;
		}
		setChanged();
		notifyObservers();
		
		
	}
	
}
