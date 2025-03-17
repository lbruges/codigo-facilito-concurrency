package com.codigofacilito.needlewunsch.controller.factory;

import com.codigofacilito.common.props.model.MatrixProcessorProperties;
import com.codigofacilito.needlewunsch.controller.MatrixDecorator;
import com.codigofacilito.needlewunsch.controller.impl.ConcurrentMatrixDecorator;
import com.codigofacilito.needlewunsch.controller.impl.ExecutionLoggableDecorator;
import com.codigofacilito.needlewunsch.controller.impl.MatrixConsolePrinterDecorator;
import com.codigofacilito.needlewunsch.controller.impl.MatrixFilePrinterDecorator;
import com.codigofacilito.needlewunsch.controller.impl.SequentialMatrixDecorator;

public class MatrixDecoratorFactory {

    /**
     * Determines the decorator definition based on a given configuration.
     * @param matrixProperties the defined properties.
     * @return a decorator that will work according to the desired configuration.
     */
    public MatrixDecorator defineDecorator(MatrixProcessorProperties matrixProperties) {
        var printerProps = matrixProperties.getPrinter();
        MatrixDecorator matrixDecorator = null;

        if (printerProps.isEnabled()) {
            matrixDecorator = switch (printerProps.getOutput()) {
                case CONSOLE -> new MatrixConsolePrinterDecorator();
                case FILE -> new MatrixFilePrinterDecorator(printerProps.getFilename());
            };
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
