package needlewunsch.controller.impl;

import needlewunsch.controller.AllowsIntermediateDecorator;
import needlewunsch.controller.MatrixDecorator;
import needlewunsch.models.MatrixInfo;

import static java.util.Objects.isNull;

public class ExecutionLoggableDecorator extends AllowsIntermediateDecorator {
    public ExecutionLoggableDecorator(MatrixDecorator next) {
        super(next);
    }

    @Override
    public void decorateMatrix(MatrixInfo matrixInfo) {
        if (isNull(next())) {
            throw new IllegalArgumentException("The ExecutionLoggableDecorator requires a defined action to execute.");
        }

        long startMillis = System.currentTimeMillis();
        next().decorateMatrix(matrixInfo);
        System.out.printf("Matrix population process took %d ms to complete%n", System.currentTimeMillis() - startMillis);
    }
}
