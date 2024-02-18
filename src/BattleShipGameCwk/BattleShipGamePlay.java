package BattleShipGameCwk;

import javax.swing.JOptionPane;

public class BattleShipGamePlay {
    public static void main(String[] args) {
        // Create a new grid to pay the game
        gridSetup newGrid = new gridSetup();
        BattleShipGamePlay gamePlay = new BattleShipGamePlay();

        // Call playGame method to start the game
        gamePlay.playGame(newGrid);
    }

    public void playGame(gridSetup grid) {
        int turns = 0;
       

        // Continue the game until the player has used all turns or sunk all ships
        while (turns < 10 && !grid.areAllShipsSunk()) {
            // Display the play grid and get the input from the user
            grid.printGrid();

            // checks the input and updates the score if needed
            processPlayerTurn(grid);
            turns++;
        }

        grid.calculateScore();
        
    }


    private void processPlayerTurn(gridSetup grid) {
        // Get the player's input
    	String input = grid.input.toUpperCase();

    	// Validating input format
    	if (input.length() == 2 && input.charAt(0) >= 'A' && input.charAt(0) <= 'J' &&
    	        input.charAt(1) >= '0' && input.charAt(1) <= '9') {

    	    // Extracting the row and column indices from the input
    	    int row = input.charAt(0) - 'A';
    	    int col = input.charAt(1) - '0';

    	    // Checking if there is a ship at the selected square
    	    if (grid.grid[row][col] != '-') {
    	        // Ship hit
    	        Ship hitShip = findHitShip(grid.ships, row, col);
    	        int points = hitShip.points;

    	        // Checking if the ship is sunk and update its status
    	        if (!hitShip.isSunk()) {
        	        // Updating points and mark the ship as sunk
        	        grid.updatePoints(points);
        	        JOptionPane.showMessageDialog(null, "Hit! You scored " + points + " points for hitting " + hitShip.name);

    	            hitShip.setSunk(true);
    	        }
    	        else {
    	        	JOptionPane.showMessageDialog(null, hitShip.name + " already sunk. Hit another");
    	        }
    	    } else {
    	        // Missed
    	        JOptionPane.showMessageDialog(null, "Missed! No ship at this location.");
    	    }
    	} else {
    	    // Invalid input format
    	    JOptionPane.showMessageDialog(null, "Invalid input format. Please enter a letter (A-J) followed by a number (0-9).");
    	}
    }


    private Ship findHitShip(Ship[] ships, int row, int col) {
        // Iterate through ships to find the one at the specified location
        for (int s = 0; s < ships.length; s++) {
            Ship ship = ships[s];
            for (int i = 0; i < ship.gridLoc.length; i++) {
                if (ship.gridLoc[i][0] == row && ship.gridLoc[i][1] == col) {
                    return ship;
                }
            }
        }
        return null;
    }


}
