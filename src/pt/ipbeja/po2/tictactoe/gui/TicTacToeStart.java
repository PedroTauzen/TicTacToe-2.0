package pt.ipbeja.po2.tictactoe.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import pt.ipbeja.po2.tictactoe.model.TicTacToeGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Tic Tac Toe Game GUI Version
 * Based on the provided Textual Version
 * By Pedro Tauzen
 * Date: 24/06/2023
 */
public class TicTacToeStart extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        TicTacToeGame game = new TicTacToeGame();
        game.fileChooser(primaryStage);

        List<String> file = game.readFile(game.getPath());

        TicTacToeBoard board = new TicTacToeBoard(game, file);
        Scene scene = new Scene(board);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
