package pt.ipbeja.po2.tictactoe.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import pt.ipbeja.po2.tictactoe.model.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Creates the Board of the Game
 * @author Pedro Tauzen
 * @version 23/06/2023
 */

public class TicTacToeBoard extends GridPane implements View {

    private final TicTacToeGame game;
    private TicTacToeButton[][] buttons;

    public TicTacToeBoard(TicTacToeGame game, List<String> file) {
        this.game = game;
        this.game.setGameView(this);

        this.game.startGame();
        this.game.markPositionBoard(file);
    }

    @Override
    public void onBoardMarkChanged(Mark place, Position position) {
        int row = position.row();
        int col = position.col();
        TicTacToeButton button = buttons[row][col];
        button.setPlace(place);
    }

    @Override
    public void onBoardCreated(Mark[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        getChildren().clear(); // redraw buttons
        //ButtonHandler handler = new ButtonHandler(); // to be used with class ButtonHandler
        EventHandler<ActionEvent> handler = event -> {
            TicTacToeButton source = (TicTacToeButton) event.getSource();
            Position position = getPosition(source);
            TicTacToeBoard.this.game.positionSelected(position);
            // ou apenas this.game.positionSelected(position);
        };
        this.buttons = new TicTacToeButton[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                TicTacToeButton button = new TicTacToeButton();
                button.setOnAction(handler);
                add(button, col, row);
                buttons[row][col] = button;
            }
        }
    }

    @Override
    public void onBoardChanged(Mark[][] board) {
        // ignorado aqui, mas útil na text user interface
    }


    @Override
    public void onGameWon(Player player) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "O jogador " + player + " venceu!");
        alert.showAndWait();
        game.startGame();
    }

    @Override
    public void onGameDraw() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "O jogo terminou empatado.");
        alert.showAndWait();
        game.startGame();
    }

    private void setButtonsHighlighted(List<Position> selectedPositions, boolean highlighted) {
        for (Position position : selectedPositions) {
            TicTacToeButton button = buttons[position.row()][position.col()];
            button.setHighlighted(highlighted);
        }
    }

    /**
     * Get node position in gridpane
     * @param node node to get position
     * @return node position
     */
    private Position getPosition(Node node) {
        // or you could store the coordinates in the TicTacToeButton, as we did in the first versions
        Integer row = GridPane.getRowIndex(node);
        Integer col = GridPane.getColumnIndex(node);
        return new Position(row, col);
    }

    class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            TicTacToeButton source = (TicTacToeButton) event.getSource();
            Position position = getPosition(source);
            TicTacToeBoard.this.game.positionSelected(position);
            // ou apenas game.positionSelected(position);
        }
    }

}
