package com.codigofacilito.needlewunsch.controller;

public abstract class TerminalMatrixDecorator implements MatrixDecorator {

    @Override
    public MatrixDecorator next() {
        return null;
    }


}
