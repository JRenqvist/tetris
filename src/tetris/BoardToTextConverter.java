package tetris;


public class BoardToTextConverter {

    // Function to convert board to text
    public StringBuilder convertToText(Board board) {
        StringBuilder resultString = new StringBuilder();
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                switch (board.getVisibleSquareAt(x, y)) {
                    case EMPTY:
                        resultString.append("E");
                        break;
                    case I:
                        resultString.append("I");
                        break;
                    case O:
                        resultString.append("O");
                        break;
                    case T:
                        resultString.append("T");
                        break;
                    case S:
                        resultString.append("S");
                        break;
                    case Z:
                        resultString.append("Z");
                        break;
                    case J:
                        resultString.append("J");
                        break;
                    case L:
                        resultString.append("L");
                        break;
                    default:
                        resultString.append("Error");
                        break;
                }
            }
            resultString.append("\n");
        }
        return resultString;
    }
}
