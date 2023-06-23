package pt.ipbeja.po2.tictactoe.model;

import java.util.Arrays;

/**
 * Tic Tac Toe board and game logic
 *
 * @author Pedro Tauzen
 * @version 24/06/2023
 */
public class TicTacToeGame {

    public static final int DEFAULT_SIZE = 3;
    private final int size;

    private int turnCounter;
    private Mark[][] board;
    private View view;
    private Position selectedPosition;

    private Player currentPlayer;

    public TicTacToeGame() {
        this(DEFAULT_SIZE);
        currentPlayer = Player.X;
    }

    public TicTacToeGame(int size) {
        this.size = size;
    }

    /**
     * Starts a new game
     */
    public void startGame() {
        this.turnCounter = 0;
        this.createBoard();
    }

    /**
     * Creates a new game board with Place.EMPTY and notifies listener
     */
    private void createBoard() {
        board = new Mark[size][size];
        for (int row = 0; row < size; row++) {
            Arrays.fill(board[row], Mark.EMPTY);
        }
        this.notifyBoardCreated();
    }

    /**
     * Communicates selected position
     *
     * @param positionSelected The user's selected position
     */
    public void positionSelected(Position positionSelected) {
        Mark markAtSelectedPosition = getPlaceForPosition(positionSelected);
        Player currentPlayer = getCurrentPlayer();

        if (markAtSelectedPosition == currentPlayer.mark()) {
            // Selecting position for movement
            this.selectedPosition = positionSelected;
            this.setMark(Mark.EMPTY, positionSelected);
        } else if (markAtSelectedPosition == Mark.EMPTY) {
            // If there is a Mark selected for movement
            if (this.selectedPosition != null) {
                moveMark(positionSelected);
            } else {
                // Normal Mark placement
                setMark(currentPlayer.mark(), positionSelected);
                this.turnCounter++;
            }
            checkGameOver(currentPlayer, positionSelected);
        } else if (selectedPosition != null) {
            // ignore invalid destination
            setMark(currentPlayer.mark(), selectedPosition);
            this.selectedPosition = null;
        }
        this.currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
    }

    /**
     * Attempts to move the previously selected Mark to a target adjacent Position
     *
     * @param target The target adjacent Position
     */
    private void moveMark(Position target) {
        Player currentPlayer = getCurrentPlayer();
        Mark mark = currentPlayer.mark();
        if (checkAdjacency(this.selectedPosition, target)) {
            setMark(mark, target);
            this.turnCounter++;
        } else {
            setMark(mark, this.selectedPosition);
        }
        this.selectedPosition = null;
    }

    /**
     * Checks if the given positions are adjacent
     *
     * @param p1 Position 1
     * @param p2 Position 2
     * @return True if they are adjacent, false otherwise
     */
    private boolean checkAdjacency(Position p1, Position p2) {
        int colDistance = Math.abs(p1.col() - p2.col());
        int rowDistance = Math.abs(p1.row() - p2.row());
        //return Math.hypot(rowDistance, colDistance) < 2;
        return colDistance < 2 && rowDistance < 2;
    }

    /**
     * Get the current player
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        //return this.turnCounter % 2 == 0 ? Player.X : Player.O;
        return currentPlayer;
    }

    /**
     * Sets a mark in the given Position and notifies the UI
     *
     * @param mark     The Place representing the player
     * @param position The target Position
     */
    public void setMark(Mark mark, Position position) {
        board[position.row()][position.col()] = mark;
        notifyPlaceChanged(position, mark);
        notifyBoardChanged(getBoardCopy());

    }

    private Mark getPlaceForPosition(Position position) {
        return board[position.row()][position.col()];
    }

    /**
     * Checks whether the game has ended and notifies the listener if so.
     *
     * @param lastPlayer The Place representing the player
     * @param position   The Position of the last play
     */
    private void checkGameOver(Player lastPlayer, Position position) {
        if (checkWinner(position)) {
            notifyGameWinner(lastPlayer);
        } else if (checkDraw()) {
            notifyGameDraw();
        }
    }

    /**
     * Check if the game has ended in a draw
     *
     * @return True if game ended in a draw
     */
    private boolean checkDraw() {
        for (Mark[] row : this.board) {
            for (Mark m : row) {
                if (m == Mark.EMPTY)
                    return false;
            }
        }
        return true;
    }

    /**
     * Check whether there's a winning sequence for a given Position
     *
     * @param position The Position representing the play
     * @return True if the play resulted in a win, false otherwise
     */
    private boolean checkWinner(Position position) {
        int col = position.col();
        int row = position.row();
        return checkRow(row) || checkCol(col) || checkDiag(row, col) || checkAntiDiag(row, col);
    }

    /**
     * Check the given row for a winning sequence
     *
     * @param row The row index
     * @return True if the row has a winning sequence, false otherwise
     */
    private boolean checkRow(int row) {
        Mark[] boardRow = board[row];

        for (int col = 1; col < size; col++) {
            Mark place = boardRow[col];
            if (place == Mark.EMPTY || boardRow[col] != boardRow[0]) return false;
        }
        return true;
    }

    /**
     * Check the given col for a winning sequence
     *
     * @param col The column index
     * @return True if the col has a winning sequence, false otherwise
     */
    private boolean checkCol(int col) {
        for (int row = 1; row < size; row++) {
            Mark place = board[row][col];
            if (place == Mark.EMPTY || board[row][col] != board[0][col]) return false;
        }
        return true;
    }

    /**
     * Check the given diagonal (top-left -> bottom-right) for a winning sequence
     *
     * @param row The row index
     * @param col The col index
     * @return True if the diagonal has a winning sequence, false otherwise
     */
    private boolean checkDiag(int row, int col) {
        if (row != col) return false;
        for (int i = 1; i < size; i++) {
            Mark place = board[i][i];
            if (place == Mark.EMPTY || place != board[0][0]) return false;
        }
        return true;
    }

    /**
     * Check the given diagonal (top-right -> bottom-left) for a winning sequence
     *
     * @param row The row index
     * @param col The col index
     * @return True if the diagonal has a winning sequence, false otherwise
     */
    private boolean checkAntiDiag(int row, int col) {
        if (row + col != size - 1) return false;
        for (int i = 1; i < size; i++) {
            Mark place = board[i][size - i - 1];
            if (place == Mark.EMPTY || place != board[0][size - 1]) return false;
        }
        return true;
    }

    /**
     * Generate a copy of the board data to prevent tampering with the board state.
     *
     * @return The copy
     */
    private Mark[][] getBoardCopy() {
        Mark[][] copy = new Mark[size][];
        for (int i = 0; i < size; i++) {
            copy[i] = Arrays.copyOf(board[i], size);
        }
        return copy;
    }

    /**
     * Set a listener for game events
     *
     * @param view The view that receives game model notifications
     */
    public void setGameView(View view) {
        // TODO poderia evoluir para addGameListener e podermos ver a GUI e TUI a funcionar em simultâneo.
        this.view = view;
    }

    /**
     * Notifies an existing listener of a Position and respective Place change
     *
     * @param position The position
     * @param place    The Place representing the player
     */
    private void notifyPlaceChanged(Position position, Mark place) {
        if (view != null) view.onBoardMarkChanged(place, position);
    }

    /**
     * Notifies an existing listener of the board creation
     */
    private void notifyBoardCreated() {
        if (view != null) view.onBoardCreated(getBoardCopy());
    }

    /**
     * Notifies an existing listener of board changes
     */
    private void notifyBoardChanged(Mark[][] board) {
        if (view != null) view.onBoardChanged(board);
    }

    /**
     * Notifies an existing listener of a game ending in a draw
     */
    private void notifyGameDraw() {
        if (view != null) view.onGameDraw();
    }

    /**
     * Notifies an existing listener of a game ending in a win for a given player
     *
     * @param player The Place representing the winning player
     */
    private void notifyGameWinner(Player player) {
        if (view != null) view.onGameWon(player);
    }

    public void initializeBoard(int size) {
        board = new Mark[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = Mark.EMPTY;
            }
        }
    }

    public void setBoard(Mark[][] board) {
        this.board = board;
        notifyBoardChanged(board);
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
