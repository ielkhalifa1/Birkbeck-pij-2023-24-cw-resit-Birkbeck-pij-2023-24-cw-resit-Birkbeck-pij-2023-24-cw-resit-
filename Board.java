package pij.main;

import java.util.*;

public class Board {
    private char[][] grid;
    private int rows;
    private int cols;
    private Random random;
    private List<PlacedPolyomino> placedPolyominoes;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(grid[i], '.');
        }
        this.random = new Random();
        this.placedPolyominoes = new ArrayList<>();
    }

    public boolean placePolyomino(Polyomino p) {
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                positions.add(new int[]{i, j});
            }
        }
        Collections.shuffle(positions, random);

        for (int[] position : positions) {
            int startRow = position[0];
            int startCol = position[1];
            for (int rotation = 0; rotation < 4; rotation++) {
                if (canPlacePolyomino(p, startRow, startCol)) {
                    placePolyominoAt(p, startRow, startCol);
                    int polySize = p.getSize();
                    placedPolyominoes.add(new PlacedPolyomino(startRow, startCol, p.getRows(), p.getCols(), polySize));
//                    Polyomino.printShape(p.getShape());
                    return true;
                }
                p.rotate();
            }
        }
        return false;
    }

    private boolean canPlacePolyomino(Polyomino p, int startRow, int startCol) {
        char[][] shape = p.getShape();
        int polyRows = shape.length;

        // Check if polyomino fits within board boundaries
        if (startRow + polyRows > rows) {
            return false;
        }

        // Check if polyomino overlaps or touches edges with existing polyominoes
        for (int i = 0; i < polyRows; i++) {
            int polyCols = shape[i].length;
            if (startCol + polyCols > cols) {
                return false;
            }
            for (int j = 0; j < polyCols; j++) {
                if (shape[i][j] == 'O') {
                    if (grid[startRow + i][startCol + j] != '.') {
                        return false;
                    }
                    // Check surrounding cells for touching edges
                    if ((startRow + i > 0 && grid[startRow + i - 1][startCol + j] == 'O') ||
                            (startRow + i < rows - 1 && grid[startRow + i + 1][startCol + j] == 'O') ||
                            (startCol + j > 0 && grid[startRow + i][startCol + j - 1] == 'O') ||
                            (startCol + j < cols - 1 && grid[startRow + i][startCol + j + 1] == 'O')) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void placePolyominoAt(Polyomino p, int startRow, int startCol) {
        char[][] shape = p.getShape();
        int polyRows = shape.length;

        for (int i = 0; i < polyRows; i++) {
            int polyCols = shape[i].length;
            for (int j = 0; j < polyCols; j++) {
                if (shape[i][j] == 'O') {
                    grid[startRow + i][startCol + j] = 'O';
                }
            }
        }
    }

    public void coverTile(int x, int y) {
        if (grid[y][x] == '.') {
            grid[y][x] = 'X';
        } else if (grid[y][x] == 'O') {
            grid[y][x] = 'V';
            updateCoveredPolyomino(x, y);
        }
    }

    private void updateCoveredPolyomino(int x, int y) {
        for (PlacedPolyomino polyomino : placedPolyominoes) {
            if (isTileWithinPolyomino(polyomino, x, y)) {
                polyomino.incrementCoveredTiles();
                break;
            }
        }
    }

    private boolean isTileWithinPolyomino(PlacedPolyomino polyomino, int x, int y) {
        int startRow = polyomino.getStartRow();
        int startCol = polyomino.getStartCol();
        int endRow = startRow + polyomino.getRows();
        int endCol = startCol + polyomino.getCols();
        return x >= startCol && x < endCol && y >= startRow && y < endRow;
    }

    public boolean isCovered(int x, int y) {
        return grid[y][x] == 'X' || grid[y][x] == 'V';
    }

    public boolean isPolyominoTile(int x, int y) {
        return grid[y][x] == 'V';
    }

    public boolean isFullyCoveredPolyomino(int x, int y) {
        for (PlacedPolyomino polyomino : placedPolyominoes) {
            if (isTileWithinPolyomino(polyomino, x, y) && polyomino.isFullyCovered()) {
                return true;
            }
        }
        return false;
    }

    public int getPolyominoSize(int x, int y) {
        for (PlacedPolyomino polyomino : placedPolyominoes) {
            if (isTileWithinPolyomino(polyomino, x, y)) {
                return polyomino.getTotalTiles();
            }
        }
        return 0;
    }

    public void displayBoard(boolean isOpenGame) {
        System.out.print(" ");
        for (char c = 'a'; c < 'a' + cols; c++) {
            System.out.print(c);
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < cols; j++) {
                char displayChar = grid[i][j];
                if (displayChar == 'O' && !isOpenGame) {
                    displayChar = '.';
                }
                System.out.print(displayChar);
            }
            System.out.println(i + 1);
        }

        System.out.print(" ");
        for (char c = 'a'; c < 'a' + cols; c++) {
            System.out.print(c);
        }
        System.out.println();
    }

    public boolean allPolyominoesCovered() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 'O') {
                    return false;
                }
            }
        }
        return true;
    }
}
