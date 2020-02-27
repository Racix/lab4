package lab4.gui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;

/**
 * The GUI class
 * 
 * @author Abboshon Hamraliev & Adam Joakim Hedberg
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	private GamePanel gameGridPanel;
	private JFrame frame;
	private JButton connectButton, newGameButton, disconnectButton;
	private JLabel messageLabel;
	private JPanel panel;

	
	private void frame() {
		frame = new JFrame("Gomoku");
		gameGridPanel = new GamePanel(gamestate.getGameGrid());
		connectButton = new JButton("Connect");
		newGameButton = new JButton("New Game");
		disconnectButton = new JButton("Disconnect");
		messageLabel = new JLabel("Welcom to Gomoku");
		panel = new JPanel(new BorderLayout());
		
		panel.add(gameGridPanel,BorderLayout.NORTH);
		panel.add(connectButton,BorderLayout.WEST);
		panel.add(newGameButton,BorderLayout.CENTER);
		panel.add(disconnectButton,BorderLayout.EAST);
		panel.add(messageLabel,BorderLayout.SOUTH);
		
		frame.add(panel);
		frame.pack();
		//frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ConnectionWindow(client);

            }
        });
		
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	                gamestate.newGame();

            }
        });

        disconnectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamestate.disconnect();
            }
        });
        
        gameGridPanel.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent me) {
            	int[] coordinates = gameGridPanel.getGridPosition(me.getX(),me.getY());
            	System.out.println(coordinates[0] + "," + coordinates[1]);
            	gamestate.move(coordinates[0],coordinates[1]);
        	}
        });
		
		
	}
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		frame();
	}
	
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
}

