package needlewunsch.controller;

import needlewunsch.models.MatrixInfo;

public interface MatrixDecorator {
    void decorateMatrix(MatrixInfo matrixInfo);

    MatrixDecorator next();

}
