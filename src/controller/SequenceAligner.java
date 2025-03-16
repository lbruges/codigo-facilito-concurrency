package controller;

import models.AlignedSequences;
import models.MatrixInfo;

public interface SequenceAligner {

    AlignedSequences alignSequences(MatrixInfo matrixInfo);

}
