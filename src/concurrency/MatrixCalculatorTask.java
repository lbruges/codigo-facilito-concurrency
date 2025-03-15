package concurrency;

import models.MatrixInfo;
import models.InputData;

import java.util.concurrent.RecursiveTask;

import static java.lang.Math.max;

public class MatrixCalculatorTask extends RecursiveTask<Void> {

    private final MatrixInfo matrixInfo;
    private final int startRow;
    private final int endRow;

    public MatrixCalculatorTask(MatrixInfo matrixInfo, int startRow, int endRow) {
        this.matrixInfo = matrixInfo;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    public MatrixCalculatorTask(MatrixInfo matrixInfo) {
        this.matrixInfo = matrixInfo;
        this.startRow = 1;
        this.endRow = matrixInfo.getMatrixInput().seqA().length();
    }

    @Override
    protected Void compute() {
        var matrixInput = matrixInfo.getMatrixInput();
        var scoreMatrix = matrixInfo.getScoreMatrix();

        for (int i = startRow; i <= endRow; i++) {
            for (int j = 1; j <= matrixInput.seqB().length(); j++) {
                int curr = scoreMatrix[i-1][j-1] + matchOrMiss(matrixInput.seqA().charAt(i-1),
                        matrixInput.seqB().charAt(j-1), matrixInput);
                int up = scoreMatrix[i-1][j] + matrixInput.gapScore();
                int left = scoreMatrix[i][j-1] + matrixInput.gapScore();

                scoreMatrix[i][j] = max(curr, max(up, left));
            }
        }

        if ((endRow - startRow) > 1) {
            int midRow = (startRow + endRow) / 2;
            MatrixCalculatorTask upperMid = new MatrixCalculatorTask(matrixInfo, startRow, midRow);
            MatrixCalculatorTask lowerMid = new MatrixCalculatorTask(matrixInfo, midRow, endRow);

            invokeAll(upperMid, lowerMid);
        }

        return null;
    }

    private int matchOrMiss(char baseA, char baseB, InputData inputData) {
        if (baseA == baseB) {
            return inputData.matchScore();
        }

        return inputData.missScore();
    }
}
