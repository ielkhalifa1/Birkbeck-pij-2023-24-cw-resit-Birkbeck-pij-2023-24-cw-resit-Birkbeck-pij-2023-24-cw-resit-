package pij.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("============                       ============");
        System.out.println("============ P o l y o m i n o e s ============");
        System.out.println("============                       ============");
        Scanner scanner = new Scanner(System.in);
        List<Polyomino> polyominoes;

        while (true) {
            System.out.println("Would you like to _l_oad polyominoes or use the _d_efault ones?");
            System.out.print("Please enter your choice (l/d): ");
            String choice = scanner.nextLine();

            if (choice.equals("d")) {
                polyominoes = loadDefaultPolyominoes();
                break;
            } else if (choice.equals("l")) {
                while (true) {
                    System.out.print("Please enter the file name of the file with the polyominoes: ");
                    String filePath = scanner.nextLine();
                    polyominoes = loadPolyominoesFromFile(filePath);
                    if (polyominoes != null) break;
                    System.out.println("This is not a valid file.");
                }
                break;
            } else {
                System.out.println("Please enter 'l' or 'd'.");
            }
        }

        System.out.println("Would you like to play an _o_pen or a _c_losed game?");
        System.out.print("Please enter your choice (o/c): ");
        boolean isOpenGame = scanner.nextLine().equals("o");

        int rows, cols;
        while (true) {
            System.out.print("Please enter the number of rows for the board (min 1, max 99): ");
            rows = scanner.nextInt();
            if (rows >= 1 && rows <= 99) break;
            System.out.println("Please enter a valid number of rows.");
        }

        while (true) {
            System.out.print("Please enter the number of columns for the board (min 1, max 26): ");
            cols = scanner.nextInt();
            if (cols >= 1 && cols <= 26) break;
            System.out.println("Please enter a valid number of columns.");
        }
        scanner.nextLine(); // Consume the newline

        Game game = new Game(rows, cols, polyominoes, isOpenGame);
        game.start(scanner);
    }


    private static List<Polyomino> loadDefaultPolyominoes() {
        return loadPolyominoesFromFile("resources/defaultPolyominoes.txt");
    }

    private static List<Polyomino> loadPolyominoesFromFile(String fileName) {
        List<Polyomino> polyominoes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            List<String> shapeLines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.equals("--")) {
                    if (!shapeLines.isEmpty()) {
                        char[][] shape = shapeLinesToCharArray(shapeLines);
                        polyominoes.add(new Polyomino(shape));
                        shapeLines.clear();
                    }
                } else {
                    shapeLines.add(line);
                }
            }
            // Add the last polyomino if the file does not end with "--"
            if (!shapeLines.isEmpty()) {
                char[][] shape = shapeLinesToCharArray(shapeLines);
                polyominoes.add(new Polyomino(shape));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
        return polyominoes;
    }

    private static char[][] shapeLinesToCharArray(List<String> shapeLines) {
        int rows = shapeLines.size();
        int cols = shapeLines.get(0).length();
        char[][] shape = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            shape[i] = shapeLines.get(i).toCharArray();
        }
        return shape;
    }
}
