package needlewunsch.controller;

import needlewunsch.models.MatrixInfo;

public abstract class MatrixPopulatorDecorator extends AllowsIntermediateDecorator {

    public MatrixPopulatorDecorator() {
        super();
    }

    public MatrixPopulatorDecorator(MatrixDecorator next) {
        super(next);
    }

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
