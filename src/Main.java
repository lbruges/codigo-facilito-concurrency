
import controller.MatrixDecorator;
import controller.impl.ConcurrentMatrixDecorator;
import controller.impl.ExecutionLoggableDecorator;
import controller.impl.MatrixPrinterDecorator;
import controller.impl.SequentialMatrixDecorator;
import models.InputData;
import models.MatrixInfo;

public class Main {
    public static void main(String[] args) {
        System.out.println("-------- Sequence Alignment! --------");
        InputData inputData = new InputData("ACG", "ATCG", -2, 1,-1);

        MatrixInfo concurrent = MatrixInfo.builder()
                .withConstantsInfo(inputData)
                .build();

        MatrixInfo sequential = MatrixInfo.builder()
                .withConstantsInfo(inputData)
                .build();


        MatrixDecorator concurrentDecorator = new ExecutionLoggableDecorator(new ConcurrentMatrixDecorator(new MatrixPrinterDecorator()));
        concurrentDecorator.decorateMatrix(concurrent);

        MatrixDecorator seqDecorator = new ExecutionLoggableDecorator(new SequentialMatrixDecorator(new MatrixPrinterDecorator()));
        seqDecorator.decorateMatrix(sequential);
    }
}