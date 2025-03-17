package com.codigofacilito.needlewunsch.controller.impl;

import com.codigofacilito.needlewunsch.controller.AbstractMatrixPrinterDecorator;
import com.codigofacilito.needlewunsch.models.MatrixInfo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.function.Consumer;

public class MatrixFilePrinterDecorator extends AbstractMatrixPrinterDecorator {

    private final String filename;

    public MatrixFilePrinterDecorator(String filename) {
        super();
        this.filename = filename;
    }

    @Override
    public void decorateMatrix(MatrixInfo matrixInfo) {
        Consumer<String> printFunction = (matrixLine) -> {
            try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
                pw.println(matrixLine);
            } catch (Exception e) {
                System.err.println("Unable to write to file: " + e.getMessage());
            }
        };

        super.decorateMatrix(matrixInfo, printFunction);
    }

}
