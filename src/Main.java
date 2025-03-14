import utils.MatrixCalculatorTask;
import utils.MatrixInfo;
import utils.MatrixPrinter;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        System.out.println("-------- Sequence Alignment! --------");
        MatrixInfo matrixInfo = MatrixInfo.builder()
                .chainA("ACG")
                .chainB("ATCG")
                .gapScore(-2)
                .missScore(-1)
                .matchScore(1)
                .withInitialGapValues()
                .build();

        try (ForkJoinPool pool = new ForkJoinPool()) {
            pool.invoke(new MatrixCalculatorTask(matrixInfo, matrixInfo.getScoreMatrix()));
        }

        MatrixPrinter.printMatrix(matrixInfo.getScoreMatrix());
    }
}