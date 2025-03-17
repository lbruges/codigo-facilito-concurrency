package com.codigofacilito.needlewunsch;

import com.codigofacilito.needlewunsch.controller.MatrixDecorator;
import com.codigofacilito.needlewunsch.controller.SequenceAligner;
import com.codigofacilito.needlewunsch.controller.factory.AlignedSequencesPrinterFactory;
import com.codigofacilito.needlewunsch.controller.impl.BacktrackSequenceAligner;
import com.codigofacilito.needlewunsch.controller.factory.MatrixDecoratorFactory;
import com.codigofacilito.needlewunsch.models.InputData;
import com.codigofacilito.needlewunsch.models.MatrixInfo;
import com.codigofacilito.common.props.reader.GlobalProperties;
import com.codigofacilito.needlewunsch.view.AlignedSequencesPrinter;

public class Main {

    public static void main(String[] args) {

        var globalProps = GlobalProperties.getInstance();
        var matrixProps = globalProps.getMatrixProperties();
        var scoreProps = matrixProps.getScoreProperties();

        System.out.println("-------- Sequence Alignment! --------");
        String seqA = "ATGCATGCTCCTTGGAGGGGCTCTGCCCTATCGCTGCAGGATCCCCACTCAGCTGAAGTTGATACCTGATGACATGAAGGAGCAGATTTACAAACTGGCCAAAAAGGGCCTGACTCCCTCACAAGTCAGTGTGATCCTGAGAGATGCACATGTGTTGCACAAGTACGCTTTGTGACAGGCAATGAAATCTTAAGAATTCTTAAGTCCAAGGGACTTGCTCTTGATCTCCCTGATGATCTGTACCATTCAATCAAGAAAGCAGTTGCTATTTGAAAGCATCTCAAACAAAACAGAAAGGATAAGGATGCTAAACTATGCCTGATTCTGACAGAGAGCCGGATTCACCATTTGGCTAGATATTATAAGACCAAGTGAATCCTCCCTCCCAGTTGGAAATATGAGTCATCAACAAACAGCCTCTGCCCTGCTCGCATAA";
        String seqB = "CTCAGAACCCTTGGGAAGCCCAAGATCGTCAAAAAGAGAACCAAGAAGTTTATCCGGCACCAGTCTGACCGATATGTCAAAATTAAGCGTAACCGGCGGAAACCCAGAGGTATTGACAACAGGGTTCGTGGAAGGTTCAAGGGTTAGATCTTGATGCCCAACATTGATTATGGGAGCAACAAGAAAACAAAGCACATGCTGCCCAGTGGCTTCCGGAAATTCCTGGTCCACACGTCAAGGAGCTGGAAGTGCTGCTGTTGTGCAACAAATCTTACTGTGCTGAGATCGCTCACAATGTTTCCTCCAAGAACCGCAAAGCCATCGTGGAAAAGAGCTGCCCAGCTGGCCGTCAGAGTCACCAACCCCAATCCCAGGCTGCGCAGCAAAGAAAATGAG";

        InputData inputData = new InputData(seqA, seqB, scoreProps.getGapScore(), scoreProps.getMatchScore(),
                scoreProps.getMissScore());

        MatrixInfo matrixInfo = MatrixInfo.builder()
                .withConstantsInfo(inputData)
                .build();

        MatrixDecoratorFactory decoratorFactory = new MatrixDecoratorFactory();
        MatrixDecorator matrixDecorator = decoratorFactory.defineDecorator(matrixProps);
        matrixDecorator.decorateMatrix(matrixInfo);

        SequenceAligner sequenceAligner = new BacktrackSequenceAligner();
        var result = sequenceAligner.alignSequences(matrixInfo);

        AlignedSequencesPrinter sequencesPrinter = new AlignedSequencesPrinterFactory().definePrinter(globalProps
                .getBacktrackerProperties().getPrinter());

        sequencesPrinter.print(result);

    }

}