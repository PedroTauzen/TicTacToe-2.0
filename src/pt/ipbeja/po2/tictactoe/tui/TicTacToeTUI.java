package pt.ipbeja.po2.tictactoe.tui;

import pt.ipbeja.po2.tictactoe.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Pedro Tauzen
 * @version 24/06/2023
 */

public class TicTacToeTUI implements View {

    private static final Scanner SCANNER = new Scanner(System.in);
    private final TicTacToeGame game;

    //private Player currentPlayer;

    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame();
        TicTacToeTUI ticTacToeTui = new TicTacToeTUI(game);
        ticTacToeTui.play();
    }

    public TicTacToeTUI(TicTacToeGame game) {
        this.game = game;
        this.game.setGameView(this);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void play() {
        System.out.println("Do you want to load the board from a file? (y/n)");
        String answer = SCANNER.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            boolean loaded = false;
            while (!loaded) {
                //loaded = loadBoardFromFile();
                if (!loaded) {
                    System.out.println("Error loading the file. Please try again.");
                }
            }
        } else {
            this.game.startGame();
        }

        //currentPlayer = Player.X; // Definir o jogador X como o primeiro a jogar.

        while (true) {
            Position position = readPlayInput();
            game.positionSelected(position);
        }
    }

    private Position readPlayInput() {
        System.out.println("Insert play (row,col) -> 0,2");
        String s = SCANNER.nextLine();
        String[] split = s.split(",");
        return new Position(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    @Override
    public void onBoardMarkChanged(Mark place, Position position) {
        // unused here
    }

    @Override
    public void onBoardCreated(Mark[][] board) {
        //currentPlayer = Player.X; // Definir o jogador X como o primeiro a jogar
        //System.out.println("Player " + currentPlayer + " to play:");
        drawBoard(board);
    }


    @Override
    public void onBoardChanged(Mark[][] board) {
        //currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X; // Alternar entre jogadores
        //System.out.println("Player " + currentPlayer + " to play:");
        drawBoard(board);
    }

    private void drawBoard(Mark[][] board) {
        for (Mark[] places : board) {
            for (Mark place : places) {
                switch (place) {
                    case X_MARK:
                        System.out.print("X");
                        break;
                    case O_MARK:
                        System.out.print("O");
                        break;
                    case EMPTY:
                        System.out.print("_");
                        break;
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void onGameWon(Player place) {
        askPlayAgain("Player " + place + " won!");
    }

    @Override
    public void onGameDraw() {
        askPlayAgain("Game ended in a draw");
    }

    private void askPlayAgain(String gameOverMessage) {
        System.out.println(gameOverMessage);
        System.out.println("Would you like to play again? (y/n)");
        String answer = SCANNER.nextLine();
        if(answer.equalsIgnoreCase("n")) {
            System.exit(0);
        }
        else {
            play();
        }
    }

    /*private boolean loadBoardFromFile() {
        System.out.println("Enter the file name:");
        String fileName = SCANNER.nextLine();
        String filePath = "src/resources/" + fileName + ".txt";
        Path path = Paths.get(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            int size = 0;
            int row = 0;
            Mark[][] board = null;
            int xCount = 0;
            int oCount = 0;

            while ((line = reader.readLine()) != null) {
                if (size == 0) {
                    size = line.length();
                    board = new Mark[size][size];
                }
                for (int col = 0; col < size; col++) {
                    char symbol = line.charAt(col);
                    Mark mark;
                    if (symbol == 'X') {
                        mark = Mark.X_MARK;
                        xCount++;
                    } else if (symbol == 'O') {
                        mark = Mark.O_MARK;
                        oCount++;
                    } else {
                        mark = Mark.EMPTY;
                    }
                    board[row][col] = mark;
                }
                row++;
            }

            game.initializeBoard(size);
            game.setBoard(board);

            System.out.println("Board loaded successfully!");

            Player nextPlayer = (xCount > oCount) ? Player.O : Player.X;
            game.setCurrentPlayer(nextPlayer);

            System.out.println("Next player to play: " + nextPlayer);
            return true;

        } catch (IOException e) {
            System.out.println("Error loading the file: " + fileName);
            return false;
        }
    }*/
}
