package com.codigofacilito.common.props.model;

import java.util.Optional;
import java.util.Properties;

import static com.codigofacilito.common.props.model.PrinterProperties.PrinterOutput.FILE;

public class BacktrackerProperties implements PropsWithPrefix {

    private final PrinterProperties printer;
    private static final String PROPS_PREFIX = "backtracker";
    private static final String DEFAULT_OUTPUT_FILE = "result.txt";
    public static final BacktrackerProperties DEFAULT = new BacktrackerProperties(new PrinterProperties(true, FILE,
            DEFAULT_OUTPUT_FILE));

    public BacktrackerProperties(PrinterProperties printer) {
        this.printer = printer;
    }

    public BacktrackerProperties(BactrackPropsBuilder builder) {
        this.printer = builder.printer;
    }

    public PrinterProperties getPrinter() {
        return printer;
    }

    public static BactrackPropsBuilder builder() {
        return new BactrackPropsBuilder();
    }

    @Override
    public String getPrefix() {
        return PROPS_PREFIX;
    }

    @Override
    public String toString() {
        return "BacktrackerProperties{" +
                "printer=" + printer +
                '}';
    }

    public static class BactrackPropsBuilder {
        private Optional<Properties> propertiesOpt;
        private PrinterProperties printer;

        public BactrackPropsBuilder fromProperties(Optional<Properties> propertiesOpt) {
            this.propertiesOpt = propertiesOpt;
            return this;
        }

        public BacktrackerProperties build() {
            return propertiesOpt.map(properties -> {
                this.printer = PrinterProperties.builder()
                        .fromProperties(PROPS_PREFIX, propertiesOpt.get())
                        .orDefault(DEFAULT.printer)
                        .build();
                return new BacktrackerProperties(this);
            }).orElse(DEFAULT);
        }

    }

}
