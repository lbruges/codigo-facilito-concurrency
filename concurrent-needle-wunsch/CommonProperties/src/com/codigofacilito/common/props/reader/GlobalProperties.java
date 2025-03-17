package com.codigofacilito.common.props.reader;

import com.codigofacilito.common.props.model.MatrixProcessorProperties;
import com.codigofacilito.common.props.model.BacktrackerProperties;

import java.util.Optional;
import java.util.Properties;

import static java.util.Objects.isNull;

public class GlobalProperties extends AbstractPropertiesReader {

    private static final String PROPS_FILE = "application.properties";
    private static GlobalProperties instance;
    private final MatrixProcessorProperties matrixProperties;
    private final BacktrackerProperties backtrackerProperties;

    private GlobalProperties() {
        Optional<Properties> propertiesOpt = readProperties(PROPS_FILE);
        this.backtrackerProperties = BacktrackerProperties.builder()
                .fromProperties(propertiesOpt)
                .build();

        this.matrixProperties = MatrixProcessorProperties.builder()
                .withProperties(propertiesOpt)
                .build();
    }

    public MatrixProcessorProperties getMatrixProperties() {
        return matrixProperties;
    }

    public BacktrackerProperties getBacktrackerProperties() {
        return backtrackerProperties;
    }

    public static GlobalProperties getInstance() {
        if (isNull(instance)) {
            instance = new GlobalProperties();
        }

        return instance;
    }

}
