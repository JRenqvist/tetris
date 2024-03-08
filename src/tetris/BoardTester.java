package tetris;




public class BoardTester {
    public static void main(String[] args) {

        // Creates a board object
        Board board = new Board(10, 20);

        // Converts the board to a string
        StringBuilder boardString = new BoardToTextConverter().convertToText(board);

        // Generates a random board
        // board.generateRandomBoard();


        // Converts the random board to a string
        final StringBuilder randomBoardString = new BoardToTextConverter().convertToText(board);


        // Creates the gui
        TetrisViewer gui = new TetrisViewer(board);
        gui.show();
    }
}