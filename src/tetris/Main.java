package tetris;




public class Main {
    public static void main(String[] args) {

        // Creates a board object
        Board board = new Board(10, 20);

        // Creates the gui
        TetrisViewer gui = new TetrisViewer(board);
        gui.show();
    }
}