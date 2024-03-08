package tetris;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.awt.Point;

public class Board {
    private SquareType[][] squares;
    private int width;
    private int height;

    private Poly falling;
    private Point fallingPos;

    private final static Random RND = new Random();
    private TetrominoMaker tetrominoMaker = new TetrominoMaker();
    private int totalTetrominos = tetrominoMaker.getNumberOfTypes() - 2;

    private List<BoardListener> listeners = new ArrayList<>();

    private final static int MARGIN = 3;
    private final static int DOUBLE_MARGIN = MARGIN * 2;

    private boolean isGameOver;
    private int numberOfFullLines;
    private List<Integer> linesToRemove;

    private boolean isPaused = false;

    private int points;
    private final static Map<Integer, Integer> POINT_MAP = Map.of(1, 100, 2, 300, 3, 500, 4, 800);

    private HighscoreList highscoreList = new HighscoreList();


    // Constructor
    public Board(int width, int height) {

        // --- Initialize field values etc
        this.width = width;
        this.height = height;
        this.squares = new SquareType[height + DOUBLE_MARGIN][width + DOUBLE_MARGIN];

        // Gets a random poly. -2 Since we have EMPTY and OUTSIDE as a SquareType

        this.falling = tetrominoMaker.getPoly(RND.nextInt(totalTetrominos));

        final int x = this.width / 2 - 2;
        final int y = 0;
        this.fallingPos = new Point(x, y);

        this.isGameOver = false;
        this.linesToRemove = new ArrayList<>();

	// --- Generate board
        // Set all tiles in squares to OUTSIDE
	for (final SquareType[] square : squares) {
	    Arrays.fill(square, SquareType.OUTSIDE);
	}

        // Add EMPTY in the middle
        for (int i = MARGIN; i < height + MARGIN; i++) {
            for (int j = MARGIN; j < width + MARGIN; j++) {
                squares[i][j] = SquareType.EMPTY;
            }
        }

        this.notifyListeners();
    }
    // Get width (columns, y) and height (rows, x) methods
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Poly getFalling() {
        return falling;
    }
    public Point getFallingPos() {
        return fallingPos;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public boolean getIsPaused() {
        return isPaused;
    }

    public int getPoints() {
        return points;
    }

    public HighscoreList getHighscoreList() {
        return highscoreList;
    }

    public void setIsPaused(final boolean paused) {
        isPaused = paused;
    }

    public SquareType getSquareTypeOfTile(int x, int y) {
        return squares[y + MARGIN][x + MARGIN];
    }

    public void addBoardListener(BoardListener bl) {
        listeners.add(bl);
    }

    private void notifyListeners() {
        if (listeners == null) return;
        for (BoardListener listener : listeners) {
            listener.boardChanged();
        }
    }

    public void tick() {
        if (falling == null) setFalling();
        else moveFalling();

        if (hasCollision()) {
            move(Direction.PLACE);
        }

        // Checks if any rows are full and needs to be removed. Adds the y-value to nrOfFullLines
        checkForEmptyRows();
        if (numberOfFullLines != 0) {
            removeRows();
        }
    }

    private void setFalling() {
        // Gets called if there isnt a falling poly on the board on a tick
        this.falling = tetrominoMaker.getPoly(RND.nextInt(totalTetrominos));

        final int x = this.width / 2 - 2;
        final int y = 0;
        this.fallingPos = new Point(x, y);

        if (hasCollision()) {
            isGameOver = true;
        }
        this.notifyListeners();
    }

    private void moveFalling() {
        // Gets called if there is a falling poly on the board to move on a tick
        this.fallingPos.y++;
        this.notifyListeners();
    }

    public void move(Direction input) {
        if (input == Direction.LEFT) {
            if (falling != null && fallingPos != null) {
                this.fallingPos.x--;
            }
        } else if (input == Direction.RIGHT) {
            if (falling != null && fallingPos != null) {
                this.fallingPos.x++;
            }
        } else if (input == Direction.ROTATE_RIGHT) {
            rotate(Direction.ROTATE_RIGHT);
        } else if (input == Direction.ROTATE_LEFT) {
            rotate(Direction.ROTATE_LEFT);

        // Gets called if falling has reached the bottom, not bound to a key
        } else if (input == Direction.PLACE) {
            this.fallingPos.y--;
            writeFallingToBoard();

        }
        this.notifyListeners();
    }

    // Gets called when we want to add a falling to the board at coordinates (x, y) and sets falling to null
    private void writeFallingToBoard() {
        int x1 = fallingPos.x;
        int y1 = fallingPos.y;
        int x2 = x1 + falling.getDimension();
        int y2 = y1 + falling.getDimension();

        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                if (falling.getPolyTypeAtPos(x, y, x1, y1) != SquareType.EMPTY) {
                    squares[y + MARGIN][x + MARGIN] = falling.getShape();
                }
            }
        }

        falling = null;
        fallingPos = null;

    }

    public boolean hasCollision() {
        if (falling != null && fallingPos != null) {
            int x1 = fallingPos.x;
            int y1 = fallingPos.y;
            int x2 = x1 + falling.getDimension();
            int y2 = y1 + falling.getDimension();

            // For loops to check all coordinates of falling poly
            for (int x = x1; x < x2; x++) {
                for (int y = y1; y < y2; y++) {
                    // Check if falling is within bounds (might collide with existing blocks)
                    if (x >= 0 && x < width && y >= 0 && y < height) {
                        SquareType fallingSquare = falling.getPolyTypeAtPos(x, y, x1, y1);
                        SquareType boardSquare = getSquareTypeOfTile(x, y);

                        // Collision if both squares are not EMPTY
                        if (fallingSquare != SquareType.EMPTY && boardSquare != SquareType.EMPTY) {
                            return true;
                        }
                    // Else we know falling is outside bounds, but might be of type EMPTY
                    } else {
                        // Check if falling poly is not empty
                        if (falling.getPolyTypeAtPos(x, y, x1, y1) != SquareType.EMPTY) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void rotate(Direction dir) {
        if (falling != null && fallingPos != null) {
            // Create a copy of falling
            Poly oldFalling = falling.deepCopy();

            if (dir == Direction.ROTATE_RIGHT) {
                // Rotate the piece
                falling = falling.rotateRight();

                // If rotation results in collision, revert to old falling
                if (hasCollision()) {
                    falling = oldFalling;
                }

            } else if (dir == Direction.ROTATE_LEFT) {
                // Rotate the piece
                falling = falling.rotateLeft();

                // If rotation results in collision, revert to old falling
                if (hasCollision()) {
                    falling = oldFalling;
                }
            }
        }
    }

    private void checkForEmptyRows() {
        // Adds all lines to be removed into field numberOfFullLines
        boolean isFullRow = false;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Checks if a line contains EMPTY type
                if (getSquareTypeOfTile(x, y) == SquareType.EMPTY) {
                    isFullRow = false;
                    break;
                }
            }
            // If a line contains an EMPTY type, add the y-value to linesToRemove
            // linesToRemove handles MARGIN
            if (isFullRow) {
                numberOfFullLines++;
                linesToRemove.add(y);
            }
            isFullRow = true;
        }
    }

    private void removeRows() {

        // First, remove the types in the rows specified by linesToRemove, set them to null
        for (int y : linesToRemove) {
            for (int x = 0; x < squares[y].length; x++) {
                if (squares[y + MARGIN][x] != SquareType.OUTSIDE) {
                    squares[y + MARGIN][x] = null;
                }
            }
        }

        // At this moment, we have the number of lines removed in linesToRemove. Add score accordingly
        addScore(linesToRemove.size());

        // While we have x amount of null pieces on the board, move rows above down one
        while (isNullInBoard()) {
            moveDownRows();
        }

        notifyListeners();
        linesToRemove.clear();
        numberOfFullLines = 0;
    }

    private void moveDownRows() {
        // Goes backwards in squares and if any null is in subarray, move the line above it down one step

        // Go through all lines
        for (int y = height + MARGIN; y >= MARGIN; y--) {
            boolean isRowEmpty = true;

            // Check if the row has null (row above needs to be moved down)
            for (int x = MARGIN; x < width + MARGIN; x++) {
                if (squares[y][x] != null) {
                    isRowEmpty = false;
                    break;
                }
            }

            if (isRowEmpty) {
                // Move the rows above it down one step
                for (int i = y; i > MARGIN; i--) {
                    for (int x = MARGIN; x < width + MARGIN; x++) {
                        squares[i][x] = squares[i - 1][x];
                        }
                    }
                }
            }
        }

    private boolean isNullInBoard() {
        for (int y = MARGIN; y < height + MARGIN; y++) {
            for (int x = MARGIN; x < width + MARGIN; x++) {
                if (squares[y][x] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    private void addScore(int numOfRowsRemoved) {
        points += POINT_MAP.get(numOfRowsRemoved);
    }
    
    public SquareType getVisibleSquareAt(int x, int y) {

        if (falling != null && fallingPos != null) {
            int x1 = fallingPos.x;
            int y1 = fallingPos.y;
            int x2 = x1 + falling.getDimension();
            int y2 = y1 + falling.getDimension();

            if (x >= x1 && x < x2 && y >= y1 && y < y2) {
                if (falling.getPolyTypeAtPos(x, y, x1, y1) == SquareType.EMPTY) {
                    // Replaces with board type. Falling is covering coordinates

                    return getSquareTypeOfTile(x, y);
                } else {
                    // Replaces with falling type
                    return falling.getShape();
                }
            }
        }
        // Replaces with board type. Falling is not covering coordinates
        return getSquareTypeOfTile(x, y);
    }
}
