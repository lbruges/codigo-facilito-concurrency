package models;

import static java.lang.Math.max;

public class MatrixInfo {

    private final int[][] scoreMatrix;
    private final ConstantsInfo constantsInfo;

    private MatrixInfo(MatrixBuilder builder) {
        this.scoreMatrix = builder.scoreMatrix;
        this.constantsInfo = builder.constantsInfo;
    }

    public static MatrixBuilder builder() {
        return new MatrixBuilder();
    }

    public int[][] getScoreMatrix() {
        return scoreMatrix;
    }

    public static class MatrixBuilder {

        private int[][] scoreMatrix;
        private ConstantsInfo constantsInfo;

        public MatrixBuilder withConstantsInfo(ConstantsInfo constantsInfo) {
            this.constantsInfo = constantsInfo;
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
            int rows = constantsInfo.chainA().length() + 1;
            int cols = constantsInfo.chainB().length() + 1;

            int[][] scoreMatrix = new int[rows][cols];
            int i = 1, j = 1;
            while (i < rows || j < cols) {
                if (i < scoreMatrix.length) {
                    scoreMatrix[i][0] = constantsInfo.gapScore() * i;
                    i += 1;
                }

                if (j < scoreMatrix[0].length) {
                    scoreMatrix[0][j] = constantsInfo.gapScore() * j;
                    j +=1;
                }
            }

            return scoreMatrix;
        }

        private void calculateScores(int[][] scoreMatrix) {
            int gapScore = constantsInfo.gapScore();
            for (int i = 1; i < scoreMatrix.length; i++) {
                for (int j = 1; j < scoreMatrix[0].length; j++) {
                    boolean isMatch = constantsInfo.chainA().charAt(i-1) == constantsInfo.chainB().charAt(j-1);
                    int cellScore = (isMatch ? constantsInfo.matchScore() : constantsInfo.missScore())
                            + scoreMatrix[i-1][j-1];

                    scoreMatrix[i][j] = max(cellScore, max(gapScore + scoreMatrix[i-1][j],
                            gapScore + scoreMatrix[i][j-1]));
                }
            }
        }

    }
}
