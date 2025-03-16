package com.codigofacilito.common.props.reader;

import com.codigofacilito.common.props.model.BacktrackerProperties;
import com.codigofacilito.common.props.model.MatrixProcessorProperties;

import static java.util.Objects.isNull;

public class GlobalProperties {

    private static GlobalProperties instance;
    private MatrixProcessorProperties matrixProperties;
    private BacktrackerProperties backtrackerProperties;

    private GlobalProperties() {

    }

    public GlobalProperties getInstance() {
        if (isNull(instance)) {
            instance = new GlobalProperties();
        }

        return instance;
    }

}
