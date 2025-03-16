package com.codigofacilito.needlewunsch.controller;

public abstract class AllowsIntermediateDecorator implements MatrixDecorator {
    private MatrixDecorator next;

    public AllowsIntermediateDecorator() {
        this.next = null;
    }

    public AllowsIntermediateDecorator(MatrixDecorator next) {
        this.next = next;
    }

    @Override
    public MatrixDecorator next() {
        return next;
    }

    @Override
    public void setNext(MatrixDecorator next) {
        this.next = next;
    }
}
