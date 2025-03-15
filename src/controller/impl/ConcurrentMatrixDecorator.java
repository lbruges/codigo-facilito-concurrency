package controller.impl;

import concurrency.MatrixCalculatorTask;
import controller.AbstractMatrixDecorator;
import controller.MatrixDecorator;
import models.MatrixInfo;

import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

public class ConcurrentMatrixDecorator extends AbstractMatrixDecorator {

    public ConcurrentMatrixDecorator() {
        super();
    }

    public ConcurrentMatrixDecorator(MatrixDecorator next) {
        super(next);
    }

    @Override
    public void decorateMatrix(MatrixInfo matrixInfo) {
        System.out.println("Concurrent:\n--------");
        super.decorateMatrix(matrixInfo);

        try (ForkJoinPool pool = new ForkJoinPool()) {
            pool.invoke(new MatrixCalculatorTask(matrixInfo));
        }

        Optional.ofNullable(next)
                .ifPresent(matrixDecorator -> matrixDecorator.decorateMatrix(matrixInfo));
    }

}
