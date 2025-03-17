package com.codigofacilito.needlewunsch.controller.factory;

import com.codigofacilito.common.props.model.PrinterProperties;
import com.codigofacilito.needlewunsch.view.AlignedSequencesPrinter;
import com.codigofacilito.needlewunsch.view.impl.FileSeqPrinter;
import com.codigofacilito.needlewunsch.view.impl.SeqConsolePrinter;

public class AlignedSequencesPrinterFactory {

    /**
     * Defines how to print the end result of the alignment.
     *
     * @param properties printing properties.
     * @return a console or file printer.
     */
    public AlignedSequencesPrinter definePrinter(PrinterProperties properties) {
        return switch (properties.getOutput()) {
            case FILE -> new FileSeqPrinter(properties.getFilename());
            case CONSOLE -> new SeqConsolePrinter();
        };
    }

}
