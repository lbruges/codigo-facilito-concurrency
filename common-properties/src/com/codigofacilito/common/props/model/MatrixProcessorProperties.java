package com.codigofacilito.common.props.model;

import java.util.Optional;
import java.util.Properties;

import static com.codigofacilito.common.props.model.PrinterProperties.PrinterOutput.CONSOLE;
import static com.codigofacilito.common.props.util.PropsUtils.PROPS_SEPARATOR;
import static com.codigofacilito.common.props.util.PropsUtils.readBooleanProperty;

public class MatrixProcessorProperties implements PropsWithPrefix {
    private static final String PROPS_PREFIX = "matrix";
    private static final String DEFAULT_OUTPUT_FILE = "matrix.txt";

    private final ConcurrencyProperties concurrency;
    private final PrinterProperties printer;
    private final ScoreProperties scoreProperties;
    private final boolean logExecTime;

    private final static MatrixProcessorProperties DEFAULT = new MatrixProcessorProperties(new ConcurrencyProperties(false,
            Runtime.getRuntime().availableProcessors(), 20), new PrinterProperties(false,
            CONSOLE, DEFAULT_OUTPUT_FILE), new ScoreProperties(-2, 1, -1), true);

    public MatrixProcessorProperties(ConcurrencyProperties concurrency, PrinterProperties printer,
                                     ScoreProperties scoreProperties, boolean logExecTime) {
        this.concurrency = concurrency;
        this.printer = printer;
        this.scoreProperties = scoreProperties;
        this.logExecTime = logExecTime;
    }

    public MatrixProcessorProperties(MatrixPropsBuilder builder) {
        this.concurrency = builder.concurrency;
        this.printer = builder.printer;
        this.scoreProperties = builder.scoreProperties;
        this.logExecTime = builder.logExecTime;
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

    public ScoreProperties getScoreProperties() {
        return scoreProperties;
    }

    public boolean shouldLogExecTime() {
        return logExecTime;
    }

    @Override
    public String toString() {
        return "MatrixProcessorProperties{" +
                "concurrency=" + concurrency +
                ", printer=" + printer +
                ", scoreProperties=" + scoreProperties +
                ", logExecTime=" + logExecTime +
                '}';
    }


    public static class MatrixPropsBuilder {

        private Optional<Properties> propertiesOpt;
        private ConcurrencyProperties concurrency;
        private PrinterProperties printer;
        private ScoreProperties scoreProperties;
        private boolean logExecTime;

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

                this.scoreProperties = ScoreProperties.builder()
                        .fromProperties(PROPS_PREFIX, properties)
                        .orDefault(DEFAULT.scoreProperties)
                        .build();

                this.logExecTime = readBooleanProperty(properties, String.join(PROPS_SEPARATOR, PROPS_PREFIX,
                        "log-exec-time"), DEFAULT.logExecTime);

                return new MatrixProcessorProperties(this);
            }).orElse(DEFAULT);
        }

    }

}
