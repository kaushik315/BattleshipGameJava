package BattleShipGameCwk;

class Ship {
    char name;
    int noOfSq;
    int points;
    int[][] gridLoc;
    boolean sunk;

    public Ship(char name, int noOfSq, int points, int[][] shipGridLoc) {
        this.name = name;
        this.noOfSq = noOfSq;
        this.points = points;
        this.gridLoc = shipGridLoc;
        this.sunk = false;
    }

    public boolean isSunk() {
        return sunk;
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }

}
