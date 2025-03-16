package com.codigofacilito.needlewunsch.controller;

/**
 * Abstract class to reduce redundant code for non-terminal (or optionally terminal) decorators.
 */
public abstract class AllowsIntermediateDecorator implements MatrixDecorator {
    private final MatrixDecorator next;

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

}
