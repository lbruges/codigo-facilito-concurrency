package controller.impl;

import controller.AbstractMatrixDecorator;
import controller.MatrixDecorator;
import models.MatrixInfo;

import java.util.Optional;

import static java.lang.Math.max;

public class SequentialMatrixDecorator extends AbstractMatrixDecorator {

    public SequentialMatrixDecorator() {
        super();
    }

    public SequentialMatrixDecorator(MatrixDecorator next) {
        super(next);
    }

    @Override
    public void decorateMatrix(MatrixInfo matrixInfo) {
        super.decorateMatrix(matrixInfo);
        System.out.println("--------\nSequential:\n--------");

        var matrixInput = matrixInfo.getMatrixInput();
        var scoreMatrix = matrixInfo.getScoreMatrix();

        int gapScore = matrixInput.gapScore();
        for (int i = 1; i < scoreMatrix.length; i++) {
            for (int j = 1; j < scoreMatrix[0].length; j++) {
                boolean isMatch = matrixInput.seqA().charAt(i-1) == matrixInput.seqB().charAt(j-1);
                int cellScore = (isMatch ? matrixInput.matchScore() : matrixInput.missScore())
                        + scoreMatrix[i-1][j-1];

                scoreMatrix[i][j] = max(cellScore, max(gapScore + scoreMatrix[i-1][j],
                        gapScore + scoreMatrix[i][j-1]));
            }
        }

        Optional.ofNullable(next)
                .ifPresent(matrixDecorator -> matrixDecorator.decorateMatrix(matrixInfo));
    }

}
