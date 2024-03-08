package tetris;

import java.util.Random;

public enum SquareType {
    OUTSIDE, EMPTY, I, O, T, S, Z, J, L;

    public static void main(String[] args) {
        Random rnd = new Random();
        
        for (int i = 1; i <= 25; i++) {
            System.out.println(rnd.nextInt(100));
        }
        System.out.println("Random values of SquareType:\n");
        for (int i = 0; i < 25; i++) {
            System.out.println(SquareType.values()[rnd.nextInt(8)]);
        }
    }
}
