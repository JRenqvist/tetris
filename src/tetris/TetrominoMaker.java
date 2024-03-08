package tetris;


public class TetrominoMaker {

    public int getNumberOfTypes() {
        return SquareType.values().length;
    }

    public Poly getPoly(int n) {

	if (n < 0 || n >= getNumberOfTypes()) {
            throw new IllegalArgumentException("Invalid index: " + n);
        }

        SquareType[][] shape = createTetrominoShape(n);
        
        return new Poly(shape);
    }

    // Helper to get corresponding piece
    private SquareType[][] createTetrominoShape(int index) {
        switch (index) {
            case 0:
                return createIPiece();
            case 1:
                return createJPiece();
            case 2:
                return createLPiece();
            case 3:
                return createOPiece();
            case 4:
                return createSPiece();
            case 5:
                return createTPiece();
            case 6:
                return createZPiece();
            default:
                throw new IllegalArgumentException("Invalid index: " + index);
        }
    }

    // Hjälpmetoder för att skapa specifika blocktyper
    private SquareType[][] createIPiece() {
        return new SquareType[][] {
            {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY},
            {SquareType.I, SquareType.I, SquareType.I, SquareType.I},
            {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY},
            {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
        };
    }

    private SquareType[][] createJPiece() {
        return new SquareType[][] {
            {SquareType.J, SquareType.EMPTY, SquareType.EMPTY},
            {SquareType.J, SquareType.J, SquareType.J},
            {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
        };
    }

    private SquareType[][] createLPiece() {
        return new SquareType[][] {
            {SquareType.EMPTY, SquareType.EMPTY, SquareType.L},
            {SquareType.L, SquareType.L, SquareType.L},
            {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
        };
    }

    private SquareType[][] createOPiece() {
        return new SquareType[][] {
            {SquareType.O, SquareType.O},
            {SquareType.O, SquareType.O}
        };
    }

    private SquareType[][] createSPiece() {
        return new SquareType[][] {
            {SquareType.EMPTY, SquareType.S, SquareType.S},
            {SquareType.S, SquareType.S, SquareType.EMPTY},
            {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
        };
    }

    private SquareType[][] createTPiece() {
        return new SquareType[][] {
            {SquareType.EMPTY, SquareType.T, SquareType.EMPTY},
            {SquareType.T, SquareType.T, SquareType.T},
            {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
        };
    }

    private SquareType[][] createZPiece() {
        return new SquareType[][] {
            {SquareType.Z, SquareType.Z, SquareType.EMPTY},
            {SquareType.EMPTY, SquareType.Z, SquareType.Z},
            {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
        };
    }
}
