package com.codigofacilito.common.props.model;

import java.util.Properties;

import static com.codigofacilito.common.props.util.PropsUtils.PROPS_SEPARATOR;
import static com.codigofacilito.common.props.util.PropsUtils.readBooleanProperty;
import static com.codigofacilito.common.props.util.PropsUtils.readEnumProperty;
import static com.codigofacilito.common.props.util.PropsUtils.readStringProperty;

public class PrinterProperties implements PropsWithPrefix {

    public enum PrinterOutput {
        CONSOLE, FILE
    }

    private final boolean enabled;
    private final PrinterOutput output;
    private final String filename;

    private static final String PROPS_PREFIX = "printer";

    public PrinterProperties(boolean enabled, PrinterOutput output, String filename) {
        this.enabled = enabled;
        this.output = output;
        this.filename = filename;
    }

    public PrinterProperties(PrinterPropertiesBuilder builder) {
        this.enabled = builder.enabled;
        this.output = builder.output;
        this.filename = builder.filename;
    }

    @Override
    public String getPrefix() {
        return PROPS_PREFIX;
    }

    public static PrinterPropertiesBuilder builder() {
        return new PrinterPropertiesBuilder();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public PrinterOutput getOutput() {
        return output;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return "PrinterProperties{" +
                "enabled=" + enabled +
                ", output=" + output +
                ", filename='" + filename + '\'' +
                '}';
    }

    public static class PrinterPropertiesBuilder {

        private String basePath;
        private Properties properties;
        private boolean enabled;
        private PrinterOutput output;
        private String filename;

        public PrinterPropertiesBuilder fromProperties(String parentPrefix, Properties properties)  {
            this.basePath = String.join(PROPS_SEPARATOR, parentPrefix, PROPS_PREFIX);
            this.properties = properties;
            return this;
        }

        public PrinterPropertiesBuilder orDefault(PrinterProperties defaultProps) {
            this.enabled = defaultProps.enabled;
            this.output = defaultProps.output;
            this.filename = defaultProps.filename;

            return this;
        }

        public PrinterProperties build() {
            enabled = readBooleanProperty(properties, String.join(PROPS_SEPARATOR, basePath,
                    "enabled"), enabled);
            output = readEnumProperty(properties, String.join(PROPS_SEPARATOR, basePath,
                    "output"), output, PrinterOutput.class);
            filename = readStringProperty(properties, String.join(PROPS_SEPARATOR, basePath,
                    "filename"), filename);

            return new PrinterProperties(this);
        }

    }

}

