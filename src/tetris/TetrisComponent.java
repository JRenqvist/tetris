package tetris;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

public class TetrisComponent extends JComponent implements BoardListener {
    private Board board;
    private final static EnumMap<SquareType, Color> SQUARE_COLORS = createColorMap();
    private final static int SQUARE_SIZE = 30;

    public TetrisComponent(final Board board) {
	this.board = board;
    }

    @Override
    public Dimension getPreferredSize() {

	int width = board.getWidth() * SQUARE_SIZE;
	int height = board.getHeight() * SQUARE_SIZE;

	return new Dimension(width, height);
    }

    public int getSquareSize() {
	return SQUARE_SIZE;
    }

    private static EnumMap<SquareType, Color> createColorMap() {

	EnumMap<SquareType, Color> map = new EnumMap<>(SquareType.class);

	map.put(SquareType.EMPTY, new Color(213, 213, 213));
	map.put(SquareType.I, new Color(51, 153, 255));
	map.put(SquareType.J, new Color(0, 60, 127));
	map.put(SquareType.L, new Color(208, 118, 0));
	map.put(SquareType.O, new Color(255, 195, 0));
	map.put(SquareType.S, new Color(35, 225, 0));
	map.put(SquareType.T, new Color(161, 0, 176));
	map.put(SquareType.Z, new Color(183, 0, 0));

	return map;
    }

    @Override public void boardChanged() {
	if (!board.getIsGameOver()) {
	    repaint();
	}
    }

    @Override protected void paintComponent(Graphics g) {

	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	int width = (int)getPreferredSize().getWidth();
	int height = (int)getPreferredSize().getHeight();


	// Now we want to draw the squares
	// Loop through all the squares in board and call paintSquare with appropriate visible SquareType
	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		switch (board.getVisibleSquareAt(x, y)) {
		    case EMPTY:
			paintSquare(g2d, SquareType.EMPTY, x, y);
			break;
		    case I:
			paintSquare(g2d, SquareType.I, x, y);
			break;
		    case O:
			paintSquare(g2d, SquareType.O, x, y);
			break;
		    case T:
			paintSquare(g2d, SquareType.T, x, y);
			break;
		    case S:
			paintSquare(g2d, SquareType.S, x, y);
			break;
		    case Z:
			paintSquare(g2d, SquareType.Z, x, y);
			break;
		    case J:
			paintSquare(g2d, SquareType.J, x, y);
			break;
		    case L:
			paintSquare(g2d, SquareType.L, x, y);
			break;
		}
	    }
	}

    }

    private void paintSquare(Graphics2D g2d, SquareType squareType, int x, int y) {
	Color color = SQUARE_COLORS.get(squareType);
	g2d.setColor(color);
	g2d.fillRect(x * 30, y * 30, SQUARE_SIZE, SQUARE_SIZE);
    }
}
