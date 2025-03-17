package com.codigofacilito.needlewunsch.controller.impl;

import com.codigofacilito.needlewunsch.controller.TerminalMatrixDecorator;
import com.codigofacilito.needlewunsch.models.MatrixInfo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class MatrixFilePrinterMatrixDecorator extends TerminalMatrixDecorator {

    private final String filename;

    public MatrixFilePrinterMatrixDecorator(String filename) {
        super();
        this.filename = filename;
    }

    @Override
    public void decorateMatrix(MatrixInfo matrixInfo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println();
            Arrays.stream(matrixInfo.getScoreMatrix())
                    .map(Arrays::toString)
                    .forEach(pw::println);
            pw.println();
        } catch (Exception e) {
            System.err.println("Unable to write to file: " + e.getMessage());
        }
    }

}
