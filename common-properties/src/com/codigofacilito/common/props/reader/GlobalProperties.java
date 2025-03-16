package com.codigofacilito.common.props.reader;

import com.codigofacilito.common.props.model.BacktrackerProperties;
import com.codigofacilito.common.props.model.MatrixProcessorProperties;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;
import java.util.Properties;

import static java.util.Objects.isNull;

public class GlobalProperties {

    private static final String PROPS_FILE = "application.properties";
    private static GlobalProperties instance;
    private MatrixProcessorProperties matrixProperties;
    private BacktrackerProperties backtrackerProperties;

    private GlobalProperties() {
        Optional<Properties> propertiesOpt = readProperties();
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

    private Optional<Properties> readProperties() {
        try {
            String baseDir = new File(GlobalProperties.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getParent();

            File propsFile = new File(baseDir, PROPS_FILE);

            Properties properties = new Properties();
            try (FileInputStream fis = new FileInputStream(propsFile)) {
                properties.load(fis);
            }

            return Optional.of(properties);
        } catch (Exception e) {
            System.err.println("Unable to read the " + PROPS_FILE + " file. " +
                    "Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public static GlobalProperties getInstance() {
        if (isNull(instance)) {
            instance = new GlobalProperties();
        }

        return instance;
    }

}
