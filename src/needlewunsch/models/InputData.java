package needlewunsch.models;

public record InputData(String seqA, String seqB, int gapScore, int matchScore, int missScore) {
    public static final char GAP_CHAR = '_';
}
