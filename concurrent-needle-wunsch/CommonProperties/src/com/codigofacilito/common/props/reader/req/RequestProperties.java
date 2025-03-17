package com.codigofacilito.common.props.reader.req;

import com.codigofacilito.common.props.model.req.WebRequestProperties;
import com.codigofacilito.common.props.reader.AbstractPropertiesReader;

import java.util.Optional;
import java.util.Properties;

import static java.util.Objects.isNull;

public class RequestProperties extends AbstractPropertiesReader {

    private static final String PROPS_FILE = "request.properties";
    private static RequestProperties instance;
    private final WebRequestProperties webRequestProperties;

    private RequestProperties() {
        Optional<Properties> propertiesOpt = readProperties(PROPS_FILE);
        this.webRequestProperties = WebRequestProperties.builder()
                .fromProperties(propertiesOpt)
                .build();

    }

    public WebRequestProperties getWebRequestProperties() {
        return webRequestProperties;
    }

    public static RequestProperties getInstance() {
        if (isNull(instance)) {
            instance = new RequestProperties();
        }

        return instance;
    }

}
