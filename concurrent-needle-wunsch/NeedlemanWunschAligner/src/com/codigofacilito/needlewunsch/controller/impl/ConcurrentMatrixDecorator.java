package com.codigofacilito.needlewunsch.controller.impl;

import com.codigofacilito.common.props.model.ConcurrencyProperties;
import com.codigofacilito.needlewunsch.concurrency.MatrixCalculatorTask;
import com.codigofacilito.needlewunsch.controller.MatrixPopulatorDecorator;
import com.codigofacilito.needlewunsch.controller.MatrixDecorator;
import com.codigofacilito.needlewunsch.models.MatrixInfo;

import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

public class ConcurrentMatrixDecorator extends MatrixPopulatorDecorator {

    private final ConcurrencyProperties concurrencyProperties;

    public ConcurrentMatrixDecorator(ConcurrencyProperties concurrencyProperties) {
        super();
        this.concurrencyProperties = concurrencyProperties;
    }

    public ConcurrentMatrixDecorator(MatrixDecorator next, ConcurrencyProperties concurrencyProperties) {
        super(next);
        this.concurrencyProperties = concurrencyProperties;
    }

    @Override
    public void decorateMatrix(MatrixInfo matrixInfo) {
        System.out.println("Concurrent:\n--------");
        super.decorateMatrix(matrixInfo);

        try (ForkJoinPool pool = new ForkJoinPool(concurrencyProperties.getPoolSize())) {
            pool.invoke(new MatrixCalculatorTask(matrixInfo, concurrencyProperties.getSequentialThreshold()));
        }

        Optional.ofNullable(next())
                .ifPresent(matrixDecorator -> matrixDecorator.decorateMatrix(matrixInfo));
    }

}
