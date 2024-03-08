package tetris;


import java.util.Arrays;

public class Poly {
    private SquareType[][] arr;
    private SquareType shape = null;

    public Poly(SquareType[][] inputArr) {
        this.arr = inputArr;
        
        // Find the type by accessing SquareType in the 2d-array
        boolean foundType = false;
        for (SquareType[] subArr : this.arr) {
            for (SquareType blockType : subArr) {
                if (blockType != SquareType.EMPTY) {

                    this.shape = blockType;
                    foundType = true;
                    break;
                }
            }
            if (foundType) {
                break;
            }
        }
    }

    // Getter för .shape av poly-objekt
    public SquareType getShape() {
        return shape;
    }

    // Getter för length av arr för poly-objekt eftersom dimensionerna är samma (2x2, 3x3 ...)
    public int getDimension() {
        return arr.length;
    }

    public SquareType getPolyTypeAtPos(int x, int y, int polyX, int polyY) {
        int relativeX = x - polyX;
        int relativeY = y - polyY;
        
        return arr[relativeY][relativeX];
    }

    // Argument "right" is true if we want to rotate to the right, false if to the left
    public Poly rotateRight() {
        int size = getDimension();

        // Create a copy of the current piece
        Poly newPoly = new Poly(this.arr);

        // Create a temporary array to store the rotated values
        SquareType[][] rotatedArr = new SquareType[size][size];

        // Rotate clockwise
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                rotatedArr[c][size-1-r] = this.arr[r][c];
            }
        }

        // Copy the rotated values back to the newPoly array
        for (int r = 0; r < size; r++) {
            System.arraycopy(rotatedArr[r], 0, newPoly.arr[r], 0, size);
        }

        return newPoly;
    }


    public Poly rotateLeft() {
        int size = getDimension();

        // Create a copy of the current piece
        Poly newPoly = new Poly(this.arr);

        // Create a temporary array to store the rotated values
        SquareType[][] rotatedArr = new SquareType[size][size];

        // Rotate counterclockwise
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                rotatedArr[size-1-c][r] = this.arr[r][c];
            }
        }

        // Copy the rotated values back to the newPoly array
        for (int r = 0; r < size; r++) {
            System.arraycopy(rotatedArr[r], 0, newPoly.arr[r], 0, size);
        }

        return newPoly;
    }

    public Poly deepCopy() {
        int size = getDimension();
        SquareType[][] newArr = new SquareType[size][size];

        for (int i = 0; i < size; i++) {
            newArr[i] = Arrays.copyOf(this.arr[i], size);
        }

        return new Poly(newArr);
    }


}
