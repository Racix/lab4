package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

/**
 * 
 * Main class that starts the program.
 * 
 * @author Abboshon Hamraliev & Adam Joakim Hedberg
 *
 */
public class GomokuMain {

	public static void main(String[] args) {
		int portNumber;
        try {
            portNumber = Integer.parseInt(args[0]);
            System.out.println(portNumber);
        }
        catch(Exception e) {
            portNumber = 4000;
        }
		GomokuClient gc = new GomokuClient(portNumber);
		GomokuClient gc1 = new GomokuClient(5);
		GomokuGameState ggs = new GomokuGameState(gc);	
		GomokuGameState ggs1 = new GomokuGameState(gc1);	
		new GomokuGUI(ggs, gc);
		new GomokuGUI(ggs1, gc1);

	}

}
