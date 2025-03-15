package controller;

import models.MatrixInfo;

public interface MatrixDecorator {
    void decorateMatrix(MatrixInfo matrixInfo);

    MatrixDecorator next();

}
