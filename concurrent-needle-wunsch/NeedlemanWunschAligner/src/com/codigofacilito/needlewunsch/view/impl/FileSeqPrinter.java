package com.codigofacilito.needlewunsch.view.impl;

import com.codigofacilito.needlewunsch.models.AlignedSequences;
import com.codigofacilito.needlewunsch.view.AlignedSequencesPrinter;

import java.io.FileWriter;
import java.io.PrintWriter;

public class FileSeqPrinter implements AlignedSequencesPrinter {

    private final String seqFilename;

    public FileSeqPrinter(String seqFilename) {
        this.seqFilename = seqFilename;
    }


    @Override
    public void print(AlignedSequences alignedSequences) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(seqFilename))) {
            pw.println("*************************");
            pw.println("    Aligned Sequences    ");
            pw.println("*************************");
            pw.println(alignedSequences.alignedSeqA());
            pw.println(alignedSequences.alignedSeqB());
            pw.print("*************************");
        } catch (Exception e) {
            System.err.println("Unable to write to file: " + e.getMessage());
        }
    }
}
