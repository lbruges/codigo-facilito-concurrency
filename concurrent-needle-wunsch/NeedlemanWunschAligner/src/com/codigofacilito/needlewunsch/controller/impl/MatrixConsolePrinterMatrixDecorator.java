package com.codigofacilito.needlewunsch.controller.impl;

import com.codigofacilito.needlewunsch.controller.TerminalMatrixDecorator;
import com.codigofacilito.needlewunsch.models.MatrixInfo;

import java.util.Arrays;


public class MatrixConsolePrinterMatrixDecorator extends TerminalMatrixDecorator {

    @Override
    public void decorateMatrix(MatrixInfo matrixInfo) {
        Arrays.stream(matrixInfo.getScoreMatrix())
                .map(Arrays::toString)
                .forEach(System.out::println);
    }

}
