package models;

public record AlignedSequences(String alignedSeqA, String alignedSeqB) {

    public void printSequences() {
        System.out.println(alignedSeqA);
        System.out.println(alignedSeqB);
    }

}
