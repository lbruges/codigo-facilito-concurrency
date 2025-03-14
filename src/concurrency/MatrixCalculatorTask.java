package concurrency;

import models.ConstantsInfo;

import java.util.concurrent.RecursiveTask;

import static java.lang.Math.max;

public class MatrixCalculatorTask extends RecursiveTask<Void> {

    private final String chainA;
    private final String chainB;
    private final int[][] scoreMatrix;
    private final int startRow;
    private final int endRow;
    private final int gapScore;
    private final int matchScore;
    private final int missScore;

    public MatrixCalculatorTask(String chainA, String chainB, int[][] scoreMatrix,
                                int startRow, int endRow, int gapScore, int matchScore,
                                int missScore) {
        this.chainA = chainA;
        this.chainB = chainB;
        this.scoreMatrix = scoreMatrix;
        this.startRow = startRow;
        this.endRow = endRow;
        this.gapScore = gapScore;
        this.matchScore = matchScore;
        this.missScore = missScore;
    }

    public MatrixCalculatorTask(ConstantsInfo constantsInfo, int[][] scoreMatrix) {
        this.chainA = constantsInfo.chainA();
        this.chainB = constantsInfo.chainB();
        this.scoreMatrix = scoreMatrix;
        this.startRow = 1;
        this.endRow = chainA.length();
        this.gapScore = constantsInfo.gapScore();
        this.matchScore = constantsInfo.matchScore();
        this.missScore = constantsInfo.missScore();
    }

    @Override
    protected Void compute() {
        for (int i = startRow; i < endRow; i++) {
            for (int j = 1; j <= chainB.length(); j++) {
                int curr = scoreMatrix[i-1][j-1] + matchOrMiss(chainA.charAt(i-1), chainB.charAt(j-1));
                int up = scoreMatrix[i-1][j] + gapScore;
                int left = scoreMatrix[i][j-1] + gapScore;

                scoreMatrix[i][j] = max(curr, max(up, left));
            }
        }

        if ((endRow - startRow) > 1) {
            int midRow = (startRow + endRow) / 2;
            MatrixCalculatorTask upperMid = new MatrixCalculatorTask(chainA, chainB, scoreMatrix, startRow, midRow,
                    gapScore, matchScore, missScore);
            MatrixCalculatorTask lowerMid = new MatrixCalculatorTask(chainA, chainB, scoreMatrix, midRow, endRow,
                    gapScore, matchScore, missScore);

            invokeAll(upperMid, lowerMid);
        }

        return null;
    }

    private int matchOrMiss(char baseA, char baseB) {
        if (baseA == baseB) {
            return matchScore;
        }

        return missScore;
    }
}
