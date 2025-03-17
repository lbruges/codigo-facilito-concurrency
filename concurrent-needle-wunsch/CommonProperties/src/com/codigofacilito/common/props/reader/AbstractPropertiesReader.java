package com.codigofacilito.common.props.reader;

import com.codigofacilito.common.props.reader.req.RequestProperties;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;
import java.util.Properties;

public abstract class AbstractPropertiesReader {

    protected Optional<Properties> readProperties(String fileName) {
        try {
            String baseDir = new File(RequestProperties.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getParent();

            File propsFile = new File(baseDir, fileName);

            Properties properties = new Properties();
            try (FileInputStream fis = new FileInputStream(propsFile)) {
                properties.load(fis);
            }

            return Optional.of(properties);
        } catch (Exception e) {
            System.err.println("Unable to read the " + fileName + " file. " +
                    "Error: " + e.getMessage());
            return Optional.empty();
        }
    }

}
