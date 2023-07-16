package pt.ipbeja.po2.tictactoe.model;

import org.junit.jupiter.api.Test;
import pt.ipbeja.po2.tictactoe.gui.TicTacToeButton;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeGameTest {

    @Test
    void testReadFile() {

        // Define the path to the test file
        String filePath = "src/play.txt";

        // Read the file content
        List<String> fileContent = new ArrayList<>();
        try {
            fileContent = Files.readAllLines(Paths.get(filePath));
        } catch (Exception e) {
            // Handle any potential exceptions when reading the file
            e.printStackTrace();
        }

        // Create an instance of TicTacToeGame
        TicTacToeGame game = new TicTacToeGame();

        //Create a dummy buttons array
        TicTacToeButton[][] buttons = null;

        // Call the markPositionBoard method to read the file content
        game.markPositionBoard(fileContent);

        // Define the expected board after reading the file
        Mark[][] expectedBoard = {
                {Mark.X_MARK, Mark.O_MARK, Mark.EMPTY},
                {Mark.EMPTY, Mark.EMPTY, Mark.EMPTY},
                {Mark.EMPTY, Mark.EMPTY, Mark.O_MARK}
        };

        // Compare the expected board with the actual board in the TicTacToeGame instance
        assertArrayEquals(expectedBoard, game.getBoard());
    }
}