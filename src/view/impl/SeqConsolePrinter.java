package view.impl;

import models.AlignedSequences;
import view.AlignedSequencesPrinter;

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
