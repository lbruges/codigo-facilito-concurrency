package com.codigofacilito.needlewunsch.controller.factory;

import com.codigofacilito.common.props.model.MatrixProcessorProperties;
import com.codigofacilito.needlewunsch.controller.MatrixDecorator;
import com.codigofacilito.needlewunsch.controller.impl.ConcurrentMatrixDecorator;
import com.codigofacilito.needlewunsch.controller.impl.ExecutionLoggableDecorator;
import com.codigofacilito.needlewunsch.controller.impl.MatrixPrinterDecorator;
import com.codigofacilito.needlewunsch.controller.impl.SequentialMatrixDecorator;

public class MatrixDecoratorFactory {

    public MatrixDecorator defineDecorator(MatrixProcessorProperties matrixProperties) {
        MatrixDecorator matrixDecorator = null;

        if (matrixProperties.getPrinter().isEnabled()) {
            matrixDecorator = new MatrixPrinterDecorator();
        }

        if (matrixProperties.getConcurrency().isEnabled()) {
            matrixDecorator = new ConcurrentMatrixDecorator(matrixDecorator, matrixProperties.getConcurrency());
        } else {
            matrixDecorator = new SequentialMatrixDecorator(matrixDecorator);
        }

        if (matrixProperties.shouldLogExecTime()) {
            matrixDecorator = new ExecutionLoggableDecorator(matrixDecorator);
        }

        return matrixDecorator;
    }

}
