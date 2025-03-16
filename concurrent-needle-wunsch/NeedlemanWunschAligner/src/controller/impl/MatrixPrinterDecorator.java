package controller.impl;

import controller.MatrixDecorator;
import models.MatrixInfo;

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
