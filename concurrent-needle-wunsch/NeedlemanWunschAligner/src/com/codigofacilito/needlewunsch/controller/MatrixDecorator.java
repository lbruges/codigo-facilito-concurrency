package com.codigofacilito.needlewunsch.controller;

import com.codigofacilito.needlewunsch.models.MatrixInfo;

/**
 * Decorators will be used to either run the matrix calculation (concurrently or sequentially), log execution time,
 * or print the matrix, enforcing the sequence of events via the "next" decorator.
 */
public interface MatrixDecorator {
    void decorateMatrix(MatrixInfo matrixInfo);

    MatrixDecorator next();

}
