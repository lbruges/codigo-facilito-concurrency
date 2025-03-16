import controller.MatrixDecorator;
import controller.SequenceAligner;
import controller.impl.BacktrackSequenceAligner;
import controller.impl.ConcurrentMatrixDecorator;
import controller.impl.ExecutionLoggableDecorator;
import controller.impl.SequentialMatrixDecorator;
import models.InputData;
import models.MatrixInfo;
import view.AlignedSequencesPrinter;
import view.impl.FileSeqPrinter;

public class Main {

    public static void main(String[] args) {
        System.out.println("-------- Sequence Alignment! --------");
        String seqA = "ATGCATGCTCCTTGGAGGGGCTCTGCCCTATCGCTGCAGGATCCCCACTCAGCTGAAGTTGATACCTGATGACATGAAGGAGCAGATTTACAAACTGGCCAAAAAGGGCCTGACTCCCTCACAAGTCAGTGTGATCCTGAGAGATGCACATGTGTTGCACAAGTACGCTTTGTGACAGGCAATGAAATCTTAAGAATTCTTAAGTCCAAGGGACTTGCTCTTGATCTCCCTGATGATCTGTACCATTCAATCAAGAAAGCAGTTGCTATTTGAAAGCATCTCAAACAAAACAGAAAGGATAAGGATGCTAAACTATGCCTGATTCTGACAGAGAGCCGGATTCACCATTTGGCTAGATATTATAAGACCAAGTGAATCCTCCCTCCCAGTTGGAAATATGAGTCATCAACAAACAGCCTCTGCCCTGCTCGCATAA";
        String seqB = "CTCAGAACCCTTGGGAAGCCCAAGATCGTCAAAAAGAGAACCAAGAAGTTTATCCGGCACCAGTCTGACCGATATGTCAAAATTAAGCGTAACCGGCGGAAACCCAGAGGTATTGACAACAGGGTTCGTGGAAGGTTCAAGGGTTAGATCTTGATGCCCAACATTGATTATGGGAGCAACAAGAAAACAAAGCACATGCTGCCCAGTGGCTTCCGGAAATTCCTGGTCCACACGTCAAGGAGCTGGAAGTGCTGCTGTTGTGCAACAAATCTTACTGTGCTGAGATCGCTCACAATGTTTCCTCCAAGAACCGCAAAGCCATCGTGGAAAAGAGCTGCCCAGCTGGCCGTCAGAGTCACCAACCCCAATCCCAGGCTGCGCAGCAAAGAAAATGAG";

        InputData inputData = new InputData(seqA,
                seqB, -2, 1,-1);

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