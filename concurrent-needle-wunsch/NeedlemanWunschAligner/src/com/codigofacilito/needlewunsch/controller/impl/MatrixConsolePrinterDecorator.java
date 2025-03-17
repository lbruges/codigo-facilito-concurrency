package com.codigofacilito.needlewunsch.controller.impl;

import com.codigofacilito.needlewunsch.controller.AbstractMatrixPrinterDecorator;
import com.codigofacilito.needlewunsch.models.MatrixInfo;


public class MatrixConsolePrinterDecorator extends AbstractMatrixPrinterDecorator {
    @Override
    public void decorateMatrix(MatrixInfo matrixInfo) {
        super.decorateMatrix(matrixInfo, System.out::println);
    }

}
