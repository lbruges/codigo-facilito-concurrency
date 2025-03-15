
import concurrency.MatrixCalculatorTask;
import models.ConstantsInfo;
import models.MatrixInfo;
import utils.MatrixPrinter;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        System.out.println("-------- Sequence Alignment! --------");
        ConstantsInfo constantsInfo = new ConstantsInfo("ACG", "ATCG", -2, 1,-1);

        MatrixInfo concurrent = MatrixInfo.builder()
                .withConstantsInfo(constantsInfo)
                .withInitialGapValues()
                .build();

        MatrixInfo nonConcurrent = MatrixInfo.builder()
                .withConstantsInfo(constantsInfo)
                .withAllValues()
                .build();

        // TODO: why getting different results sync vs async
        try (ForkJoinPool pool = new ForkJoinPool()) {
            pool.invoke(new MatrixCalculatorTask(constantsInfo, concurrent.getScoreMatrix()));
        }

        System.out.println("Concurrent:\n--------");
        MatrixPrinter.printMatrix(concurrent.getScoreMatrix());
        System.out.println("--------\nNon-Concurrent:\n--------");
        MatrixPrinter.printMatrix(nonConcurrent.getScoreMatrix());
    }
}