package utils;

import java.util.Arrays;

public class MatrixPrinter {

    public static void printMatrix(int[][] scoreMatrix) {
        Arrays.stream(scoreMatrix)
                .map(Arrays::toString)
                .forEach(System.out::println);
    }

}
