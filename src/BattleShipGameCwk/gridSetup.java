package BattleShipGameCwk;

import java.util.Random;

import javax.swing.JOptionPane;

public class gridSetup {
    public static final int gridSize = 10;
    public char[][] grid = new char[gridSize][gridSize];
    public String input;
    public Ship[] ships;
    private int score = 0;
    private boolean debugMode = false;
    

    public gridSetup() {
        initializeGrid();

        // Ask the user if they want to enable debug mode
        String debugInput = JOptionPane.showInputDialog("Do you want to enable debug mode? (yes/no): ");
        if (debugInput != null && debugInput.trim().equalsIgnoreCase("yes")) {
            debugMode = true;
        }

        ships = new Ship[]{
                new Ship('A', 5, 2, new int[5][2]),
                new Ship('B', 4, 4, new int[4][2]),
                new Ship('S', 3, 6, new int[3][2]),
                new Ship('D', 2, 8, new int[2][2]),
                new Ship('P', 1, 10, new int[1][2])
        };
        placeShipsRandomly();
    }

    private void initializeGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = '-';
            }
        }
    }

    public void placeShipsRandomly() {
        Random rand = new Random();

        for (int i = 0; i < ships.length; i++) {
            Ship ship = ships[i];
            int shipSize = ship.noOfSq;
            int[][] shipGridLoc = new int[shipSize][2];

            boolean placed = false;
            while (!placed) {
                int startRow = rand.nextInt(gridSize);
                int startCol = rand.nextInt(gridSize);
                boolean isHorizontal = rand.nextBoolean();

                placed = tryPlaceShip(ship, shipGridLoc, startRow, startCol, isHorizontal);
            }

            ship.gridLoc = shipGridLoc;
        }
    }

    private boolean tryPlaceShip(Ship ship, int[][] shipGridLoc, int startRow, int startCol, boolean isHorizontal) {
        int currentRow = startRow;
        int currentCol = startCol;
        
        //Searching for the coordinates for the ship to be placed
        for (int i = 0; i < shipGridLoc.length; i++) {
            if (currentRow >= gridSize || currentCol >= gridSize || grid[currentRow][currentCol] != '-') {
                return false; // Ship placement failed
            }

            shipGridLoc[i][0] = currentRow;
            shipGridLoc[i][1] = currentCol;

            if (isHorizontal) {
                currentCol++;
            } else {
                currentRow++;
            }
        }

        // Placing the ship on the grid
        for (int i = 0; i < shipGridLoc.length; i++) {
            grid[shipGridLoc[i][0]][shipGridLoc[i][1]] = ship.name;
        }

        return true; 
    }

    public void printGrid() {
        String output = "\n      0    1    2    3    4    5    6    7    8    9\n";
        char rowpointer = 'A';

        for (int i = 0; i < gridSize; i++) {
            output += "\n" + rowpointer + "     ";
            for (int j = 0; j < gridSize; j++) {
                if (debugMode || grid[i][j] == '-') {
                    output += grid[i][j] + "     ";
                } else {
                    output += "-     ";
                }
            }
            rowpointer++;
        }

        input = JOptionPane.showInputDialog(output + "\n\nWhere do you want to fire (Range: A0 - J9): ");
    }

    public boolean areAllShipsSunk() {
        for (int i = 0; i < ships.length; i++) {
            Ship ship = ships[i];
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }


    public void updatePoints(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    public void calculateScore() {
        int turnScore = 0;

        for (int i = 0; i < ships.length; i++) {
            Ship ship = ships[i];
            if (ship.isSunk() && !shipAlreadySunk(ship)) {
                turnScore += ship.getPoints();
                markShipAsSunk(ship);
            }
        }

        score += turnScore;
        //Display the result
        if (areAllShipsSunk()) {
            JOptionPane.showMessageDialog(null, "All ships have been sunk! The game is over.\nYour score: " + score);
            System.exit(0); 
        }
        else {
        	JOptionPane.showMessageDialog(null, "Out of turns. Game over.\nYour total score is: " + score);
        }
    }
    
    // Check if ship is sunk before
    private boolean shipAlreadySunk(Ship ship) {
        return ship.isSunk() && ship.getPoints() > 0;
    }
    
    // Ensures no duplicate scoring
    private void markShipAsSunk(Ship ship) {
        ship.setSunk(true);
        ship.setPoints(0);
    }

}
