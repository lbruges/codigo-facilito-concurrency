package com.codigofacilito.needlewunsch.models;

/**
 * Stores the scores from config and the sequences entered as an input.
 * @param seqA first sequence to align
 * @param seqB second sequence to align
 * @param gapScore score for gaps
 * @param matchScore score for matching values
 * @param missScore score for mismatches
 */
public record InputData(String seqA, String seqB, int gapScore, int matchScore, int missScore) {
    public static final char GAP_CHAR = '_';
}
