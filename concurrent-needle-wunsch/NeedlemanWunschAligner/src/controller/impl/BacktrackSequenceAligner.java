package controller.impl;

import controller.SequenceAligner;
import models.AlignedSequences;
import models.InputData;
import models.MatrixInfo;

import static models.InputData.GAP_CHAR;

public class BacktrackSequenceAligner implements SequenceAligner {

    @Override
    public AlignedSequences alignSequences(MatrixInfo matrixInfo) {
        var input = matrixInfo.getMatrixInput();
        String seqA = input.seqA();
        String seqB = input.seqB();
        
        var scoreMatrix = matrixInfo.getScoreMatrix();

        var alignedSeqA = new StringBuilder();
        var alignedSeqB = new StringBuilder();

        int i = seqA.length();
        int j = seqB.length();

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && (scoreMatrix[i][j] == getAccumulatedCharScore(input, scoreMatrix, i-1, j-1))) {
                alignedSeqA.append(seqA.charAt(i - 1));
                alignedSeqB.append(seqB.charAt(j - 1));
                i--;
                j--;
            } else if (i > 0 && scoreMatrix[i][j] == scoreMatrix[i - 1][j] + input.gapScore()) {
                alignedSeqA.append(seqA.charAt(i - 1));
                alignedSeqB.append(GAP_CHAR);
                i--;
            } else {
                alignedSeqA.append(GAP_CHAR);
                alignedSeqB.append(seqB.charAt(j - 1));
                j--;
            }
        }

        return new AlignedSequences(alignedSeqA.toString(), alignedSeqB.toString());
    }

    private int getAccumulatedCharScore(InputData input, int[][] scoreMatrix, int i, int j) {
        int currScore = ((input.seqA().charAt(i) == input.seqB().charAt(j)) ? input.matchScore() : input.missScore());

        return scoreMatrix[i][j] + currScore;
    }

}
