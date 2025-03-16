package com.codigofacilito.common.props.model;

import static com.codigofacilito.common.props.model.PrinterProperties.PrinterOutput.FILE;

public record BacktrackerProperties(PrinterProperties printer) implements PropsWithPrefix {
    private static final String PROPS_PREFIX = "backtracker";
    private static final String DEFAULT_OUTPUT_FILE = "result.txt";
    public static final BacktrackerProperties DEFAULT = new BacktrackerProperties(new PrinterProperties(true, FILE,
            DEFAULT_OUTPUT_FILE));

    @Override
    public String getPrefix() {
        return PROPS_PREFIX;
    }
}
