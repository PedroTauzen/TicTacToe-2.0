package pt.ipbeja.po2.tictactoe.gui;

import javafx.scene.layout.GridPane;
import pt.ipbeja.po2.tictactoe.model.TicTacToeGame;
import pt.ipbeja.po2.tictactoe.model.View;

/**
 * Creates the Board of the Game
 * @author Pedro Tauzen
 * @version 23/06/2023
 */

public class TicTacToeBoard extends GridPane implements View {

    private final TicTacToeGame game;
    private TicTacToeButton[][] buttons;

    public TicTacToeBoard(TicTacToeGame game) {
        this.game = game;
    }
}
