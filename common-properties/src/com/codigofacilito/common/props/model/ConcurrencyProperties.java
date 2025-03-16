package com.codigofacilito.common.props.model;

import java.util.Properties;

import static com.codigofacilito.common.props.util.PropsUtils.PROPS_SEPARATOR;
import static com.codigofacilito.common.props.util.PropsUtils.readBooleanProperty;
import static com.codigofacilito.common.props.util.PropsUtils.readIntegerProperty;
import static java.util.Objects.isNull;

public class ConcurrencyProperties implements PropsWithPrefix {
    private final boolean enabled;
    private final int poolSize;
    private final int sequentialThreshold;

    private static final String PROPS_PREFIX = "concurrency";

    public ConcurrencyProperties(boolean enabled, int poolSize, int sequentialThreshold) {
        this.enabled = enabled;
        this.poolSize = poolSize;
        this.sequentialThreshold = sequentialThreshold;
    }

    public ConcurrencyProperties(ConcurrencyPropsBuilder builder) {
        this.enabled = builder.enabled;
        this.poolSize = builder.poolSize;
        this.sequentialThreshold = builder.sequentialThreshold;
    }

    public static ConcurrencyPropsBuilder builder() {
        return new ConcurrencyPropsBuilder();
    }

    @Override
    public String getPrefix() {
        return PROPS_PREFIX;
    }

    public static class ConcurrencyPropsBuilder {

        private String basePath;
        private Properties properties;
        private boolean enabled;
        private int poolSize;
        private int sequentialThreshold;


        public ConcurrencyPropsBuilder fromProperties(String parentPrefix, Properties properties)  {
            this.basePath = String.join(PROPS_SEPARATOR, parentPrefix, PROPS_PREFIX);
            this.properties = properties;
            return this;
        }

        public ConcurrencyPropsBuilder orDefault(ConcurrencyProperties defaultProps) {
            this.enabled = defaultProps.enabled;
            this.poolSize = defaultProps.poolSize;
            this.sequentialThreshold = defaultProps.sequentialThreshold;

            return this;
        }

        public ConcurrencyProperties build() {
            if (isNull(properties)) {
                return new ConcurrencyProperties(this);
            }

            enabled = readBooleanProperty(properties, String.join(PROPS_SEPARATOR, basePath,
                    "enabled"), enabled);
            poolSize = readIntegerProperty(properties, String.join(PROPS_SEPARATOR, basePath,
                    "pool-size"), poolSize);
            sequentialThreshold = readIntegerProperty(properties, String.join(PROPS_SEPARATOR, basePath,
                    "seq-threshold"), sequentialThreshold);

            return new ConcurrencyProperties(this);
        }

        public boolean isEnabled() {
            return enabled;
        }

        public int getPoolSize() {
            return poolSize;
        }

        public int getSequentialThreshold() {
            return sequentialThreshold;
        }
    }

}
