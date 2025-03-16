package com.codigofacilito.needlewunsch.controller;

import com.codigofacilito.needlewunsch.models.MatrixInfo;

/**
 * Intermediate abstract operator that performs an operation common to the sequential and parallel version of the
 * Needleman-Wunsch Algorithm.
 *
 */
public abstract class MatrixPopulatorDecorator extends AllowsIntermediateDecorator {

    public MatrixPopulatorDecorator() {
        super();
    }

    public MatrixPopulatorDecorator(MatrixDecorator next) {
        super(next);
    }

    /**
     * Initializes the external values of the matrix (first row and first column), which corresponds to the gap score
     * times the current index.
     *
     * @param matrixInfo matrix information.
     */
    public void decorateMatrix(MatrixInfo matrixInfo) {
        var matrixInput = matrixInfo.getMatrixInput();
        int[][] scoreMatrix = matrixInfo.getScoreMatrix();

        int i = 1, j = 1;
        while (i < matrixInput.seqA().length() || j < matrixInput.seqB().length()) {
            if (i < scoreMatrix.length) {
                scoreMatrix[i][0] = matrixInput.gapScore() * i;
                i += 1;
            }

            if (j < scoreMatrix[0].length) {
                scoreMatrix[0][j] = matrixInput.gapScore() * j;
                j +=1;
            }
        }
    }

}
