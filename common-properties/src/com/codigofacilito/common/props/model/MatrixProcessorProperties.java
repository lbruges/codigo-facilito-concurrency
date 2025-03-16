package com.codigofacilito.common.props.model;

import java.util.Optional;
import java.util.Properties;

import static com.codigofacilito.common.props.model.PrinterProperties.PrinterOutput.CONSOLE;

public class MatrixProcessorProperties implements PropsWithPrefix {
    private static final String PROPS_PREFIX = "matrix";
    private static final String DEFAULT_OUTPUT_FILE = "matrix.txt";

    private final ConcurrencyProperties concurrency;
    private final PrinterProperties printer;

    private final static MatrixProcessorProperties DEFAULT = new MatrixProcessorProperties(new ConcurrencyProperties(false,
            Runtime.getRuntime().availableProcessors(), 20), new PrinterProperties(false,
            CONSOLE, DEFAULT_OUTPUT_FILE));

    public MatrixProcessorProperties(ConcurrencyProperties concurrency, PrinterProperties printer) {
        this.concurrency = concurrency;
        this.printer = printer;
    }

    public MatrixProcessorProperties(MatrixPropsBuilder builder) {
        this.concurrency = builder.concurrency;
        this.printer = builder.printer;
    }

    public static MatrixPropsBuilder builder() {
        return new MatrixPropsBuilder();
    }

    @Override
    public String getPrefix() {
        return PROPS_PREFIX;
    }

    public PrinterProperties getPrinter() {
        return printer;
    }

    public ConcurrencyProperties getConcurrency() {
        return concurrency;
    }

    public static class MatrixPropsBuilder {

        private Optional<Properties> propertiesOpt;
        private ConcurrencyProperties concurrency;
        private PrinterProperties printer;

        public MatrixPropsBuilder withProperties(Optional<Properties> propertiesOpt) {
            this.propertiesOpt = propertiesOpt;
            return this;
        }

        public MatrixProcessorProperties build() {
            return propertiesOpt.map(properties -> {
                this.printer = PrinterProperties.builder()
                        .fromProperties(PROPS_PREFIX, properties)
                        .orDefault(DEFAULT.printer)
                        .build();

                this.concurrency = ConcurrencyProperties.builder()
                        .fromProperties(PROPS_PREFIX, properties)
                        .orDefault(DEFAULT.concurrency)
                        .build();

                return new MatrixProcessorProperties(this);
            }).orElse(DEFAULT);
        }

    }

}
