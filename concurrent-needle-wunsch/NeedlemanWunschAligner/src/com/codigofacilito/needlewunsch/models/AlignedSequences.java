package com.codigofacilito.needlewunsch.models;

/**
 * Model that holds the aligned versions of the sequence A and B, after being processed using the Needleman-Wusnch
 * algorithm.
 *
 * @param alignedSeqA first sequence
 * @param alignedSeqB second sequence
 */
public record AlignedSequences(String alignedSeqA, String alignedSeqB) {

}
