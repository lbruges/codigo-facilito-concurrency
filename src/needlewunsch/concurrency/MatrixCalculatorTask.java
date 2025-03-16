package needlewunsch.concurrency;

import needlewunsch.models.MatrixInfo;
import needlewunsch.models.InputData;

import java.util.concurrent.RecursiveTask;

import static java.lang.Math.max;

/**
 * Recursive task to be used as input for the fork-join framework.
 */
public class MatrixCalculatorTask extends RecursiveTask<Void> {

    private static final int SEQUENTIAL_THRESHOLD = 20;  // TODO: Externalize to tune
    private final MatrixInfo matrixInfo;
    private final int startRow;
    private final int endRow;

    public MatrixCalculatorTask(MatrixInfo matrixInfo, int startRow, int endRow) {
        this.matrixInfo = matrixInfo;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    public MatrixCalculatorTask(MatrixInfo matrixInfo) {
        this(matrixInfo, 1, matrixInfo.getMatrixInput().seqA().length());
    }

    @Override
    protected Void compute() {
        int numRows = endRow - startRow;

        // Controlling splitting by using a threshold
        if (numRows <= SEQUENTIAL_THRESHOLD) {
            computeSequentially();
        } else {
            // Split the rows in two, as they're independent and can be computed in parallel
            int midRow = (startRow + endRow) / 2;
            MatrixCalculatorTask upperTask = new MatrixCalculatorTask(matrixInfo, startRow, midRow);
            MatrixCalculatorTask lowerTask = new MatrixCalculatorTask(matrixInfo, midRow, endRow);

            // Fork one task and compute the other in parallel
            lowerTask.fork();
            upperTask.compute();
            lowerTask.join();
        }
        return null;
    }

    private void computeSequentially() {
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
    }

    private int matchOrMiss(char baseA, char baseB, InputData inputData) {
        return (baseA == baseB) ? inputData.matchScore() : inputData.missScore();
    }
}
