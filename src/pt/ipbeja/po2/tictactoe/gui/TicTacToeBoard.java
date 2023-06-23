package pt.ipbeja.po2.tictactoe.gui;

import javafx.scene.layout.GridPane;
import pt.ipbeja.po2.tictactoe.model.TicTacToeGame;

public class TicTacToeBoard extends GridPane {

    private final TicTacToeGame game;
    private TicTacToeButton[][] buttons;

    public TicTacToeBoard(TicTacToeGame game) {
        this.game = game;
    }
}
