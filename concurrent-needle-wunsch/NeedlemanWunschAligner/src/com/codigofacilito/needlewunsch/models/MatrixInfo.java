package com.codigofacilito.needlewunsch.models;

/**
 * Holds input and score matrix data (input scoring parameters, sequences to align and  score matrix).
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
            // Initializes all-zeroes integer matrix based on sequences length, adding one additional row and column to
            // hold initial gap accumulation values.
            this.scoreMatrix = new int[inputData.seqA().length() + 1][inputData.seqB().length() + 1];
            return new MatrixInfo(this);
        }

    }
}
