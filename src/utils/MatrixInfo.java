package utils;

import java.util.Arrays;

import static java.lang.Math.max;

public class MatrixInfo {

    private final int[][] scoreMatrix;
    private final String chainA;
    private final String chainB;
    private final int gapScore;
    private final int matchScore;
    private final int missScore;

    private MatrixInfo(MatrixBuilder builder) {
        this.scoreMatrix = builder.scoreMatrix;
        this.chainA = builder.chainA;
        this.chainB = builder.chainB;
        this.gapScore = builder.gapScore;
        this.matchScore = builder.matchScore;
        this.missScore = builder.missScore;
    }

    public static MatrixBuilder builder() {
        return new MatrixBuilder();
    }

    public int[][] getScoreMatrix() {
        return scoreMatrix;
    }

    public String getChainA() {
        return chainA;
    }

    public String getChainB() {
        return chainB;
    }

    public int getGapScore() {
        return gapScore;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public int getMissScore() {
        return missScore;
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

        public MatrixBuilder withAllValues() {
            this.scoreMatrix = initMatrix();
            return this;
        }

        public MatrixBuilder withInitialGapValues() {
            this.scoreMatrix = addInitialGapValues();
            return this;
        }

        public MatrixInfo build() {
            return new MatrixInfo(this);
        }

        private int[][] initMatrix() {

            int[][] scoreMatrix = addInitialGapValues();
            calculateScores(scoreMatrix);

            return scoreMatrix;
        }

        private int[][] addInitialGapValues() {
            int rows = chainA.length() + 1;
            int cols = chainB.length() + 1;

            int[][] scoreMatrix = new int[rows][cols];
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

            return scoreMatrix;
        }

        private void calculateScores(int[][] scoreMatrix) {
            for (int i = 1; i < scoreMatrix.length; i++) {
                for (int j = 1; j < scoreMatrix[0].length; j++) {
                    boolean isMatch = chainA.charAt(i - 1) == chainB.charAt(j - 1);
                    int cellScore = isMatch ? this.matchScore : this.missScore;

                    scoreMatrix[i][j] = max(cellScore, max(this.gapScore + scoreMatrix[i-1][j],
                            this.gapScore + scoreMatrix[i][j-1]));
                }
            }
        }

    }
}
