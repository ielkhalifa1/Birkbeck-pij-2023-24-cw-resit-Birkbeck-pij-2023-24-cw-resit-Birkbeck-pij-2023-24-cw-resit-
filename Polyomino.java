package pij.main;

import java.util.Arrays;

public class Polyomino {
    private char[][] shape;
    private int rows;
    private int cols;

    public Polyomino(char[][] shape) {
        this.shape = shape;
        this.rows = shape.length;
        this.cols = Arrays.stream(shape).mapToInt(row -> row.length).max().orElse(0);
    }

    public void rotate() {
        int maxCols = Arrays.stream(shape).mapToInt(row -> row.length).max().orElse(0);
        int newRows = maxCols;
        int newCols = rows;
        char[][] rotatedShape = new char[newRows][newCols];

        for (char[] row : rotatedShape) {
            Arrays.fill(row, ' ');
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                rotatedShape[j][rows - 1 - i] = shape[i][j];
            }
        }

        shape = rotatedShape;
        rows = newRows;
        cols = newCols;
    }

    public char[][] getShape() {
        return shape;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getSize() {
        int size = 0;
        for (char[] row : shape) {
            for (char c : row) {
                if (c == 'O') {
                    size++;
                }
            }
        }
        return size;
    }

    public static void main(String[] args) {
        char[][] shape = {
                {'O'},
                {'O', 'O'},
                {'O'}
        };

        Polyomino polyomino = new Polyomino(shape);
        System.out.println("Original shape:");
        printShape(polyomino.getShape());

        polyomino.rotate();
        System.out.println("Rotated shape:");
        printShape(polyomino.getShape());

        polyomino.rotate();
        System.out.println("Rotated shape again:");
        printShape(polyomino.getShape());

        polyomino.rotate();
        System.out.println("Rotated shape again:");
        printShape(polyomino.getShape());

        polyomino.rotate();
        System.out.println("Rotated shape again:");
        printShape(polyomino.getShape());

        System.out.println("Polyomino size: " + polyomino.getSize());
    }

    public static void printShape(char[][] shape) {
        for (char[] row : shape) {
            for (char c : row) {
                System.out.print(c == ' ' ? ' ' : c);
            }
            System.out.println();
        }
        System.out.println("--");
    }
}
