package utils;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MatrixInitializer {

    private final int[][] scoreMatrix;

    private MatrixInitializer(MatrixBuilder builder) {
        this.scoreMatrix = builder.scoreMatrix;
    }

    public static MatrixBuilder builder() {
        return new MatrixBuilder();
    }

    public int[][] getScoreMatrix() {
        return scoreMatrix;
    }

    public void printMatrix() {
        Arrays.stream(scoreMatrix)
                .map(Arrays::toString)
                .forEach(System.out::println);
    }

    public static class MatrixBuilder {

        private int[][] scoreMatrix;
        private String chainA;
        private String chainB;
        private int gapScore;
        private int matchScore;
        private int missScore;

        public MatrixBuilder chainA(String chainA) {
            this.chainA = chainA;
            return this;
        }

        public MatrixBuilder chainB(String chainB) {
            this.chainB = chainB;
            return this;
        }

        public MatrixBuilder gapScore(int gapScore) {
            this.gapScore = gapScore;
            return this;
        }
        public MatrixBuilder matchScore(int matchScore) {
            this.matchScore = matchScore;
            return this;
        }
        public MatrixBuilder missScore(int missScore) {
            this.missScore = missScore;
            return this;
        }

        public MatrixInitializer build() {
            this.scoreMatrix = initMatrix();
            return new MatrixInitializer(this);
        }

        private int[][] initMatrix() {
            int rows = chainA.length() + 1;
            int cols = chainB.length() + 1;

            int[][] scoreMatrix = new int[rows][cols];
            addInitialGapValues(scoreMatrix, rows, cols);
            calculateScores(scoreMatrix, chainA, chainB);

            return scoreMatrix;
        }

        private void addInitialGapValues(int[][] scoreMatrix, int rows, int cols) {
            int i = 1, j = 1;
            while (i < rows || j < cols) {
                if (i < scoreMatrix.length) {
                    scoreMatrix[i][0] = gapScore * i;
                    i += 1;
                }

                if (j < scoreMatrix[0].length) {
                    scoreMatrix[0][j] = gapScore * j;
                    j +=1;
                }
            }
        }

        private void calculateScores(int[][] scoreMatrix, String chainA, String chainB) {
            for (int i = 1; i < scoreMatrix.length; i++) {
                for (int j = 1; j < scoreMatrix[0].length; j++) {
                    boolean isMatch = chainA.charAt(i - 1) == chainB.charAt(j - 1);
                    int cellScore = isMatch ? this.matchScore : this.missScore;
                    int curr = IntStream.of(cellScore + scoreMatrix[i-1][j-1],
                                    this.gapScore + scoreMatrix[i-1][j],
                                    this.gapScore + scoreMatrix[i][j-1])
                            .max()
                            .orElse(this.gapScore + scoreMatrix[i-1][j]);
                    scoreMatrix[i][j] = curr;
                }
            }
        }

    }
}
