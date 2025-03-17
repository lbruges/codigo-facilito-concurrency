package com.codigofacilito.needlewunsch.controller;

import com.codigofacilito.needlewunsch.models.MatrixInfo;

import java.util.Arrays;
import java.util.function.Consumer;

public abstract class AbstractMatrixPrinterDecorator implements MatrixDecorator {


    public void decorateMatrix(MatrixInfo matrixInfo, Consumer<String> printFunction) {
        Arrays.stream(matrixInfo.getScoreMatrix())
                .map(Arrays::toString)
                .forEach(printFunction);
    }

    @Override
    public MatrixDecorator next() {
        return null;
    }


}
