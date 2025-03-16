package com.codigofacilito.needlewunsch.controller;

import com.codigofacilito.needlewunsch.models.MatrixInfo;

public interface MatrixDecorator {
    void decorateMatrix(MatrixInfo matrixInfo);

    MatrixDecorator next();

}
