package com.codigofacilito.needlewunsch.controller.impl;

import com.codigofacilito.needlewunsch.controller.MatrixDecorator;
import com.codigofacilito.needlewunsch.models.MatrixInfo;

import java.util.Arrays;

public class MatrixPrinterDecorator implements MatrixDecorator {
    @Override
    public void decorateMatrix(MatrixInfo matrixInfo) {
        Arrays.stream(matrixInfo.getScoreMatrix())
                .map(Arrays::toString)
                .forEach(System.out::println);
    }

    @Override
    public MatrixDecorator next() {
        return null;
    }
}
