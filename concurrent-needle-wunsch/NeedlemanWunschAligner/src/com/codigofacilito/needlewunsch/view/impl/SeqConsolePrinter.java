package com.codigofacilito.needlewunsch.view.impl;

import com.codigofacilito.needlewunsch.models.AlignedSequences;
import com.codigofacilito.needlewunsch.view.AlignedSequencesPrinter;

public class SeqConsolePrinter implements AlignedSequencesPrinter {
    @Override
    public void print(AlignedSequences alignedSequences) {
        System.out.println("\n*************************");
        System.out.println("    Aligned Sequences    ");
        System.out.println("*************************");
        System.out.println(alignedSequences.alignedSeqA());
        System.out.println(alignedSequences.alignedSeqB());
        System.out.println("*************************");
    }
}
