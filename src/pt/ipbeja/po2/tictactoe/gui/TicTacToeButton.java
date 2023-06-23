package pt.ipbeja.po2.tictactoe.gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pt.ipbeja.po2.tictactoe.model.Mark;

/**
 * Creates the Buttons
 * @author Pedro Tauzen
 * @version 24/06/2023
 */

public class TicTacToeButton extends Button {

    public static final Image EMPTY = new Image("resources/noplayer.png");
    public static final Image PLAYER_X = new Image("resources/player1.png");
    public static final Image PLAYER_O = new Image("resources/player2.png");

    private static final String HIGHLIGHTED_STYLE = "-fx-background-color: #ff0000; ";

    private final ImageView imageView;

    public TicTacToeButton() {
        this.imageView = new ImageView(EMPTY);
        this.setHighlighted(false);
        this.setGraphic(this.imageView);
    }

    public void setX() {
        this.imageView.setImage(PLAYER_X);
    }

    public void setO() {
        this.imageView.setImage(PLAYER_O);
    }

    public void setEmpty() {
        imageView.setImage(EMPTY);
    }

    public void setPlace(Mark place) {
        switch (place) {
            case X_MARK:
                setX();
                break;
            case O_MARK:
                setO();
                break;
            case EMPTY:
                setEmpty();
                break;
        }
    }

    public void setHighlighted(boolean highlight) {
        setStyle(highlight ? HIGHLIGHTED_STYLE : null);
    }
}
