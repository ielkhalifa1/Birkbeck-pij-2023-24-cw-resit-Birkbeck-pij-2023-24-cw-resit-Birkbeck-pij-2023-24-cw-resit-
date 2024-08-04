package pij.main;

import java.util.*;

public class Game {
    private Board board;
    private List<Polyomino> polyominoes;
    private List<Polyomino> unusedPolyominoes;
    private int score;
    private boolean isOpenGame;
    private Random random;

    public Game(int rows, int cols, List<Polyomino> polyominoes, boolean isOpenGame) {
        this.board = new Board(rows, cols);
        this.polyominoes = polyominoes;
        this.unusedPolyominoes = new ArrayList<>(polyominoes);
        this.score = 0;
        this.isOpenGame = isOpenGame;
        this.random = new Random();
    }

    public void start(Scanner scanner) {
        placePolyominoes();
        displayUnusedPolyominoes();
        board.displayBoard(isOpenGame);
        System.out.println("Score: " + score);

        while (true) {
            String input;
            while (true) {
                System.out.print("It's your turn! Please enter a position to cover (e.g., a1): ");
                input = scanner.nextLine();
                if (isValidMove(input)) break;
                System.out.println("Please enter a valid move (e.g., a1).");
            }

            int x = input.charAt(0) - 'a';
            int y = Integer.parseInt(input.substring(1)) - 1;

            board.coverTile(x, y);
            updateScore(x, y);
            board.displayBoard(isOpenGame);
            System.out.println("Score: " + score);

            if (board.allPolyominoesCovered()) {
                break;
            }
        }

        if (score > 0) {
            System.out.println("The human player wins!");
        } else {
            System.out.println("The computer player wins!");
        }
        System.out.println("Final score: " + score);
    }

    private boolean isValidMove(String move) {
        if (move.length() < 2 || move.length() > 3) return false;
        char col = move.charAt(0);
        if (col < 'a' || col > 'z') return false;
        try {
            int row = Integer.parseInt(move.substring(1));
            if (row < 1 || row > 99) return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void placePolyominoes() {
        Collections.shuffle(polyominoes);
        for (Polyomino polyomino : polyominoes) {
            if (random.nextBoolean() && board.placePolyomino(polyomino)) {
                score -= polyomino.getSize() * polyomino.getSize();
                unusedPolyominoes.remove(polyomino);
            }
        }
    }

    private void updateScore(int x, int y) {
        if (board.isCovered(x, y)) {
            score += board.isPolyominoTile(x, y) ? 2 : -1;
            if (board.isFullyCoveredPolyomino(x, y)) {
                score += 3 * board.getPolyominoSize(x, y) + 5;
            }
        }
    }

    private void displayUnusedPolyominoes() {
        System.out.println("There are " + unusedPolyominoes.size() + " unused polyominoes:");
        for (Polyomino polyomino : unusedPolyominoes) {
            char[][] shape = polyomino.getShape();
            for (char[] row : shape) {
                for (char c : row) {
                    System.out.print(c == '\0' ? ' ' : c);
                }
                System.out.println();
            }
            System.out.println("--");
        }
    }
}
