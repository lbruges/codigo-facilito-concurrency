package models;

/**
 * Holds input and score matrix data.
 */
public class MatrixInfo {

    private final int[][] scoreMatrix;
    private final InputData inputData;

    private MatrixInfo(MatrixBuilder builder) {
        this.scoreMatrix = builder.scoreMatrix;
        this.inputData = builder.inputData;
    }

    public static MatrixBuilder builder() {
        return new MatrixBuilder();
    }

    public int[][] getScoreMatrix() {
        return scoreMatrix;
    }

    public InputData getMatrixInput() {
        return inputData;
    }

    public static class MatrixBuilder {
        private int[][] scoreMatrix;
        private InputData inputData;

        public MatrixBuilder withConstantsInfo(InputData inputData) {
            this.inputData = inputData;
            return this;
        }

        public MatrixInfo build() {
            // Initializes all-zeroes integer matrix based on sequences length
            this.scoreMatrix = new int[inputData.seqA().length() + 1][inputData.seqB().length() + 1];
            return new MatrixInfo(this);
        }

    }
}
