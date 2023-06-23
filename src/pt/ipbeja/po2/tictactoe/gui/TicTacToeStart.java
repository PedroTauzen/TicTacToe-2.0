package pt.ipbeja.po2.tictactoe.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipbeja.po2.tictactoe.model.TicTacToeGame;

/**
 * Start GUI version
 * @author Pedro Tauzen
 * @version 24/06/2023
 */

public class TicTacToeStart extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TicTacToeGame game = new TicTacToeGame();
        TicTacToeBoard board = new TicTacToeBoard(game);
        Scene scene = new Scene(board);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
