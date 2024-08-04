package pij.main;

public class PlacedPolyomino {
    private int startRow;
    private int startCol;
    private int rows;
    private int cols;
    private int coveredTiles;
    private int totalTiles;

    public PlacedPolyomino(int startRow, int startCol, int rows, int cols, int totalTiles) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.rows = rows;
        this.cols = cols;
        this.coveredTiles = 0;
        this.totalTiles = totalTiles;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getCoveredTiles() {
        return coveredTiles;
    }

    public void incrementCoveredTiles() {
        this.coveredTiles++;
    }

    public boolean isFullyCovered() {
        return coveredTiles == totalTiles;
    }

    public int getTotalTiles() {
        return totalTiles;
    }
}
