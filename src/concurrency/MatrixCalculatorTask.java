package concurrency;

import models.ConstantsInfo;

import java.util.concurrent.RecursiveTask;

import static java.lang.Math.max;

public class MatrixCalculatorTask extends RecursiveTask<Void> {

    private final ConstantsInfo constantsInfo;
    private final int[][] scoreMatrix;
    private final int startRow;
    private final int endRow;

    public MatrixCalculatorTask(ConstantsInfo constantsInfo, int[][] scoreMatrix,
                                int startRow, int endRow) {
        this.constantsInfo = constantsInfo;
        this.scoreMatrix = scoreMatrix;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    public MatrixCalculatorTask(ConstantsInfo constantsInfo, int[][] scoreMatrix) {
        this.constantsInfo = constantsInfo;
        this.scoreMatrix = scoreMatrix;
        this.startRow = 1;
        this.endRow = constantsInfo.seqA().length();
    }

    @Override
    protected Void compute() {
        for (int i = startRow; i <= endRow; i++) {
            for (int j = 1; j <= constantsInfo.seqB().length(); j++) {
                int curr = scoreMatrix[i-1][j-1] + matchOrMiss(constantsInfo.seqA().charAt(i-1),
                        constantsInfo.seqB().charAt(j-1));
                int up = scoreMatrix[i-1][j] + constantsInfo.gapScore();
                int left = scoreMatrix[i][j-1] + constantsInfo.gapScore();

                scoreMatrix[i][j] = max(curr, max(up, left));
            }
        }

        if ((endRow - startRow) > 1) {
            int midRow = (startRow + endRow) / 2;
            MatrixCalculatorTask upperMid = new MatrixCalculatorTask(constantsInfo, scoreMatrix, startRow, midRow);
            MatrixCalculatorTask lowerMid = new MatrixCalculatorTask(constantsInfo, scoreMatrix, midRow, endRow);

            invokeAll(upperMid, lowerMid);
        }

        return null;
    }

    private int matchOrMiss(char baseA, char baseB) {
        if (baseA == baseB) {
            return constantsInfo.matchScore();
        }

        return constantsInfo.missScore();
    }
}
