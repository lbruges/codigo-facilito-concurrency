package com.codigofacilito.needlewunsch.controller.factory;

import com.codigofacilito.common.props.model.BacktrackerProperties;
import com.codigofacilito.needlewunsch.view.AlignedSequencesPrinter;
import com.codigofacilito.needlewunsch.view.impl.FileSeqPrinter;
import com.codigofacilito.needlewunsch.view.impl.SeqConsolePrinter;

public class AlignedSequencesPrinterFactory {

    public AlignedSequencesPrinter definePrinter(BacktrackerProperties properties) {
        var printerProps = properties.getPrinter();

        return switch (printerProps.getOutput()) {
            case FILE -> new FileSeqPrinter(printerProps.getFilename());
            case CONSOLE -> new SeqConsolePrinter();
        };
    }

}
