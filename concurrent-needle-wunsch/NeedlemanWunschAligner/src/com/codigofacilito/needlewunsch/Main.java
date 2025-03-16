package com.codigofacilito.needlewunsch;

import com.codigofacilito.needlewunsch.controller.MatrixDecorator;
import com.codigofacilito.needlewunsch.controller.SequenceAligner;
import com.codigofacilito.needlewunsch.controller.impl.BacktrackSequenceAligner;
import com.codigofacilito.needlewunsch.controller.impl.ConcurrentMatrixDecorator;
import com.codigofacilito.needlewunsch.controller.impl.ExecutionLoggableDecorator;
import com.codigofacilito.needlewunsch.controller.impl.SequentialMatrixDecorator;
import com.codigofacilito.needlewunsch.models.InputData;
import com.codigofacilito.needlewunsch.models.MatrixInfo;
import com.codigofacilito.common.props.reader.GlobalProperties;
import com.codigofacilito.needlewunsch.view.AlignedSequencesPrinter;
import com.codigofacilito.needlewunsch.view.impl.FileSeqPrinter;

public class Main {

    public static void main(String[] args) {
        System.out.println("-------- Sequence Alignment! --------");
        String seqA = "ATGCATGCTCCTTGGAGGGGCTCTGCCCTATCGCTGCAGGATCCCCACTCAGCTGAAGTTGATACCTGATGACATGAAGGAGCAGATTTACAAACTGGCCAAAAAGGGCCTGACTCCCTCACAAGTCAGTGTGATCCTGAGAGATGCACATGTGTTGCACAAGTACGCTTTGTGACAGGCAATGAAATCTTAAGAATTCTTAAGTCCAAGGGACTTGCTCTTGATCTCCCTGATGATCTGTACCATTCAATCAAGAAAGCAGTTGCTATTTGAAAGCATCTCAAACAAAACAGAAAGGATAAGGATGCTAAACTATGCCTGATTCTGACAGAGAGCCGGATTCACCATTTGGCTAGATATTATAAGACCAAGTGAATCCTCCCTCCCAGTTGGAAATATGAGTCATCAACAAACAGCCTCTGCCCTGCTCGCATAA";
        String seqB = "CTCAGAACCCTTGGGAAGCCCAAGATCGTCAAAAAGAGAACCAAGAAGTTTATCCGGCACCAGTCTGACCGATATGTCAAAATTAAGCGTAACCGGCGGAAACCCAGAGGTATTGACAACAGGGTTCGTGGAAGGTTCAAGGGTTAGATCTTGATGCCCAACATTGATTATGGGAGCAACAAGAAAACAAAGCACATGCTGCCCAGTGGCTTCCGGAAATTCCTGGTCCACACGTCAAGGAGCTGGAAGTGCTGCTGTTGTGCAACAAATCTTACTGTGCTGAGATCGCTCACAATGTTTCCTCCAAGAACCGCAAAGCCATCGTGGAAAAGAGCTGCCCAGCTGGCCGTCAGAGTCACCAACCCCAATCCCAGGCTGCGCAGCAAAGAAAATGAG";

        InputData inputData = new InputData(seqA,
                seqB, GlobalProperties.getInstance().getMatrixProperties().getScoreProperties().getGapScore(), 1,-1);

        MatrixInfo concurrent = MatrixInfo.builder()
                .withConstantsInfo(inputData)
                .build();

        MatrixInfo sequential = MatrixInfo.builder()
                .withConstantsInfo(inputData)
                .build();

        // Use new ExecutionLoggableDecorator(new ConcurrentMatrixDecorator(new MatrixPrinterDecorator())); if wanting to print to console.
        // TODO: add writing matrix to text file support
        MatrixDecorator concurrentDecorator = new ExecutionLoggableDecorator(new ConcurrentMatrixDecorator());
        concurrentDecorator.decorateMatrix(concurrent);

        MatrixDecorator seqDecorator = new ExecutionLoggableDecorator(new SequentialMatrixDecorator());
        seqDecorator.decorateMatrix(sequential);

        SequenceAligner sequenceAligner = new BacktrackSequenceAligner();
        var result = sequenceAligner.alignSequences(concurrent);

        AlignedSequencesPrinter sequencesPrinter = new FileSeqPrinter();
        sequencesPrinter.print(result);

    }

}