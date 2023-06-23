package pt.ipbeja.po2.tictactoe.model;

/**
 * @author Pedro Tauzen
 * @version 24/06/2023
 */

/**
 * This Player enum has two possible values: X and O
 * In this game we have one PLayer X and one Player O
 * Both receive one Mark as a parameter
 * X player receives X_MARK; O player receives O_MARK
 * The complete names are Mark.X_MARK and Mark.O_MARK
 * because they belong to enum Mark.
 */
public enum Player {
    X(Mark.X_MARK), O(Mark.O_MARK);

    private final Mark mark;

    Player(Mark mark) {
        this.mark = mark;
    }

    public Mark mark() {
        return mark;
    }
}
